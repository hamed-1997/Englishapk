package com.example.ui.screens.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.EnglishLevel
import com.example.data.model.MigrationGoal
import com.example.data.model.StudyTimeGoal
import com.example.ui.components.PersianTranslationBox
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
    onCompleteOnboarding: (
        currentLevel: EnglishLevel,
        targetLevel: EnglishLevel,
        primaryGoal: MigrationGoal,
        secondaryGoal: MigrationGoal,
        dailyMinutes: Int,
        name: String
    ) -> Unit
) {
    var step by remember { mutableStateOf(0) }
    var userName by remember { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf(MigrationGoal.WORK_ABROAD) }
    var selectedCurrentLevel by remember { mutableStateOf(EnglishLevel.A2) }
    var selectedTargetLevel by remember { mutableStateOf(EnglishLevel.B2) }
    var selectedStudyTime by remember { mutableStateOf(StudyTimeGoal.REGULAR) }

    val totalSteps = 4

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (step > 0) {
                        OutlinedButton(
                            onClick = { step-- },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("onboarding_back_button")
                        ) {
                            Text("Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Button(
                        onClick = {
                            if (step < totalSteps - 1) {
                                step++
                            } else {
                                onCompleteOnboarding(
                                    selectedCurrentLevel,
                                    selectedTargetLevel,
                                    selectedGoal,
                                    MigrationGoal.DAILY_LIFE,
                                    selectedStudyTime.minutes,
                                    userName
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("onboarding_next_button")
                    ) {
                        Text(
                            text = if (step == totalSteps - 1) "Start My Learning Journey 🚀" else "Continue →",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Progress Dots
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(totalSteps) { idx ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(6.dp)
                            .width(if (idx == step) 28.dp else 12.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (idx <= step) Gold500 else Slate300)
                    )
                }
            }

            AnimatedContent(
                targetState = step,
                label = "onboarding_step"
            ) { currentStep ->
                when (currentStep) {
                    0 -> WelcomeStep(
                        name = userName,
                        onNameChange = { userName = it },
                        selectedGoal = selectedGoal,
                        onGoalSelect = { selectedGoal = it }
                    )
                    1 -> LevelAssessmentStep(
                        currentLevel = selectedCurrentLevel,
                        onCurrentSelect = { selectedCurrentLevel = it },
                        targetLevel = selectedTargetLevel,
                        onTargetSelect = { selectedTargetLevel = it }
                    )
                    2 -> TimeCommitmentStep(
                        selectedTime = selectedStudyTime,
                        onSelectTime = { selectedStudyTime = it }
                    )
                    3 -> PersonalizedPlanSummaryStep(
                        name = userName,
                        goal = selectedGoal,
                        currentLevel = selectedCurrentLevel,
                        targetLevel = selectedTargetLevel,
                        time = selectedStudyTime
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeStep(
    name: String,
    onNameChange: (String) -> Unit,
    selectedGoal: MigrationGoal,
    onGoalSelect: (MigrationGoal) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Image
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hero_journey),
                contentDescription = "Immigration English Journey",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Welcome to English+",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "راهنمای هوشمند مهاجرت و زندگی مستقل به زبان انگلیسی",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Gold600,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("What is your name? (نام شما)") },
            placeholder = { Text("e.g. Ali, Sara...") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("name_input")
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "What is your main migration goal?",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "هدف اصلی شما از یادگیری چیست؟",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        MigrationGoal.values().forEach { goal ->
            val isSelected = goal == selectedGoal
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) Gold100 else MaterialTheme.colorScheme.surface,
                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Gold500) else androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onGoalSelect(goal) }
                    .testTag("goal_${goal.name}")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = goal.icon, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = goal.titleEn,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Navy900 else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = goal.titleFa,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isSelected) Slate700 else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = Gold600
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelAssessmentStep(
    currentLevel: EnglishLevel,
    onCurrentSelect: (EnglishLevel) -> Unit,
    targetLevel: EnglishLevel,
    onTargetSelect: (EnglishLevel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Your English Level Assessment",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "سطح فعلی و سطح هدف خود را مشخص کنید",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Current English Level (سطح فعلی):",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        listOf(EnglishLevel.BEGINNER, EnglishLevel.A1, EnglishLevel.A2, EnglishLevel.B1, EnglishLevel.B2).forEach { level ->
            val isSelected = level == currentLevel
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) Sky100 else MaterialTheme.colorScheme.surface,
                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Sky500) else androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onCurrentSelect(level) }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = level.label,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Navy900 else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = "${level.descriptionEn} • ${level.descriptionFa}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = if (isSelected) Slate700 else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    if (isSelected) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Sky500)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Target Level (سطح هدف برای مهاجرت):",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(EnglishLevel.B1, EnglishLevel.B2, EnglishLevel.C1).forEach { target ->
                val isSelected = target == targetLevel
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Emerald100 else MaterialTheme.colorScheme.surface,
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Emerald500) else androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTargetSelect(target) }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = target.label.split(" ")[0],
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Emerald600 else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = if (target == EnglishLevel.B2) "Work/Uni" else if (target == EnglishLevel.C1) "Fluent" else "Independent",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeCommitmentStep(
    selectedTime: StudyTimeGoal,
    onSelectTime: (StudyTimeGoal) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Daily Study Commitment",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "میزان زمانی که در طول روز می‌توانید به تمرین اختصاص دهید",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        StudyTimeGoal.values().forEach { goal ->
            val isSelected = goal == selectedTime
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) Gold100 else MaterialTheme.colorScheme.surface,
                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Gold500) else androidx.compose.foundation.BorderStroke(1.dp, Slate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelectTime(goal) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = if (isSelected) Gold600 else Slate400,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = goal.labelEn,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Navy900 else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = goal.labelFa,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isSelected) Slate700 else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    if (isSelected) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Gold600)
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonalizedPlanSummaryStep(
    name: String,
    goal: MigrationGoal,
    currentLevel: EnglishLevel,
    targetLevel: EnglishLevel,
    time: StudyTimeGoal
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = Navy900,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✨", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your Personalized Learning Plan",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Gold400
                        )
                    )
                }
                Text(
                    text = "برنامه شخصی‌سازی‌شده برای ${if (name.isNotBlank()) name else "شما"}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Slate300),
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                Divider(color = Slate700)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Current Level", style = MaterialTheme.typography.labelSmall.copy(color = Slate400))
                        Text(currentLevel.name, style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold))
                    }
                    Column {
                        Text("Target Level", style = MaterialTheme.typography.labelSmall.copy(color = Slate400))
                        Text(targetLevel.name, style = MaterialTheme.typography.titleMedium.copy(color = Emerald500, fontWeight = FontWeight.Bold))
                    }
                    Column {
                        Text("Pace", style = MaterialTheme.typography.labelSmall.copy(color = Slate400))
                        Text("${time.minutes}m/day", style = MaterialTheme.typography.titleMedium.copy(color = Gold400, fontWeight = FontWeight.Bold))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Curriculum Weight Breakdown (ترکیب مهارت‌ها):",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        val workPercent = if (goal == MigrationGoal.WORK_ABROAD) 45 else 30
        val dailyPercent = if (goal == MigrationGoal.DAILY_LIFE) 65 else if (goal == MigrationGoal.WORK_ABROAD) 40 else 35
        val academicPercent = if (goal == MigrationGoal.ACADEMIC_STUDY) 50 else 15
        val generalPercent = 100 - workPercent - dailyPercent - academicPercent

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                PlanWeightRow("🏠 Independent Daily Life", "$dailyPercent%", Emerald500)
                Spacer(modifier = Modifier.height(8.dp))
                PlanWeightRow("💼 Work & Career Communication", "$workPercent%", Gold500)
                Spacer(modifier = Modifier.height(8.dp))
                PlanWeightRow("🎓 Academic & University", "$academicPercent%", Sky500)
                Spacer(modifier = Modifier.height(8.dp))
                PlanWeightRow("⚡ Grammar & Weak Points Fix", "$generalPercent%", Rose500)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        PersianTranslationBox(
            textFa = "سیستم هوشمند English+ با توجه به سطح ${currentLevel.name} و هدف شما، گام‌های آموزشی را مرحله‌به‌مرحله آماده کرده است. شما هیچ‌وقت سردرگم نخواهید شد و در هر جلسه هر ۴ مهارت را همزمان تمرین می‌کنید.",
            label = "پیام مربی هوشمند (Coach Rayan)"
        )
    }
}

@Composable
private fun PlanWeightRow(label: String, percent: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
        }
        Text(percent, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = color))
    }
}
