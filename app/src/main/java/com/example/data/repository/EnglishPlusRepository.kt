package com.example.data.repository

import android.content.Context
import com.example.data.local.*
import com.example.data.model.*
import com.example.data.seed.CurriculumSeed
import com.example.data.seed.LessonRepositoryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class EnglishPlusRepository(private val db: AppDatabase) {

    val userProfile: Flow<UserEntity?> = db.userDao().getUserProfile()
    val allLessons: Flow<List<LessonEntity>> = db.lessonDao().getAllLessons()
    val allVocab: Flow<List<VocabEntity>> = db.vocabDao().getAllVocab()
    val weakPoints: Flow<List<WeakPointEntity>> = db.weakPointDao().getAllWeakPoints()
    val activeWeakPoints: Flow<List<WeakPointEntity>> = db.weakPointDao().getActiveWeakPoints()
    val coachFeedbackLogs: Flow<List<CoachFeedbackEntity>> = db.coachFeedbackDao().getRecentFeedback()

    suspend fun initializeIfNeeded() = withContext(Dispatchers.IO) {
        val existingUser = db.userDao().getUserProfileOnce()
        if (existingUser == null) {
            db.userDao().insertOrUpdate(UserEntity())
            db.lessonDao().insertAll(CurriculumSeed.INITIAL_LESSONS)
            db.vocabDao().insertAll(CurriculumSeed.INITIAL_VOCAB)
            db.weakPointDao().insertAll(CurriculumSeed.INITIAL_WEAK_POINTS)
        }
    }

    suspend fun completeOnboarding(
        currentLevel: EnglishLevel,
        targetLevel: EnglishLevel,
        primaryGoal: MigrationGoal,
        secondaryGoal: MigrationGoal,
        dailyMinutes: Int,
        userName: String
    ) = withContext(Dispatchers.IO) {
        val existing = db.userDao().getUserProfileOnce() ?: UserEntity()
        val updated = existing.copy(
            name = userName.ifBlank { "Learner" },
            currentLevel = currentLevel.name,
            targetLevel = targetLevel.name,
            primaryGoal = primaryGoal.name,
            secondaryGoal = secondaryGoal.name,
            dailyMinutesGoal = dailyMinutes,
            isOnboardingCompleted = true,
            currentLessonId = when (currentLevel) {
                EnglishLevel.BEGINNER, EnglishLevel.A1 -> "phase1_lesson3_directions"
                EnglishLevel.A2 -> "phase2_lesson1_landlord"
                EnglishLevel.B1 -> "phase3_lesson1_firstday"
                EnglishLevel.B2, EnglishLevel.C1 -> "phase4_lesson1_prof_office"
            }
        )
        db.userDao().insertOrUpdate(updated)
    }

    suspend fun completeLesson(
        lessonId: String,
        score: Int,
        studyMinutes: Int
    ) = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        db.lessonDao().markLessonCompleted(lessonId, score, now)

        val user = db.userDao().getUserProfileOnce() ?: UserEntity()
        val allLessonsList = db.lessonDao().getAllLessons().firstOrNull() ?: emptyList()
        val nextLesson = allLessonsList.firstOrNull { it.id != lessonId && !it.isCompleted }

        // Boost readiness & skill stats smoothly
        val newListening = (user.listeningScore + 4).coerceAtMost(100)
        val newSpeaking = (user.speakingScore + 5).coerceAtMost(100)
        val newReading = (user.readingScore + 3).coerceAtMost(100)
        val newWriting = (user.writingScore + 4).coerceAtMost(100)

        val isDailyLife = lessonId.contains("phase2") || lessonId.contains("phase1")
        val isWork = lessonId.contains("phase3")
        val isAcademic = lessonId.contains("phase4")

        val newDailyReadiness = if (isDailyLife) (user.dailyLifeReadiness + 6).coerceAtMost(100) else user.dailyLifeReadiness
        val newWorkReadiness = if (isWork) (user.workReadiness + 7).coerceAtMost(100) else user.workReadiness
        val newAcademicReadiness = if (isAcademic) (user.academicReadiness + 8).coerceAtMost(100) else user.academicReadiness

        val updatedUser = user.copy(
            completedLessonsCount = user.completedLessonsCount + 1,
            totalStudyMinutes = user.totalStudyMinutes + studyMinutes,
            lastStudyDateMillis = now,
            currentStreakDays = user.currentStreakDays + 1,
            listeningScore = newListening,
            speakingScore = newSpeaking,
            readingScore = newReading,
            writingScore = newWriting,
            dailyLifeReadiness = newDailyReadiness,
            workReadiness = newWorkReadiness,
            academicReadiness = newAcademicReadiness,
            currentLessonId = nextLesson?.id ?: lessonId
        )
        db.userDao().insertOrUpdate(updatedUser)
    }

    suspend fun recordVocabReview(vocabId: Long, wasMastered: Boolean) = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val newStatus = if (wasMastered) "STRONG" else "NEEDS_REVIEW"
        val intervalDays = if (wasMastered) 4 else 1
        val nextDue = now + (intervalDays * 86400000L)
        db.vocabDao().recordVocabReview(vocabId, newStatus, now, nextDue)
    }

    suspend fun saveCoachFeedback(
        scenarioTitle: String,
        skillType: String,
        userSubmission: String,
        eval: AICoachEvaluation
    ) = withContext(Dispatchers.IO) {
        val feedback = CoachFeedbackEntity(
            scenarioTitle = scenarioTitle,
            skillType = skillType,
            userSubmission = userSubmission,
            wasUnderstandable = eval.wasUnderstandable,
            critiqueFa = eval.grammarCritiqueFa,
            betterVersion = eval.betterVersion,
            naturalNativeVersion = eval.naturalNativeVersion,
            score = eval.scoreOutOf100
        )
        db.coachFeedbackDao().insert(feedback)

        // If mistake pattern detected, log/update weak point
        eval.detectedMistakePattern?.let { pattern ->
            val existing = db.weakPointDao().getActiveWeakPoints().firstOrNull()?.find { it.conceptName.contains(pattern, ignoreCase = true) }
            if (existing != null) {
                db.weakPointDao().update(existing.copy(mistakeCount = existing.mistakeCount + 1, status = "WEAK"))
            } else {
                db.weakPointDao().insert(
                    WeakPointEntity(
                        conceptName = pattern,
                        category = skillType,
                        errorSummaryEn = eval.grammarCritiqueEn,
                        errorSummaryFa = eval.grammarCritiqueFa,
                        explanationFa = eval.grammarCritiqueFa,
                        mistakeCount = 1,
                        status = "NEEDS_REVIEW"
                    )
                )
            }
        }
    }

    suspend fun resolveWeakPoint(id: Long) = withContext(Dispatchers.IO) {
        db.weakPointDao().updateStatus(id, "STRONG")
    }

    fun getFullLesson(lessonId: String): FullLessonContent {
        return LessonRepositoryData.getLessonById(lessonId)
    }

    companion object {
        @Volatile
        private var INSTANCE: EnglishPlusRepository? = null

        fun getInstance(context: Context): EnglishPlusRepository {
            return INSTANCE ?: synchronized(this) {
                val db = AppDatabase.getDatabase(context)
                val instance = EnglishPlusRepository(db)
                INSTANCE = instance
                instance
            }
        }
    }
}
