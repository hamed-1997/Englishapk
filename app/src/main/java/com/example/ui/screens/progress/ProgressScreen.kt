package com.example.ui.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserEntity
import com.example.data.local.WeakPointEntity
import com.example.ui.components.EnglishPlusTopBar
import com.example.ui.components.PersianTranslationBox
import com.example.ui.components.ReadinessCard
import com.example.ui.components.SkillProgressBar
import com.example.ui.theme.*

@Composable
fun ProgressScreen(
    user: UserEntity?,
    weakPoints: List<WeakPointEntity>,
    onResolveWeakPoint: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            EnglishPlusTopBar(
                title = "Progress & Diagnostics",
                subtitle = "Immigration readiness, skill masteries & mistake tracker"
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Stats Overview Card
            item {
                Surface(
                    color = Navy900,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(icon = "🔥", label = "Day Streak", value = "${user?.currentStreakDays ?: 3} Days")
                        StatItem(icon = "⏱️", label = "Study Time", value = "${user?.totalStudyMinutes ?: 45} mins")
                        StatItem(icon = "📚", label = "Lessons Done", value = "${user?.completedLessonsCount ?: 2}")
                    }
                }
            }

            // Real-World Immigration Readiness
            item {
                Text(
                    text = "REAL-WORLD IMMIGRATION READINESS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            item {
                ReadinessCard(
                    category = "Independent Daily Life",
                    categoryFa = "زندگی مستقل (مسکن، درمان، خدمات شهری)",
                    percentage = user?.dailyLifeReadiness ?: 62,
                    icon = "🏠",
                    focusTip = "Ready for routine errands. Landlord & emergency communication practicing.",
                    color = Emerald500,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ReadinessCard(
                    category = "Work & Career English",
                    categoryFa = "محیط کار، جلسات و نگارش رسمی",
                    percentage = user?.workReadiness ?: 45,
                    icon = "💼",
                    focusTip = "Focused on daily standup updates and polite modal request phrasing.",
                    color = Gold500,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ReadinessCard(
                    category = "Academic & University English",
                    categoryFa = "تحصیل دانشگاهی، سخنرانی و اساتید",
                    percentage = user?.academicReadiness ?: 35,
                    icon = "🎓",
                    focusTip = "Upcoming: Office hours interactions and seminar discussions.",
                    color = Sky500,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 4 Language Skills
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Core Language Competencies",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Based on CEFR benchmarks and AI Coach evaluations",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            modifier = Modifier.padding(bottom = 14.dp)
                        )

                        SkillProgressBar("Listening Comprehension", "🎧", user?.listeningScore ?: 58, Sky500, Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(10.dp))
                        SkillProgressBar("Speaking Fluency & Accent", "🗣️", user?.speakingScore ?: 48, Gold500, Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(10.dp))
                        SkillProgressBar("Reading Authentic Text", "📖", user?.readingScore ?: 65, Emerald500, Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(10.dp))
                        SkillProgressBar("Practical Writing & Grammar", "✍️", user?.writingScore ?: 50, Rose500, Modifier.fillMaxWidth())
                    }
                }
            }

            // Diagnostic Mistake Tracker
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DETECTED WEAK POINTS & PERSIAN TRANSFER MISTAKES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            items(weakPoints) { wp ->
                val isResolved = wp.status == "STRONG"
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isResolved) Emerald100.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = wp.conceptName,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isResolved) Emerald600 else MaterialTheme.colorScheme.primary
                                )
                            )

                            Surface(
                                color = if (isResolved) Emerald100 else Rose100,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (isResolved) "Mastered ✓" else "${wp.mistakeCount} occurrences",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isResolved) Emerald600 else Rose600,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "❌ Error pattern: ${wp.errorSummaryEn}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Rose600, fontWeight = FontWeight.Medium)
                        )

                        PersianTranslationBox(
                            textFa = wp.explanationFa,
                            label = "توضیح فارسی مربی هوشمند",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        if (!isResolved) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = { onResolveWeakPoint(wp.id) }
                                ) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Emerald600, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Mark as Understood", color = Emerald600, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(icon: String, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = icon, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Gold400))
        Text(text = label, style = MaterialTheme.typography.bodySmall.copy(color = Slate400, fontSize = 10.sp))
    }
}
