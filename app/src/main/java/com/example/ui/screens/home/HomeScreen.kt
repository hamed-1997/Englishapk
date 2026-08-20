package com.example.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.LessonEntity
import com.example.data.local.UserEntity
import com.example.data.local.WeakPointEntity
import com.example.ui.components.EnglishPlusTopBar
import com.example.ui.components.PersianTranslationBox
import com.example.ui.components.ReadinessCard
import com.example.ui.components.SkillProgressBar
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    user: UserEntity?,
    nextLesson: LessonEntity?,
    activeWeakPoints: List<WeakPointEntity>,
    onStartLesson: (String) -> Unit,
    onNavigateToJourney: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    var showQuickVocabDialog by remember { mutableStateOf(false) }
    var showSpeakingChallengeDialog by remember { mutableStateOf(false) }
    var showWeakPointsDialog by remember { mutableStateOf(false) }

    val currentLessonId = nextLesson?.id ?: "phase2_lesson1_landlord"
    val lessonTitle = nextLesson?.titleEn ?: "Talking to Your Landlord: Reporting a Leak"
    val lessonTitleFa = nextLesson?.titleFa ?: "گفتگو با صاحب‌خانه: گزارش نشتی و خرابی"
    val lessonMinutes = nextLesson?.estimatedMinutes ?: 25
    val lessonCategoryIcon = nextLesson?.categoryIcon ?: "🏠"

    Scaffold(
        topBar = {
            EnglishPlusTopBar(
                title = "English+",
                subtitle = "Building your future abroad step by step",
                streakDays = user?.currentStreakDays ?: 3,
                onStreakClick = onNavigateToProgress
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Calm Personalized Greeting
            Text(
                text = "Good day, ${user?.name ?: "Learner"} 👋",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "You are preparing for independence in an English-speaking country.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ==================== PRIMARY HERO: YOUR NEXT STEP IS READY ====================
            Text(
                text = "YOUR NEXT STEP",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp,
                    color = Gold600
                )
            )
            Spacer(modifier = Modifier.height(6.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("today_lesson_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Navy700,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = lessonCategoryIcon, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "TODAY'S MAIN LESSON",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Gold400,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Slate300,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$lessonMinutes min",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Slate300,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = lessonTitle,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 28.sp
                        )
                    )
                    Text(
                        text = lessonTitleFa,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Gold100,
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    // Integrated 4-Skills Badge Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SkillChip(icon = "🎧", label = "Listen")
                        SkillChip(icon = "🗣️", label = "Speak")
                        SkillChip(icon = "📖", label = "Read")
                        SkillChip(icon = "✍️", label = "Write")
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = { onStartLesson(currentLessonId) },
                        colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("continue_learning_button")
                    ) {
                        Text(
                            text = "Continue Learning →",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== QUICK PRACTICE ====================
            Text(
                text = "QUICK PRACTICE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickPracticeCard(
                    icon = "🧠",
                    title = "5-Min Vocab",
                    subtitle = "SRS Review",
                    color = Sky500,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToLibrary
                )
                QuickPracticeCard(
                    icon = "🗣️",
                    title = "Speech Lab",
                    subtitle = "Fluency Drill",
                    color = Emerald500,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToPractice
                )
                QuickPracticeCard(
                    icon = "🎯",
                    title = "Weak Points",
                    subtitle = "${activeWeakPoints.size} to Fix",
                    color = Rose500,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToProgress
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== IMMIGRATION READINESS ====================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REAL-WORLD IMMIGRATION READINESS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "View All →",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Gold600
                    ),
                    modifier = Modifier.clickable { onNavigateToProgress() }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            ReadinessCard(
                category = "Independent Daily Life",
                categoryFa = "زندگی مستقل (مسکن، درمان، خدمات)",
                percentage = user?.dailyLifeReadiness ?: 62,
                icon = "🏠",
                focusTip = "Next focus: Landlord communications & clinic walk-ins.",
                color = Emerald500,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            ReadinessCard(
                category = "Work & Career English",
                categoryFa = "محیط کار و ارتباطات حرفه‌ای",
                percentage = user?.workReadiness ?: 45,
                icon = "💼",
                focusTip = "Next focus: Standup updates and polite email phrasing.",
                color = Gold500,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== 4 SKILLS BREAKDOWN ====================
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Core Language Skills",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Current Level: ${user?.currentLevel ?: "A2"} → Target: ${user?.targetLevel ?: "B2"}",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    SkillProgressBar(
                        title = "Listening Comprehension",
                        icon = "🎧",
                        score = user?.listeningScore ?: 58,
                        color = Sky500,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    SkillProgressBar(
                        title = "Speaking & Conversational Confidence",
                        icon = "🗣️",
                        score = user?.speakingScore ?: 48,
                        color = Gold500,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    SkillProgressBar(
                        title = "Reading Real-World Documents",
                        icon = "📖",
                        score = user?.readingScore ?: 65,
                        color = Emerald500,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    SkillProgressBar(
                        title = "Practical Workplace Writing",
                        icon = "✍️",
                        score = user?.writingScore ?: 50,
                        color = Rose500,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Coach Rayan's Insight Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate100),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🤖", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "AI Coach Rayan's Note",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                        )
                        Text(
                            text = "«شما منظورتان را به خوبی می‌رسانید. امروز روی استفاده از for به جای from برای بیان مدت زمان بیماری تمرکز داریم.»",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Slate700,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SkillChip(icon: String, label: String) {
    Surface(
        color = Navy700,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 11.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Slate300,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun QuickPracticeCard(
    icon: String,
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = color.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = icon, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
