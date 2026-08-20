package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Learner",
    val currentLevel: String = "A2",
    val targetLevel: String = "B2",
    val primaryGoal: String = "WORK_ABROAD",
    val secondaryGoal: String = "DAILY_LIFE",
    val dailyMinutesGoal: Int = 30,
    val isOnboardingCompleted: Boolean = false,
    val currentStreakDays: Int = 3,
    val lastStudyDateMillis: Long = System.currentTimeMillis(),
    val totalStudyMinutes: Int = 45,
    val completedLessonsCount: Int = 0,
    // Skill masteries (0 to 100)
    val listeningScore: Int = 58,
    val speakingScore: Int = 48,
    val readingScore: Int = 65,
    val writingScore: Int = 50,
    // Readiness percentages (0 to 100)
    val dailyLifeReadiness: Int = 62,
    val workReadiness: Int = 45,
    val academicReadiness: Int = 35,
    val currentLessonId: String = "phase2_lesson1_landlord"
)

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: String,
    val phaseNumber: Int,
    val categoryIcon: String,
    val titleEn: String,
    val titleFa: String,
    val estimatedMinutes: Int,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val lastScore: Int = 0,
    val completedAtMillis: Long? = null,
    val currentStepNumber: Int = 1
)

@Entity(tableName = "vocabulary")
data class VocabEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String,
    val pronunciation: String,
    val partOfSpeech: String,
    val meaningEn: String,
    val meaningFa: String,
    val exampleEn: String,
    val exampleFa: String,
    val collocation: String,
    val relatedLessonId: String,
    val masteryStatus: String = "INTRODUCED", // INTRODUCED, PRACTICING, STRONG, NEEDS_REVIEW, WEAK
    val reviewCount: Int = 0,
    val lastReviewedMillis: Long = System.currentTimeMillis(),
    val nextReviewDueMillis: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

@Entity(tableName = "weak_points")
data class WeakPointEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val conceptName: String,
    val category: String, // "Grammar", "Speaking", "Vocabulary", "Pronunciation"
    val errorSummaryEn: String,
    val errorSummaryFa: String,
    val explanationFa: String,
    val mistakeCount: Int = 1,
    val status: String = "NEEDS_REVIEW", // WEAK, NEEDS_REVIEW, PRACTICING, STRONG
    val lastOccurrenceMillis: Long = System.currentTimeMillis(),
    val quickFixLessonId: String? = null
)

@Entity(tableName = "coach_feedback_logs")
data class CoachFeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val scenarioTitle: String,
    val skillType: String, // "Speaking", "Writing", "Roleplay"
    val userSubmission: String,
    val wasUnderstandable: Boolean,
    val critiqueFa: String,
    val betterVersion: String,
    val naturalNativeVersion: String,
    val score: Int
)
