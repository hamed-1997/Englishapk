package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.data.local.LessonEntity
import com.example.data.local.UserEntity
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.journey.JourneyScreen
import com.example.ui.screens.lesson.LessonScreen
import com.example.ui.screens.library.LibraryScreen
import com.example.ui.screens.onboarding.OnboardingScreen
import com.example.ui.screens.practice.PracticeScreen
import com.example.ui.screens.progress.ProgressScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.EnglishPlusViewModel

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Main : Screen("main")
    object Lesson : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: String) = "lesson/$lessonId"
    }
}

enum class NavigationTab(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    JOURNEY("journey", "Journey", Icons.Filled.Map, Icons.Outlined.Map),
    PRACTICE("practice", "AI Coach", Icons.Filled.Psychology, Icons.Outlined.Psychology),
    LIBRARY("library", "Library", Icons.Filled.LocalLibrary, Icons.Outlined.LocalLibrary),
    PROGRESS("progress", "Progress", Icons.Filled.Analytics, Icons.Outlined.Analytics)
}

@Composable
fun AppNavigation(
    viewModel: EnglishPlusViewModel,
    navController: NavHostController = rememberNavController()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val allLessons by viewModel.allLessons.collectAsState()
    val allVocab by viewModel.allVocab.collectAsState()
    val weakPoints by viewModel.weakPoints.collectAsState()
    val activeWeakPoints by viewModel.activeWeakPoints.collectAsState()

    // Determine initial destination
    val startDestination = if (userProfile?.isOnboardingCompleted == true) Screen.Main.route else Screen.Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onCompleteOnboarding = { currentLevel, targetLevel, primaryGoal, secondaryGoal, dailyMinutes, name ->
                    viewModel.completeOnboarding(
                        currentLevel,
                        targetLevel,
                        primaryGoal,
                        secondaryGoal,
                        dailyMinutes,
                        name
                    )
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainTabsScaffold(
                viewModel = viewModel,
                userProfile = userProfile,
                allLessons = allLessons,
                onStartLesson = { lessonId ->
                    viewModel.startLesson(lessonId)
                    navController.navigate(Screen.Lesson.createRoute(lessonId))
                }
            )
        }

        composable(Screen.Lesson.route) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "phase2_lesson1_landlord"
            val activeLesson by viewModel.currentActiveLesson.collectAsState()
            val currentStep by viewModel.currentLessonStep.collectAsState()
            val selectedAnswers by viewModel.selectedQuizAnswers.collectAsState()
            val writtenResponse by viewModel.writtenResponse.collectAsState()
            val spokenResponse by viewModel.spokenResponse.collectAsState()
            val isEvaluating by viewModel.isEvaluating.collectAsState()
            val latestEvaluation by viewModel.latestEvaluation.collectAsState()
            val roleplayMessages by viewModel.roleplayMessages.collectAsState()

            activeLesson?.let { lesson ->
                LessonScreen(
                    lesson = lesson,
                    audioHelper = viewModel.audioHelper,
                    currentStep = currentStep,
                    selectedQuizAnswers = selectedAnswers,
                    writtenResponse = writtenResponse,
                    spokenResponse = spokenResponse,
                    isEvaluating = isEvaluating,
                    latestEvaluation = latestEvaluation,
                    roleplayMessages = roleplayMessages,
                    onStepChange = { viewModel.setLessonStep(it) },
                    onQuizAnswer = { qId, optIdx -> viewModel.selectQuizAnswer(qId, optIdx) },
                    onWrittenChange = { viewModel.updateWrittenResponse(it) },
                    onEvaluateWriting = { viewModel.evaluateWriting() },
                    onEvaluateSpeaking = { viewModel.evaluateSpeaking(it) },
                    onSendRoleplay = { viewModel.sendRoleplayTurn(it) },
                    onCompleteLesson = { viewModel.completeCurrentLesson() },
                    onCloseLesson = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun MainTabsScaffold(
    viewModel: EnglishPlusViewModel,
    userProfile: UserEntity?,
    allLessons: List<LessonEntity>,
    onStartLesson: (String) -> Unit
) {
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavigationTab.HOME.route

    val allVocab by viewModel.allVocab.collectAsState()
    val weakPoints by viewModel.weakPoints.collectAsState()
    val activeWeakPoints by viewModel.activeWeakPoints.collectAsState()

    val nextLesson = remember(allLessons, userProfile) {
        val targetId = userProfile?.currentLessonId ?: "phase2_lesson1_landlord"
        allLessons.find { it.id == targetId } ?: allLessons.firstOrNull { !it.isCompleted } ?: allLessons.firstOrNull()
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                NavigationTab.values().forEach { tab ->
                    val isSelected = currentRoute == tab.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != tab.route) {
                                tabNavController.navigate(tab.route) {
                                    popUpTo(tabNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title,
                                tint = if (isSelected) Gold600 else Slate400
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Gold600 else Slate400
                                )
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = NavigationTab.HOME.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationTab.HOME.route) {
                HomeScreen(
                    user = userProfile,
                    nextLesson = nextLesson,
                    activeWeakPoints = activeWeakPoints,
                    onStartLesson = onStartLesson,
                    onNavigateToJourney = { tabNavController.navigate(NavigationTab.JOURNEY.route) },
                    onNavigateToPractice = { tabNavController.navigate(NavigationTab.PRACTICE.route) },
                    onNavigateToLibrary = { tabNavController.navigate(NavigationTab.LIBRARY.route) },
                    onNavigateToProgress = { tabNavController.navigate(NavigationTab.PROGRESS.route) }
                )
            }

            composable(NavigationTab.JOURNEY.route) {
                JourneyScreen(
                    lessons = allLessons,
                    onSelectLesson = onStartLesson
                )
            }

            composable(NavigationTab.PRACTICE.route) {
                PracticeScreen(
                    audioHelper = viewModel.audioHelper,
                    onStartLesson = onStartLesson
                )
            }

            composable(NavigationTab.LIBRARY.route) {
                LibraryScreen(
                    vocabList = allVocab,
                    audioHelper = viewModel.audioHelper,
                    onRecordVocabReview = { id, mastered -> viewModel.recordVocabReview(id, mastered) }
                )
            }

            composable(NavigationTab.PROGRESS.route) {
                ProgressScreen(
                    user = userProfile,
                    weakPoints = weakPoints,
                    onResolveWeakPoint = { id -> viewModel.resolveWeakPoint(id) }
                )
            }
        }
    }
}
