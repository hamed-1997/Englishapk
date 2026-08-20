package com.example.ui.screens.lesson

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun LessonScreen(
    lesson: FullLessonContent,
    audioHelper: AudioHelper,
    currentStep: Int,
    selectedQuizAnswers: Map<Int, Int>,
    writtenResponse: String,
    spokenResponse: String,
    isEvaluating: Boolean,
    latestEvaluation: AICoachEvaluation?,
    roleplayMessages: List<Pair<String, String>>,
    onStepChange: (Int) -> Unit,
    onQuizAnswer: (Int, Int) -> Unit,
    onWrittenChange: (String) -> Unit,
    onEvaluateWriting: () -> Unit,
    onEvaluateSpeaking: (String) -> Unit,
    onSendRoleplay: (String) -> Unit,
    onCompleteLesson: () -> Unit,
    onCloseLesson: () -> Unit
) {
    val totalSteps = 9
    val isSpeaking by audioHelper.isSpeaking.collectAsState()
    val isListening by audioHelper.isListening.collectAsState()
    val recognizedSpeech by audioHelper.recognizedText.collectAsState()

    var showCompleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onCloseLesson) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Lesson")
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Step $currentStep of $totalSteps",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Gold600
                                )
                            )
                            Text(
                                text = getStepTitle(currentStep),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Text(
                            text = "${(currentStep * 100) / totalSteps}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { currentStep / totalSteps.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Gold500,
                        trackColor = Slate200
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { onStepChange(currentStep - 1) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("← Previous")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Button(
                        onClick = {
                            if (currentStep < totalSteps) {
                                onStepChange(currentStep + 1)
                            } else {
                                onCompleteLesson()
                                showCompleteDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentStep == totalSteps) Emerald600 else Gold500
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("lesson_next_step_button")
                    ) {
                        Text(
                            text = if (currentStep == totalSteps) "Complete Lesson 🎉" else "Next Step →",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (currentStep == totalSteps) Color.White else Navy900
                            )
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                when (currentStep) {
                    1 -> Step1Context(lesson = lesson)
                    2 -> Step2Listening(lesson = lesson, audioHelper = audioHelper, isSpeaking = isSpeaking)
                    3 -> Step3Understanding(lesson = lesson, selectedAnswers = selectedQuizAnswers, onAnswer = onQuizAnswer)
                    4 -> Step4Vocabulary(lesson = lesson, audioHelper = audioHelper)
                    5 -> Step5Grammar(lesson = lesson)
                    6 -> Step6Speaking(
                        lesson = lesson,
                        audioHelper = audioHelper,
                        isListening = isListening,
                        latestEvaluation = latestEvaluation,
                        isEvaluating = isEvaluating,
                        onEvaluateSpeaking = onEvaluateSpeaking
                    )
                    7 -> Step7Reading(lesson = lesson)
                    8 -> Step8Writing(
                        lesson = lesson,
                        writtenResponse = writtenResponse,
                        isEvaluating = isEvaluating,
                        latestEvaluation = latestEvaluation,
                        onWrittenChange = onWrittenChange,
                        onEvaluateWriting = onEvaluateWriting
                    )
                    9 -> Step9Challenge(
                        lesson = lesson,
                        roleplayMessages = roleplayMessages,
                        isListening = isListening,
                        audioHelper = audioHelper,
                        onSendTurn = onSendRoleplay
                    )
                }
            }

            // Lesson Finished Dialog
            if (showCompleteDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showCompleteDialog = false
                        onCloseLesson()
                    },
                    title = {
                        Text(
                            text = "Lesson Completed! 🎉",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "تبریک! شما این درس کاربردی را با موفقیت به پایان رساندید.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Gold600)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "مهارت‌های شما به روز شدند:",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("✓ Listening: +4% Comprehension")
                            Text("✓ Speaking: +5% Confidence")
                            Text("✓ Reading: +3% Real Documents")
                            Text("✓ Writing: +4% Professional Text")
                            Text("✓ Migration Readiness: +6%")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showCompleteDialog = false
                                onCloseLesson()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Gold500)
                        ) {
                            Text("Continue to Dashboard", color = Navy900, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 1: CONTEXT
// -------------------------------------------------------------
@Composable
private fun Step1Context(lesson: FullLessonContent) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = Navy900,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = lesson.categoryIcon, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = lesson.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Gold400
                            )
                        )
                        Text(
                            text = lesson.titleFa,
                            style = MaterialTheme.typography.bodySmall.copy(color = Gold100)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Realistic Immigration Scenario",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = lesson.contextSituationEn,
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                PersianTranslationBox(
                    textFa = lesson.contextSituationFa,
                    label = "موقعیت واقعی در خارج از کشور"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your Mission in this Lesson",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Gold600)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Surface(
            color = Gold100,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🎯", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = lesson.immigrantMission,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Navy900,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                )
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 2: ACTIVE LISTENING
// -------------------------------------------------------------
@Composable
private fun Step2Listening(
    lesson: FullLessonContent,
    audioHelper: AudioHelper,
    isSpeaking: Boolean
) {
    var showPersianTranslation by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Natural Scenario Dialogue",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "به مکالمه واقعی گوش دهید و تلفظ‌ها را بشنوید",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            AudioPlayButton(
                onClick = {
                    val fullText = lesson.listeningDialogue.joinToString(". ") { "${it.speaker} says: ${it.textEn}" }
                    audioHelper.speak(fullText)
                },
                isPlaying = isSpeaking,
                text = "Play All"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FilterChip(
                selected = showPersianTranslation,
                onClick = { showPersianTranslation = !showPersianTranslation },
                label = { Text(if (showPersianTranslation) "Hide Persian" else "Show Persian (ترجمه)") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        lesson.listeningDialogue.forEach { line ->
            val isUserSpeaker = line.speaker.contains("You", ignoreCase = true) || line.speakerRole.contains("Tenant", ignoreCase = true) || line.speakerRole.contains("Ali", ignoreCase = true)

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isUserSpeaker) Slate100 else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(14.dp),
                border = if (isUserSpeaker) androidx.compose.foundation.BorderStroke(1.dp, Gold500.copy(alpha = 0.5f)) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = line.speaker,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isUserSpeaker) Gold600 else MaterialTheme.colorScheme.primary
                            )
                        )
                        IconButton(
                            onClick = { audioHelper.speak(line.textEn) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play line",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = line.textEn,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = 22.sp
                        )
                    )

                    if (showPersianTranslation) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = line.textFa,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            ),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 3: UNDERSTANDING & COMPREHENSION
// -------------------------------------------------------------
@Composable
private fun Step3Understanding(
    lesson: FullLessonContent,
    selectedAnswers: Map<Int, Int>,
    onAnswer: (Int, Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Comprehension & Nuance Check",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "درک دقیق مکالمه و نکات مهم قانونی / مهاجرتی",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 14.dp)
        )

        lesson.comprehensionQuestions.forEach { q ->
            val userSelected = selectedAnswers[q.id]
            val isAnswered = userSelected != null

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Q: ${q.question}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = q.questionFa,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    q.options.forEachIndexed { optIdx, optionText ->
                        val isCorrect = optIdx == q.correctIndex
                        val isPicked = userSelected == optIdx

                        val containerColor = when {
                            !isAnswered -> MaterialTheme.colorScheme.surfaceVariant
                            isPicked && isCorrect -> Emerald100
                            isPicked && !isCorrect -> Rose100
                            isCorrect -> Emerald100.copy(alpha = 0.6f)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }

                        val borderColor = when {
                            isPicked && isCorrect -> Emerald500
                            isPicked && !isCorrect -> Rose500
                            else -> Color.Transparent
                        }

                        Surface(
                            color = containerColor,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable(enabled = !isAnswered) {
                                    onAnswer(q.id, optIdx)
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isPicked) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isPicked && isCorrect) Emerald600 else if (isPicked && !isCorrect) Rose600 else MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                if (isAnswered && isCorrect) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Correct", tint = Emerald600)
                                } else if (isAnswered && isPicked && !isCorrect) {
                                    Icon(imageVector = Icons.Default.Cancel, contentDescription = "Incorrect", tint = Rose500)
                                }
                            }
                        }
                    }

                    if (isAnswered) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Slate100,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "💡 Explanation: ${q.explanation}",
                                style = MaterialTheme.typography.bodySmall.copy(color = Slate700),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 4: VOCABULARY IN CONTEXT
// -------------------------------------------------------------
@Composable
private fun Step4Vocabulary(
    lesson: FullLessonContent,
    audioHelper: AudioHelper
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Essential Scenario Vocabulary",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "واژگان کلیدی این موقعیت همراه با تلفظ، کالوکیشن و ترجمه فارسی",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        lesson.vocabularyList.forEach { vocab ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = vocab.word,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = vocab.partOfSpeech,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Slate600,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                )
                            }
                            Text(
                                text = vocab.pronunciation,
                                style = MaterialTheme.typography.bodySmall.copy(color = Gold600)
                            )
                        }

                        IconButton(
                            onClick = { audioHelper.speak("${vocab.word}. ${vocab.exampleEn}") }
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Gold600)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "• Definition: ${vocab.meaningEn}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                    )

                    PersianTranslationBox(
                        textFa = vocab.meaningFa,
                        label = "معنی فارسی",
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Collocations: ${vocab.collocation}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Sky500
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Example: \"${vocab.exampleEn}\"",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                            )
                            Text(
                                text = vocab.exampleFa,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 5: GRAMMAR IN CONTEXT
// -------------------------------------------------------------
@Composable
private fun Step5Grammar(lesson: FullLessonContent) {
    val grammar = lesson.grammarInContext

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Grammar in Context",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = grammar.topicFa,
            style = MaterialTheme.typography.bodySmall.copy(color = Gold600, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = grammar.topic,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = grammar.coreRule,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Why it matters for migration
                Surface(
                    color = Sky100,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "🌍 Why this matters for migration:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = grammar.whyItMattersForMigration,
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate800)
                        )
                        Text(
                            text = grammar.whyItMattersFa,
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate700, fontSize = 11.sp),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Examples:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
                grammar.examples.forEach { (en, fa) ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = en, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                            Text(text = fa, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), textAlign = TextAlign.Right, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Common Persian Mistake Callout
                Surface(
                    color = Rose100,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Rose500.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "⚠️ Common Mistake by Persian Speakers:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Rose600
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = grammar.commonPersianMistake,
                            style = MaterialTheme.typography.bodySmall.copy(color = Slate800)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "✓ Correct Way: ${grammar.correctForm}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Emerald600
                            )
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 6: SPEAKING PRACTICE
// -------------------------------------------------------------
@Composable
private fun Step6Speaking(
    lesson: FullLessonContent,
    audioHelper: AudioHelper,
    isListening: Boolean,
    latestEvaluation: AICoachEvaluation?,
    isEvaluating: Boolean,
    onEvaluateSpeaking: (String) -> Unit
) {
    var manualInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Speaking & Pronunciation Lab",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "با صدای رسا صحبت کنید و فیدبک هوشمند دریافت کنید",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 14.dp)
        )

        lesson.speakingPractices.forEachIndexed { idx, task ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Task ${idx + 1}: ${task.promptEn}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = task.promptFa,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    Surface(
                        color = Gold100,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🎯 Target Phrase:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Navy900
                                    )
                                )
                                IconButton(
                                    onClick = { audioHelper.speak(task.targetPhrase) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Navy900)
                                }
                            }
                            Text(
                                text = "\"${task.targetPhrase}\"",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Navy900
                                )
                            )
                            Text(
                                text = task.targetPhraseFa,
                                style = MaterialTheme.typography.bodySmall.copy(color = Slate700, fontSize = 11.sp),
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "🗣️ Pronunciation Tip: ${task.pronunciationTips}",
                        style = MaterialTheme.typography.bodySmall.copy(color = Slate600, fontSize = 11.sp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Mic & Speaking Input Box
        Card(
            colors = CardDefaults.cardColors(containerColor = Slate100),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isListening) "Listening to you... Speak now!" else "Tap the microphone to speak aloud",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isListening) Rose500 else Navy900
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                SpeechRecordButton(
                    isListening = isListening,
                    onStart = {
                        audioHelper.startListening { spoken ->
                            onEvaluateSpeaking(spoken)
                        }
                    },
                    onStop = { audioHelper.stopListening() }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Text fallback
                OutlinedTextField(
                    value = manualInput,
                    onValueChange = { manualInput = it },
                    placeholder = { Text("Or type what you practiced speaking...") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        if (manualInput.isNotBlank()) {
                            IconButton(onClick = {
                                onEvaluateSpeaking(manualInput)
                                manualInput = ""
                            }) {
                                Icon(imageVector = Icons.Default.Send, contentDescription = "Evaluate")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (isEvaluating) {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Coach is analyzing pronunciation & structure...", style = MaterialTheme.typography.bodySmall)
            }
        }

        latestEvaluation?.let { eval ->
            Spacer(modifier = Modifier.height(16.dp))
            AICoachFeedbackCard(evaluation = eval)
        }
    }
}

// -------------------------------------------------------------
// STEP 7: READING REAL-WORLD MATERIAL
// -------------------------------------------------------------
@Composable
private fun Step7Reading(lesson: FullLessonContent) {
    val doc = lesson.readingDocument

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Real-World Document Reading",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "درک اسناد واقعی، بندهای قرارداد و ایمیل‌های رسمی",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    color = Navy900,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = doc.type,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(color = Gold400, fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = doc.headline,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = doc.headlineFa,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Document Paper Box
                Surface(
                    color = Slate100,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate300),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = doc.content,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            lineHeight = 20.sp,
                            color = Slate900
                        ),
                        modifier = Modifier.padding(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Key Migration Takeaways (نکات کلیدی):",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Gold600)
                )
                Spacer(modifier = Modifier.height(6.dp))

                doc.keyTakeawaysEn.forEachIndexed { i, en ->
                    val fa = doc.keyTakeawaysFa.getOrNull(i) ?: ""
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "✓ $en", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                            if (fa.isNotBlank()) {
                                Text(
                                    text = fa,
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp),
                                    textAlign = TextAlign.Right,
                                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 8: PRACTICAL WRITING
// -------------------------------------------------------------
@Composable
private fun Step8Writing(
    lesson: FullLessonContent,
    writtenResponse: String,
    isEvaluating: Boolean,
    latestEvaluation: AICoachEvaluation?,
    onWrittenChange: (String) -> Unit,
    onEvaluateWriting: () -> Unit
) {
    val task = lesson.writingTask

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Practical Real-World Writing",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "نگارش ایمیل یا پیام واقعی با بررسی هوشمند خط‌به‌خط",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = task.promptEn,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = task.promptFa,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                )

                // Required keywords
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Keywords:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Gold600)
                    )
                    task.requiredKeywords.forEach { kw ->
                        Surface(
                            color = Gold100,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = kw,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Navy900)
                            )
                        }
                    }
                }

                // Text Input Field
                OutlinedTextField(
                    value = writtenResponse,
                    onValueChange = onWrittenChange,
                    placeholder = { Text("Type your message here...\ne.g., ${task.starterText}") },
                    minLines = 4,
                    maxLines = 8,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("writing_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onEvaluateWriting,
                    enabled = writtenResponse.isNotBlank() && !isEvaluating,
                    colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("evaluate_writing_button")
                ) {
                    if (isEvaluating) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Navy900)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyzing with AI Coach...", color = Navy900)
                    } else {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Navy900)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Get AI Feedback & Correction", color = Navy900, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        latestEvaluation?.let { eval ->
            Spacer(modifier = Modifier.height(16.dp))
            AICoachFeedbackCard(evaluation = eval)

            Spacer(modifier = Modifier.height(14.dp))

            // Model Ideal Answer Comparison
            Card(
                colors = CardDefaults.cardColors(containerColor = Emerald100.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "⭐ Ideal Professional Model Answer:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Emerald600)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.idealModelAnswer,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp, color = Navy900)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STEP 9: REAL-LIFE CHALLENGE (LIVE SIMULATION)
// -------------------------------------------------------------
@Composable
private fun Step9Challenge(
    lesson: FullLessonContent,
    roleplayMessages: List<Pair<String, String>>,
    isListening: Boolean,
    audioHelper: AudioHelper,
    onSendTurn: (String) -> Unit
) {
    val challenge = lesson.finalChallenge
    var userTurnText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Final Challenge: Live Simulation",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = challenge.missionObjectiveFa,
            style = MaterialTheme.typography.bodySmall.copy(color = Gold600, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Surface(
            color = Navy900,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🎭", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = challenge.scenarioTitle,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Gold400)
                    )
                    Text(
                        text = "Partner: ${challenge.partnerRole}",
                        style = MaterialTheme.typography.bodySmall.copy(color = Slate300)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Chat bubble stream
        roleplayMessages.forEach { (speaker, text) ->
            val isUser = speaker == "You"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
            ) {
                Surface(
                    color = if (isUser) Gold500 else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp,
                        bottomStart = if (isUser) 14.dp else 2.dp,
                        bottomEnd = if (isUser) 2.dp else 14.dp
                    ),
                    shadowElevation = if (isUser) 0.dp else 1.dp,
                    modifier = Modifier.widthIn(max = 280.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = speaker,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isUser) Navy900 else MaterialTheme.colorScheme.primary,
                                fontSize = 10.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (isUser) Navy900 else MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Interactive Turn Sender
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (isListening) {
                            audioHelper.stopListening()
                        } else {
                            audioHelper.startListening { spoken ->
                                onSendTurn(spoken)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = "Speak Turn",
                        tint = if (isListening) Rose500 else Gold600
                    )
                }

                OutlinedTextField(
                    value = userTurnText,
                    onValueChange = { userTurnText = it },
                    placeholder = { Text("Speak or type response...") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = {
                        if (userTurnText.isNotBlank()) {
                            onSendTurn(userTurnText)
                            userTurnText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

private fun getStepTitle(step: Int): String {
    return when (step) {
        1 -> "Context & Mission"
        2 -> "Active Listening"
        3 -> "Understanding"
        4 -> "Vocabulary"
        5 -> "Grammar in Context"
        6 -> "Speaking Practice"
        7 -> "Real Documents"
        8 -> "Practical Writing"
        9 -> "Live Simulation"
        else -> "Lesson Step"
    }
}
