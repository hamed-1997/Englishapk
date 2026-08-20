package com.example.ui.screens.library

import androidx.compose.animation.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioHelper
import com.example.data.local.VocabEntity
import com.example.ui.components.EnglishPlusTopBar
import com.example.ui.components.PersianTranslationBox
import com.example.ui.theme.*

@Composable
fun LibraryScreen(
    vocabList: List<VocabEntity>,
    audioHelper: AudioHelper,
    onRecordVocabReview: (Long, Boolean) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: SRS Flashcards, 1: Vocabulary List, 2: Grammar & Docs
    var searchQuery by remember { mutableStateOf("") }
    var currentFlashcardIndex by remember { mutableStateOf(0) }
    var isFlashcardFlipped by remember { mutableStateOf(false) }

    val filteredVocab = remember(vocabList, searchQuery) {
        if (searchQuery.isBlank()) vocabList
        else vocabList.filter {
            it.word.contains(searchQuery, ignoreCase = true) ||
            it.meaningEn.contains(searchQuery, ignoreCase = true) ||
            it.meaningFa.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            EnglishPlusTopBar(
                title = "Library & SRS Memory",
                subtitle = "Vocabulary, Grammar Rules & Migration Documents"
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
                    text = { Text("🗂️ SRS Flashcards", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("📖 Vocab (${vocabList.size})", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("📜 Documents", fontWeight = FontWeight.Bold) }
                )
            }

            when (selectedTab) {
                0 -> {
                    // SRS Flashcards Mode
                    if (vocabList.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("No vocabulary items yet. Start a lesson to add words!")
                        }
                    } else {
                        val currentCard = vocabList[currentFlashcardIndex.coerceIn(0, vocabList.size - 1)]

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Card ${currentFlashcardIndex + 1} of ${vocabList.size}",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Flashcard Box
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 280.dp)
                                    .clickable { isFlashcardFlipped = !isFlashcardFlipped }
                                    .testTag("srs_flashcard")
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = currentCard.word,
                                        style = MaterialTheme.typography.displaySmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = currentCard.pronunciation,
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Gold600),
                                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                                    )

                                    IconButton(
                                        onClick = { audioHelper.speak("${currentCard.word}. ${currentCard.exampleEn}") }
                                    ) {
                                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Pronounce", tint = Gold600)
                                    }

                                    if (isFlashcardFlipped) {
                                        Divider(modifier = Modifier.padding(vertical = 12.dp))
                                        Text(
                                            text = currentCard.meaningFa,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Emerald600
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = currentCard.meaningEn,
                                            style = MaterialTheme.typography.bodySmall.copy(color = Slate600),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Example: \"${currentCard.exampleEn}\"",
                                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                                            textAlign = TextAlign.Center
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "👆 Tap to reveal Persian translation & examples",
                                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Review Decision Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        onRecordVocabReview(currentCard.id, false)
                                        isFlashcardFlipped = false
                                        if (currentFlashcardIndex < vocabList.size - 1) currentFlashcardIndex++ else currentFlashcardIndex = 0
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Still Learning 🔁", color = Rose500)
                                }

                                Button(
                                    onClick = {
                                        onRecordVocabReview(currentCard.id, true)
                                        isFlashcardFlipped = false
                                        if (currentFlashcardIndex < vocabList.size - 1) currentFlashcardIndex++ else currentFlashcardIndex = 0
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Mastered! ✓", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Searchable Vocabulary Catalog
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search words, English or Persian...") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredVocab) { vocab ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    shape = RoundedCornerShape(14.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = vocab.word,
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = vocab.partOfSpeech,
                                                    style = MaterialTheme.typography.labelSmall.copy(color = Slate600)
                                                )
                                            }
                                            Text(
                                                text = "${vocab.meaningFa} • ${vocab.meaningEn}",
                                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            )
                                            Text(
                                                text = "Collocation: ${vocab.collocation}",
                                                style = MaterialTheme.typography.labelSmall.copy(color = Sky500, fontSize = 11.sp),
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                        }

                                        IconButton(onClick = { audioHelper.speak(vocab.word) }) {
                                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Play", tint = Gold600)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Practical Immigrant Reference Documents
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            DocumentCard(
                                title = "Tenancy Repair & Maintenance Notice",
                                titleFa = "متن نمونه درخواست تعمیرات به صاحب‌خانه",
                                text = "Dear Landlord, I am writing to notify you of an urgent maintenance issue in Apt 4B. The pipe under the kitchen sink is leaking water. Please send a technician as soon as possible. Thank you."
                            )
                        }
                        item {
                            DocumentCard(
                                title = "Professional Workplace Sick Day Notification",
                                titleFa = "متن نمونه اعلام مرخصی استعلاجی به مدیر",
                                text = "Hi Sarah, I am writing to let you know that I am unwell today with a fever and throat infection. I visited the clinic and will be taking a sick day to rest. I expect to be back tomorrow."
                            )
                        }
                        item {
                            DocumentCard(
                                title = "Daily Standup Async Slack Update",
                                titleFa = "متن نمونه گزارش تسک‌های روزانه در اسلک",
                                text = "Hi Team, 1) Yesterday: Completed the auth flow testing. 2) Today: Working on backend API error handlers. 3) Blockers: None at this time."
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DocumentCard(title: String, titleFa: String, text: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            Text(text = titleFa, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(bottom = 10.dp))

            Surface(
                color = Slate100,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp, color = Slate800),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
