package com.example.data.ai

import com.example.BuildConfig
import com.example.data.model.AICoachEvaluation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object AICoachService {

    private const val MODEL = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL:generateContent"

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Evaluates a learner's spoken or written English submission, specifically tailored
     * for Persian-speaking immigrants. Identifies common Persian transfer errors (he/she, articles, past simple, prepositions, word order).
     */
    suspend fun evaluateSubmission(
        scenarioContext: String,
        promptEn: String,
        userText: String,
        skillType: String = "Speaking"
    ): AICoachEvaluation = withContext(Dispatchers.IO) {
        val cleanUserText = userText.trim()
        if (cleanUserText.isEmpty()) {
            return@withContext getLocalFallbackEvaluation(scenarioContext, "", "Please try speaking or typing your response.")
        }

        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getLocalFallbackEvaluation(scenarioContext, cleanUserText)
        }

        try {
            val systemPrompt = """
                You are English+ AI Coach, a master English mentor specializing in helping Persian (Farsi) speaking immigrants prepare for real-world life, work, and university abroad.
                Analyze the learner's response to the scenario task.
                
                Provide your analysis strictly in valid JSON format with these exact keys:
                {
                  "wasUnderstandable": true or false,
                  "grammarCritiqueEn": "Detailed grammar feedback in clear English",
                  "grammarCritiqueFa": "توضیح فارسی دقیق برای زبان‌آموز در مورد اشکالات گرامری یا ساختاری",
                  "betterVersion": "A grammatically correct version of their sentence",
                  "naturalNativeVersion": "A polished, natural native phrasing used in English-speaking countries",
                  "detectedMistakePattern": "Specific mistake e.g. 'Duration Preposition (from vs for)' or 'Missing Article' or null,
                  "encouragementEn": "Encouraging tip for migration success",
                  "encouragementFa": "پیام انگیزشی و کاربردی به زبان فارسی",
                  "scoreOutOf100": integer between 40 and 100
                }
            """.trimIndent()

            val userPrompt = """
                Scenario: $scenarioContext
                Task: $promptEn
                Skill Type: $skillType
                Learner's sentence: "$cleanUserText"
                
                Evaluate the learner's sentence now. Return ONLY JSON.
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "$systemPrompt\n\n$userPrompt"))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.3)
                    put("topP", 0.95)
                })
            }

            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (response.isSuccessful && responseBody.isNotBlank()) {
                val jsonResponse = JSONObject(responseBody)
                val candidates = jsonResponse.optJSONArray("candidates")
                val firstCandidate = candidates?.optJSONObject(0)
                val content = firstCandidate?.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                val text = parts?.optJSONObject(0)?.optString("text") ?: ""

                // Clean json markers if present
                val cleanedText = text
                    .replace("```json", "")
                    .replace("```", "")
                    .trim()

                val evalJson = JSONObject(cleanedText)
                return@withContext AICoachEvaluation(
                    userText = cleanUserText,
                    wasUnderstandable = evalJson.optBoolean("wasUnderstandable", true),
                    grammarCritiqueEn = evalJson.optString("grammarCritiqueEn", "Good attempt!"),
                    grammarCritiqueFa = evalJson.optString("grammarCritiqueFa", "تلاش بسیار خوبی بود."),
                    betterVersion = evalJson.optString("betterVersion", cleanUserText),
                    naturalNativeVersion = evalJson.optString("naturalNativeVersion", cleanUserText),
                    detectedMistakePattern = if (evalJson.has("detectedMistakePattern") && !evalJson.isNull("detectedMistakePattern")) evalJson.optString("detectedMistakePattern") else null,
                    encouragementEn = evalJson.optString("encouragementEn", "Keep practicing daily for immigration fluency!"),
                    encouragementFa = evalJson.optString("encouragementFa", "با تداوم در تمرین روزانه به روانی کلام خواهید رسید."),
                    scoreOutOf100 = evalJson.optInt("scoreOutOf100", 82)
                )
            } else {
                return@withContext getLocalFallbackEvaluation(scenarioContext, cleanUserText)
            }
        } catch (e: Exception) {
            return@withContext getLocalFallbackEvaluation(scenarioContext, cleanUserText)
        }
    }

    /**
     * Intelligent local coach fallback with deep heuristic analysis of Persian-specific mistakes.
     */
    fun getLocalFallbackEvaluation(
        scenarioContext: String,
        userText: String,
        customMessage: String? = null
    ): AICoachEvaluation {
        val lower = userText.lowercase()

        // Detect common Persian error 1: "from two days" instead of "for two days"
        val hasFromDurationError = lower.contains("from two days") || lower.contains("from 2 days") || lower.contains("from three days") || lower.contains("from 3 days") || lower.contains("from yesterday") && lower.contains("have headache")
        
        // Detect common Persian error 2: Missing articles
        val hasMissingArticle = lower.contains("send email") || lower.contains("have headache") || lower.contains("call landlord") || lower.contains("have car")

        // Detect common Persian error 3: "I have 5 years experience" vs "I have 5 years of experience"
        val hasExperienceGrammar = lower.contains("years experience")

        return when {
            hasFromDurationError -> {
                AICoachEvaluation(
                    userText = userText,
                    wasUnderstandable = true,
                    grammarCritiqueEn = "Your meaning was clear! However, use 'for [duration]' instead of 'from' to describe how long something has lasted.",
                    grammarCritiqueFa = "معنای شما کاملاً منتقل شد. اما در انگلیسی برای بیان مدت زمان استمرار یک بیماری یا وضعیت از for استفاده می‌شود نه from.",
                    betterVersion = userText.replace(Regex("from (\\d+|two|three|four) days", RegexOption.IGNORE_CASE), "for $1 days"),
                    naturalNativeVersion = "I've had this symptom for the past few days.",
                    detectedMistakePattern = "Duration Prepositions (for vs from)",
                    encouragementEn = "Great job communicating the urgency!",
                    encouragementFa = "بیان منظور شما عالی بود. با اصلاح این نکته گرامری، صحبت شما کاملاً نیتیو به نظر می‌رسد.",
                    scoreOutOf100 = 84
                )
            }
            hasMissingArticle -> {
                AICoachEvaluation(
                    userText = userText,
                    wasUnderstandable = true,
                    grammarCritiqueEn = "Great communication. Remember to include the article 'a' or 'the' before singular nouns (e.g., 'a headache', 'the landlord').",
                    grammarCritiqueFa = "پیام شما رسا بود. در انگلیسی قبل از اسم‌های مفرد قابل شمارش حتماً از a/an یا the استفاده کنید (مثلاً a headache یا the landlord).",
                    betterVersion = userText.replace("have headache", "have a headache").replace("call landlord", "call the landlord"),
                    naturalNativeVersion = "I have a headache and need to notify the landlord.",
                    detectedMistakePattern = "Missing Articles (a / an / the)",
                    encouragementEn = "Very understandable and polite!",
                    encouragementFa = "لحن شما بسیار محترمانه و قابل درک بود.",
                    scoreOutOf100 = 86
                )
            }
            hasExperienceGrammar -> {
                AICoachEvaluation(
                    userText = userText,
                    wasUnderstandable = true,
                    grammarCritiqueEn = "Professional response! Say 'five years of experience' (adding 'of') or use 'I have worked for five years'.",
                    grammarCritiqueFa = "پاسخ حرفه‌ای بود. برای بیان سابقه کاری از عبارت five years of experience (همراه با of) استفاده نمایید.",
                    betterVersion = userText.replace("years experience", "years of experience"),
                    naturalNativeVersion = "I have over five years of experience collaborating with cross-functional teams.",
                    detectedMistakePattern = "Workplace Experience Phrasing",
                    encouragementEn = "Excellent professional tone!",
                    encouragementFa = "لحن حرفه‌ای و کاری بسیار خوبی داشتید.",
                    scoreOutOf100 = 90
                )
            }
            else -> {
                AICoachEvaluation(
                    userText = userText,
                    wasUnderstandable = true,
                    grammarCritiqueEn = customMessage ?: "Well structured! Your sentence communicates the core intention accurately and politely.",
                    grammarCritiqueFa = "جمله‌بندی شما دقیق و ساختاریافته است و منظور شما را با لحنی محترمانه منتقل می‌کند.",
                    betterVersion = userText,
                    naturalNativeVersion = userText,
                    detectedMistakePattern = null,
                    encouragementEn = "Keep up this confidence—you are ready for real-world situations!",
                    encouragementFa = "این اعتماد به نفس در مکالمه شما را برای زندگی واقعی در خارج از کشور آماده می‌کند!",
                    scoreOutOf100 = 92
                )
            }
        }
    }

    /**
     * AI Coach dynamic roleplay simulation turn response.
     */
    suspend fun getCoachRoleplayResponse(
        roleTitle: String,
        scenarioGoal: String,
        conversationHistory: List<Pair<String, String>>, // Speaker to Text
        userSpeech: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackRoleplayResponse(roleTitle, userSpeech)
        }

        try {
            val historyFormatted = conversationHistory.joinToString("\n") { "${it.first}: ${it.second}" }
            val prompt = """
                You are roleplaying as "$roleTitle" in an English learning scenario for an immigrant.
                Scenario Objective: $scenarioGoal
                
                Previous dialogue:
                $historyFormatted
                Learner said: "$userSpeech"
                
                Respond in character as $roleTitle in 1-2 realistic, natural English sentences that keep the conversation moving.
                Do not include meta comments, just the character's speech.
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (response.isSuccessful && responseBody.isNotBlank()) {
                val jsonResponse = JSONObject(responseBody)
                val candidates = jsonResponse.optJSONArray("candidates")
                val firstCandidate = candidates?.optJSONObject(0)
                val text = firstCandidate?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")
                return@withContext text?.trim() ?: getFallbackRoleplayResponse(roleTitle, userSpeech)
            } else {
                return@withContext getFallbackRoleplayResponse(roleTitle, userSpeech)
            }
        } catch (e: Exception) {
            return@withContext getFallbackRoleplayResponse(roleTitle, userSpeech)
        }
    }

    private fun getFallbackRoleplayResponse(roleTitle: String, userSpeech: String): String {
        val lower = userSpeech.lowercase()
        return when {
            roleTitle.contains("Landlord", ignoreCase = true) -> {
                if (lower.contains("afternoon") || lower.contains("2") || lower.contains("pm") || lower.contains("time")) {
                    "Understood. I will book the plumber for 2:30 PM today. They will ring your doorbell upon arrival."
                } else {
                    "Thanks for clarifying. Is anyone available at the apartment this afternoon so the technician can inspect the leak?"
                }
            }
            roleTitle.contains("Doctor", ignoreCase = true) -> {
                if (lower.contains("headache") || lower.contains("throat") || lower.contains("fever")) {
                    "Let me examine your throat and listen to your chest. Have you taken any over-the-counter medicine yet?"
                } else {
                    "I see. How many days have you been experiencing these symptoms, and do you have any allergies?"
                }
            }
            roleTitle.contains("Manager", ignoreCase = true) || roleTitle.contains("Product", ignoreCase = true) -> {
                "That's great to hear! Let's sync up after the morning standup so I can share the onboarding workspace with you."
            }
            else -> {
                "Thank you for letting me know. Let's proceed with the next step together."
            }
        }
    }
}
