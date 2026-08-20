package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.AudioHelper
import com.example.data.ai.AICoachService
import com.example.data.local.*
import com.example.data.model.*
import com.example.data.repository.EnglishPlusRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EnglishPlusViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EnglishPlusRepository.getInstance(application)
    val audioHelper = AudioHelper(application)

    val userProfile: StateFlow<UserEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allLessons: StateFlow<List<LessonEntity>> = repository.allLessons
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allVocab: StateFlow<List<VocabEntity>> = repository.allVocab
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weakPoints: StateFlow<List<WeakPointEntity>> = repository.weakPoints
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeWeakPoints: StateFlow<List<WeakPointEntity>> = repository.activeWeakPoints
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val coachFeedbackLogs: StateFlow<List<CoachFeedbackEntity>> = repository.coachFeedbackLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Lesson Flow State
    private val _currentActiveLesson = MutableStateFlow<FullLessonContent?>(null)
    val currentActiveLesson: StateFlow<FullLessonContent?> = _currentActiveLesson

    private val _currentLessonStep = MutableStateFlow(1) // 1 to 9
    val currentLessonStep: StateFlow<Int> = _currentLessonStep

    private val _selectedQuizAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedQuizAnswers: StateFlow<Map<Int, Int>> = _selectedQuizAnswers

    private val _writtenResponse = MutableStateFlow("")
    val writtenResponse: StateFlow<String> = _writtenResponse

    private val _spokenResponse = MutableStateFlow("")
    val spokenResponse: StateFlow<String> = _spokenResponse

    private val _isEvaluating = MutableStateFlow(false)
    val isEvaluating: StateFlow<Boolean> = _isEvaluating

    private val _latestEvaluation = MutableStateFlow<AICoachEvaluation?>(null)
    val latestEvaluation: StateFlow<AICoachEvaluation?> = _latestEvaluation

    // Live Scenario Roleplay State
    private val _roleplayMessages = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val roleplayMessages: StateFlow<List<Pair<String, String>>> = _roleplayMessages

    // Quick Practice Dialog State
    private val _quickVocabIndex = MutableStateFlow(0)
    val quickVocabIndex: StateFlow<Int> = _quickVocabIndex

    init {
        viewModelScope.launch {
            repository.initializeIfNeeded()
        }
    }

    fun startLesson(lessonId: String) {
        val lesson = repository.getFullLesson(lessonId)
        _currentActiveLesson.value = lesson
        _currentLessonStep.value = 1
        _selectedQuizAnswers.value = emptyMap()
        _writtenResponse.value = ""
        _spokenResponse.value = ""
        _latestEvaluation.value = null
        _roleplayMessages.value = listOf(
            lesson.finalChallenge.partnerRole to lesson.finalChallenge.partnerOpeningLine
        )
    }

    fun setLessonStep(step: Int) {
        _currentLessonStep.value = step.coerceIn(1, 9)
    }

    fun selectQuizAnswer(questionId: Int, optionIndex: Int) {
        _selectedQuizAnswers.value = _selectedQuizAnswers.value.toMutableMap().apply {
            put(questionId, optionIndex)
        }
    }

    fun updateWrittenResponse(text: String) {
        _writtenResponse.value = text
    }

    fun evaluateWriting() {
        val lesson = _currentActiveLesson.value ?: return
        val text = _writtenResponse.value
        if (text.isBlank()) return

        viewModelScope.launch {
            _isEvaluating.value = true
            val eval = AICoachService.evaluateSubmission(
                scenarioContext = lesson.contextSituationEn,
                promptEn = lesson.writingTask.promptEn,
                userText = text,
                skillType = "Writing"
            )
            _latestEvaluation.value = eval
            repository.saveCoachFeedback(lesson.titleEn, "Writing", text, eval)
            _isEvaluating.value = false
        }
    }

    fun evaluateSpeaking(spokenText: String) {
        val lesson = _currentActiveLesson.value ?: return
        _spokenResponse.value = spokenText
        if (spokenText.isBlank()) return

        viewModelScope.launch {
            _isEvaluating.value = true
            val eval = AICoachService.evaluateSubmission(
                scenarioContext = lesson.contextSituationEn,
                promptEn = "Speaking practice",
                userText = spokenText,
                skillType = "Speaking"
            )
            _latestEvaluation.value = eval
            repository.saveCoachFeedback(lesson.titleEn, "Speaking", spokenText, eval)
            _isEvaluating.value = false
        }
    }

    fun sendRoleplayTurn(userSpeech: String) {
        val lesson = _currentActiveLesson.value ?: return
        if (userSpeech.isBlank()) return

        val currentList = _roleplayMessages.value.toMutableList()
        currentList.add("You" to userSpeech)
        _roleplayMessages.value = currentList

        viewModelScope.launch {
            val partnerResponse = AICoachService.getCoachRoleplayResponse(
                roleTitle = lesson.finalChallenge.partnerRole,
                scenarioGoal = lesson.finalChallenge.missionObjectiveEn,
                conversationHistory = currentList,
                userSpeech = userSpeech
            )
            val updatedList = _roleplayMessages.value.toMutableList()
            updatedList.add(lesson.finalChallenge.partnerRole to partnerResponse)
            _roleplayMessages.value = updatedList

            // Also read out partner response for realistic immersion
            audioHelper.speak(partnerResponse)
        }
    }

    fun completeCurrentLesson() {
        val lesson = _currentActiveLesson.value ?: return
        viewModelScope.launch {
            repository.completeLesson(lesson.id, score = 94, studyMinutes = lesson.estimatedMinutes)
        }
    }

    fun completeOnboarding(
        currentLevel: EnglishLevel,
        targetLevel: EnglishLevel,
        primaryGoal: MigrationGoal,
        secondaryGoal: MigrationGoal,
        dailyMinutes: Int,
        userName: String
    ) {
        viewModelScope.launch {
            repository.completeOnboarding(
                currentLevel,
                targetLevel,
                primaryGoal,
                secondaryGoal,
                dailyMinutes,
                userName
            )
        }
    }

    fun recordVocabReview(vocabId: Long, wasMastered: Boolean) {
        viewModelScope.launch {
            repository.recordVocabReview(vocabId, wasMastered)
        }
    }

    fun resolveWeakPoint(id: Long) {
        viewModelScope.launch {
            repository.resolveWeakPoint(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioHelper.shutdown()
    }
}
