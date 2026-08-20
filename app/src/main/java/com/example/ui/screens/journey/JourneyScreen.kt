package com.example.ui.screens.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.local.LessonEntity
import com.example.data.model.PhaseType
import com.example.ui.components.EnglishPlusTopBar
import com.example.ui.theme.*

@Composable
fun JourneyScreen(
    lessons: List<LessonEntity>,
    onSelectLesson: (String) -> Unit
) {
    var selectedPhase by remember { mutableStateOf(PhaseType.PHASE_2) }

    Scaffold(
        topBar = {
            EnglishPlusTopBar(
                title = "Learning Journey",
                subtitle = "Your structured path to English independence"
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Phase Tab Selector
            ScrollableTabRow(
                selectedTabIndex = selectedPhase.id - 1,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                PhaseType.values().forEach { phase ->
                    Tab(
                        selected = phase == selectedPhase,
                        onClick = { selectedPhase = phase },
                        text = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Phase ${phase.id}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = phase.titleEn,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = if (phase == selectedPhase) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    )
                }
            }

            // Phase Overview Header
            Surface(
                color = Navy900,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedPhase.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Gold400
                            )
                        )
                        Surface(
                            color = Navy700,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = selectedPhase.subtitle,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Slate300,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                    Text(
                        text = selectedPhase.titleFa,
                        style = MaterialTheme.typography.bodySmall.copy(color = Gold100),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Lessons Roadmap List
            val phaseLessons = lessons.filter { it.phaseNumber == selectedPhase.id }

            if (phaseLessons.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Curriculum modules unlocking soon!",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(phaseLessons) { lesson ->
                        LessonJourneyCard(
                            lesson = lesson,
                            onLaunch = { onSelectLesson(lesson.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonJourneyCard(
    lesson: LessonEntity,
    onLaunch: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (lesson.isCompleted) Emerald100.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = if (lesson.isCompleted) androidx.compose.foundation.BorderStroke(1.dp, Emerald500) else null,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLaunch() }
            .testTag("journey_lesson_${lesson.id}")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Badge
            Surface(
                color = if (lesson.isCompleted) Emerald500 else Navy700,
                shape = CircleShape,
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (lesson.isCompleted) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Completed", tint = Color.White)
                    } else {
                        Text(text = lesson.categoryIcon, fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.titleEn,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = lesson.titleFa,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⏱️ ${lesson.estimatedMinutes} min",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Slate600,
                            fontSize = 10.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "9 Integrated Skills",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Gold600,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                    if (lesson.isCompleted && lesson.lastScore > 0) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Score: ${lesson.lastScore}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Emerald600,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            IconButton(onClick = onLaunch) {
                Icon(
                    imageVector = if (lesson.isCompleted) Icons.Default.Refresh else Icons.Default.PlayCircle,
                    contentDescription = "Start Lesson",
                    tint = if (lesson.isCompleted) Emerald600 else Gold600,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
