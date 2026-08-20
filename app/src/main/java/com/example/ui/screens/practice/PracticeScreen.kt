package com.example.ui.screens.practice

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
import com.example.audio.AudioHelper
import com.example.data.ai.AICoachService
import com.example.data.model.AICoachEvaluation
import com.example.ui.components.AICoachFeedbackCard
import com.example.ui.components.EnglishPlusTopBar
import com.example.ui.components.SpeechRecordButton
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class PracticeScenario(
    val id: String,
    val icon: String,
    val title: String,
    val titleFa: String,
    val category: String,
    val description: String,
    val starterPrompt: String,
    val partnerRole: String
)

@Composable
fun PracticeScreen(
    audioHelper: AudioHelper,
    onStartLesson: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) } // 0: AI Roleplay, 1: Speech Lab, 2: Writing Clinic

    val isListening by audioHelper.isListening.collectAsState()

    val scenarios = listOf(
        PracticeScenario(
            id = "sc_landlord",
            icon = "🏠",
            title = "Emergency Water Leak at Rental",
            titleFa = "گزارش خرابی آب به صاحب‌خانه",
            category = "Daily Life",
            description = "Explain the leaking pipe under the sink and schedule immediate repairs.",
            starterPrompt = "Hello Mr. Henderson, this is Ali. There is water leaking under my kitchen sink.",
            partnerRole = "Landlord (Mr. Henderson)"
        ),
        PracticeScenario(
            id = "sc_doctor",
            icon = "🏥",
            title = "Walk-in Clinic Consultation",
            titleFa = "ویزیت پزشک و شرح علائم بیماری",
            category = "Daily Life",
            description = "Describe your fever, headache duration, and ask for prescription advice.",
            starterPrompt = "Good morning Doctor. I have had a sore throat and fever for three days.",
            partnerRole = "Doctor (Dr. Patel)"
        ),
        PracticeScenario(
            id = "sc_standup",
            icon = "💼",
            title = "Workplace Standup Task Update",
            titleFa = "جلسه کوتاه کاری و ارائه وضعیت تسک‌ها",
            category = "Work Abroad",
            description = "Explain what you completed yesterday, what you are working on today, and if you are blocked.",
            starterPrompt = "Good morning everyone. Yesterday I completed the API integration, today I will work on UI testing.",
            partnerRole = "Team Lead (Sarah)"
        ),
        PracticeScenario(
            id = "sc_bank",
            icon = "🏦",
            title = "Opening a Bank Checking Account",
            titleFa = "افتتاح حساب بانکی و کارت نقدی",
            category = "Daily Life",
            description = "Ask about monthly account fees, debit card arrival, and wire transfers.",
            starterPrompt = "Hi, I would like to open a checking account and set up direct deposit for my salary.",
            partnerRole = "Bank Teller (James)"
        ),
        PracticeScenario(
            id = "sc_prof",
            icon = "🎓",
            title = "Professor Office Hours Discussion",
            titleFa = "مراجعه به دفتر استاد برای رفع اشکال",
            category = "Academic",
            description = "Ask for clarification on assignment requirements and seminar deadlines.",
            starterPrompt = "Professor, could you please clarify the research methodology expected for the midterm essay?",
            partnerRole = "Professor (Dr. Adams)"
        )
    )

    var activeScenario by remember { mutableStateOf<PracticeScenario?>(null) }
    var userSpeechInput by remember { mutableStateOf("") }
    var activeEvaluation by remember { mutableStateOf<AICoachEvaluation?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            EnglishPlusTopBar(
                title = "AI Coach Lab",
                subtitle = "Real-world conversational simulations & speech feedback"
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("🎭 AI Roleplay", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("🗣️ Speech Lab", fontWeight = FontWeight.Bold) }
                )
            }

            if (activeScenario == null) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Surface(
                            color = Navy900,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Select a Live Immigration Simulation",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Gold400
                                    )
                                )
                                Text(
                                    text = "با هوش مصنوعی در موقعیت‌های واقعی خارج از کشور تمرین کنید و خطاهای رایج فارسی‌زبانان را اصلاح نمایید.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Slate300),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    items(scenarios) { scenario ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    activeScenario = scenario
                                    userSpeechInput = scenario.starterPrompt
                                    activeEvaluation = null
                                }
                                .testTag("scenario_${scenario.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Navy700,
                                    shape = CircleShape,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = scenario.icon, fontSize = 20.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = scenario.title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = scenario.titleFa,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                    Text(
                                        text = scenario.description,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Slate600,
                                            fontSize = 11.sp
                                        ),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Start",
                                    tint = Gold600
                                )
                            }
                        }
                    }
                }
            } else {
                // Active Scenario Lab View
                val current = activeScenario!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { activeScenario = null },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("← Back to Scenarios")
                        }
                        Text(
                            text = current.category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Gold600
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = current.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = current.titleFa,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input / Speech Box
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Your Spoken / Written Response:",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = userSpeechInput,
                                onValueChange = { userSpeechInput = it },
                                minLines = 3,
                                maxLines = 5,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            if (isListening) {
                                                audioHelper.stopListening()
                                            } else {
                                                audioHelper.startListening { spoken ->
                                                    userSpeechInput = spoken
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                                            contentDescription = "Speak",
                                            tint = if (isListening) Rose500 else Gold600
                                        )
                                    }
                                    if (userSpeechInput.isNotBlank()) {
                                        IconButton(
                                            onClick = { audioHelper.speak(userSpeechInput) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Listen",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }

                                Button(
                                    onClick = {
                                        if (userSpeechInput.isNotBlank()) {
                                            coroutineScope.launch {
                                                isAnalyzing = true
                                                val eval = AICoachService.evaluateSubmission(
                                                    scenarioContext = current.description,
                                                    promptEn = current.title,
                                                    userText = userSpeechInput,
                                                    skillType = "Speaking"
                                                )
                                                activeEvaluation = eval
                                                isAnalyzing = false
                                            }
                                        }
                                    },
                                    enabled = userSpeechInput.isNotBlank() && !isAnalyzing,
                                    colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    if (isAnalyzing) {
                                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Navy900)
                                    } else {
                                        Text("Analyze & Coach 🤖", color = Navy900, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    activeEvaluation?.let { eval ->
                        Spacer(modifier = Modifier.height(16.dp))
                        AICoachFeedbackCard(evaluation = eval)
                    }
                }
            }
        }
    }
}
