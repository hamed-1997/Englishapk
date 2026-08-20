package com.example.data.model

enum class EnglishLevel(val label: String, val descriptionEn: String, val descriptionFa: String) {
    BEGINNER("Beginner", "Just starting with basic words", "آشنایی اندک با کلمات ابتدایی"),
    A1("Elementary (A1)", "Can understand simple everyday phrases", "درک جملات بسیار ساده و روزمره"),
    A2("Pre-Intermediate (A2)", "Can communicate in routine tasks & personal info", "مکالمه در کارهای روزمره و اطلاعات اولیه"),
    B1("Intermediate (B1)", "Can deal with most situations while traveling or living abroad", "درک موضوعات کاری، تحصیلی و مهاجرتی"),
    B2("Upper-Intermediate (B2)", "Can interact fluently with native speakers and work/study", "تسلط در محیط‌های شغلی و دانشگاهی"),
    C1("Advanced (C1)", "Effective operational proficiency in complex situations", "تسلط کامل و روان در موقعیت‌های پیچیده")
}

enum class MigrationGoal(val titleEn: String, val titleFa: String, val icon: String) {
    DAILY_LIFE("Independent Daily Life Abroad", "زندگی مستقل و روزمره", "🏠"),
    WORK_ABROAD("Career & Working in English", "اشتغال و کار تخصصی", "💼"),
    ACADEMIC_STUDY("University & Academic Study", "تحصیل دانشگاهی و آکادمیک", "🎓"),
    BALANCED("Comprehensive Migration Readiness", "مهاجرت جامع (کار + زندگی)", "🌍")
}

enum class StudyTimeGoal(val minutes: Int, val labelEn: String, val labelFa: String) {
    CASUAL(15, "15 min / day", "۱۵ دقیقه در روز (سبک)"),
    REGULAR(30, "30 min / day", "۳۰ دقیقه در روز (متعادل و ایده‌آل)"),
    INTENSIVE(45, "45 min / day", "۴۵ دقیقه در روز (فشرده)"),
    IMMERSION(60, "60 min / day", "۶۰ دقیقه در روز (جهش سریع)")
}

enum class PhaseType(val id: Int, val titleEn: String, val titleFa: String, val subtitle: String) {
    PHASE_1(1, "Essential Foundation", "پایه و نیازهای اولیه مهاجرت", "Core survival communication"),
    PHASE_2(2, "Independent Daily Life Abroad", "زندگی مستقل در خارج از کشور", "Housing, banking, healthcare & services"),
    PHASE_3(3, "Working Abroad", "ارتباطات شغلی و محیط کار", "Interviews, meetings, emails & teamwork"),
    PHASE_4(4, "Academic & University English", "انگلیسی دانشگاهی و تحصیلی", "Lectures, professors, papers & seminars")
}

enum class MasteryStatus(val labelEn: String, val labelFa: String) {
    INTRODUCED("Introduced", "به‌تازگی معرفی‌شده"),
    PRACTICING("Practicing", "در حال تمرین"),
    NEEDS_REVIEW("Needs Review", "نیازمند مرور"),
    WEAK("Weak Point", "نقطه ضعف تکراری"),
    STRONG("Mastered", "مسلط شده")
}

data class LessonStep(
    val stepNumber: Int,
    val title: String,
    val titleFa: String,
    val description: String
)

data class VocabWord(
    val word: String,
    val pronunciation: String,
    val partOfSpeech: String,
    val meaningEn: String,
    val meaningFa: String,
    val exampleEn: String,
    val exampleFa: String,
    val collocation: String
)

data class ComprehensionQuestion(
    val id: Int,
    val question: String,
    val questionFa: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class DialogueLine(
    val speaker: String,
    val speakerRole: String,
    val textEn: String,
    val textFa: String,
    val audioDurationMs: Long = 3000
)

data class GrammarPoint(
    val topic: String,
    val topicFa: String,
    val coreRule: String,
    val whyItMattersForMigration: String,
    val whyItMattersFa: String,
    val examples: List<Pair<String, String>>, // English to Persian
    val commonPersianMistake: String,
    val correctForm: String
)

data class SpeakingTask(
    val promptEn: String,
    val promptFa: String,
    val targetPhrase: String,
    val targetPhraseFa: String,
    val sampleAnswer: String,
    val pronunciationTips: String
)

data class ReadingMaterial(
    val type: String, // "Email", "Lease Contract", "Doctor Note", "Slack Message"
    val headline: String,
    val headlineFa: String,
    val content: String,
    val keyTakeawaysEn: List<String>,
    val keyTakeawaysFa: List<String>
)

data class WritingTask(
    val promptEn: String,
    val promptFa: String,
    val contextDescription: String,
    val starterText: String,
    val requiredKeywords: List<String>,
    val idealModelAnswer: String
)

data class SimulationChallenge(
    val scenarioTitle: String,
    val partnerRole: String,
    val partnerOpeningLine: String,
    val missionObjectiveEn: String,
    val missionObjectiveFa: String,
    val expectedKeywords: List<String>
)

data class FullLessonContent(
    val id: String,
    val phase: PhaseType,
    val categoryIcon: String,
    val titleEn: String,
    val titleFa: String,
    val estimatedMinutes: Int,
    val contextSituationEn: String,
    val contextSituationFa: String,
    val immigrantMission: String,
    // Step 2 & 3: Listening & Understanding
    val listeningDialogue: List<DialogueLine>,
    val comprehensionQuestions: List<ComprehensionQuestion>,
    // Step 4: Vocabulary
    val vocabularyList: List<VocabWord>,
    // Step 5: Grammar in Context
    val grammarInContext: GrammarPoint,
    // Step 6: Speaking
    val speakingPractices: List<SpeakingTask>,
    // Step 7: Reading Real-World Material
    val readingDocument: ReadingMaterial,
    // Step 8: Practical Writing
    val writingTask: WritingTask,
    // Step 9: Final Challenge
    val finalChallenge: SimulationChallenge
)

data class AICoachEvaluation(
    val userText: String,
    val wasUnderstandable: Boolean,
    val grammarCritiqueEn: String,
    val grammarCritiqueFa: String,
    val betterVersion: String,
    val naturalNativeVersion: String,
    val detectedMistakePattern: String? = null,
    val encouragementEn: String,
    val encouragementFa: String,
    val scoreOutOf100: Int
)

data class LearningPlan(
    val primaryGoal: MigrationGoal,
    val currentLevel: EnglishLevel,
    val targetLevel: EnglishLevel,
    val dailyMinutes: Int,
    val workWeightPercent: Int,
    val dailyLifeWeightPercent: Int,
    val academicWeightPercent: Int,
    val generalWeightPercent: Int,
    val recommendedPaceSummary: String
)
