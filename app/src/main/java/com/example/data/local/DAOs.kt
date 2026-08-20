package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileOnce(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)
}

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY phaseNumber ASC, id ASC")
    fun getAllLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: String): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lessons: List<LessonEntity>)

    @Update
    suspend fun update(lesson: LessonEntity)

    @Query("UPDATE lessons SET isCompleted = 1, lastScore = :score, completedAtMillis = :completedTime WHERE id = :lessonId")
    suspend fun markLessonCompleted(lessonId: String, score: Int, completedTime: Long)

    @Query("UPDATE lessons SET currentStepNumber = :stepNumber WHERE id = :lessonId")
    suspend fun updateLessonStep(lessonId: String, stepNumber: Int)
}

@Dao
interface VocabDao {
    @Query("SELECT * FROM vocabulary ORDER BY lastReviewedMillis DESC")
    fun getAllVocab(): Flow<List<VocabEntity>>

    @Query("SELECT * FROM vocabulary WHERE nextReviewDueMillis <= :currentTimeMillis ORDER BY nextReviewDueMillis ASC LIMIT 10")
    fun getDueVocab(currentTimeMillis: Long): Flow<List<VocabEntity>>

    @Query("SELECT * FROM vocabulary WHERE masteryStatus IN ('WEAK', 'NEEDS_REVIEW') ORDER BY reviewCount ASC")
    fun getWeakVocab(): Flow<List<VocabEntity>>

    @Query("SELECT * FROM vocabulary WHERE isFavorite = 1")
    fun getFavoriteVocab(): Flow<List<VocabEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vocabList: List<VocabEntity>)

    @Update
    suspend fun update(vocab: VocabEntity)

    @Query("UPDATE vocabulary SET masteryStatus = :newStatus, reviewCount = reviewCount + 1, lastReviewedMillis = :now, nextReviewDueMillis = :nextDue WHERE id = :id")
    suspend fun recordVocabReview(id: Long, newStatus: String, now: Long, nextDue: Long)
}

@Dao
interface WeakPointDao {
    @Query("SELECT * FROM weak_points ORDER BY mistakeCount DESC")
    fun getAllWeakPoints(): Flow<List<WeakPointEntity>>

    @Query("SELECT * FROM weak_points WHERE status IN ('WEAK', 'NEEDS_REVIEW') ORDER BY mistakeCount DESC LIMIT 5")
    fun getActiveWeakPoints(): Flow<List<WeakPointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weakPoint: WeakPointEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(weakPoints: List<WeakPointEntity>)

    @Update
    suspend fun update(weakPoint: WeakPointEntity)

    @Query("UPDATE weak_points SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)
}

@Dao
interface CoachFeedbackDao {
    @Query("SELECT * FROM coach_feedback_logs ORDER BY timestamp DESC LIMIT 20")
    fun getRecentFeedback(): Flow<List<CoachFeedbackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedback: CoachFeedbackEntity)
}
