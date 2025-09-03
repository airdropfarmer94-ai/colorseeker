package com.example.colorseeker.presentation.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colorseeker.presentation.navigation.Screen

data class MockPuzzle(
    val id: String,
    val title: String,
    val rarity: String,
    val difficulty: Int,
    val size: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean,
    val progress: Float,
    val unlockCost: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleLibraryScreen(navController: NavController) {
    var selectedRarity by remember { mutableStateOf("All") }
    
    val mockPuzzles = remember {
        listOf(
            MockPuzzle("1", "Mountain Vista", "Common", 3, "12x12", true, true, 1.0f, 0),
            MockPuzzle("2", "Ocean Sunset", "Common", 4, "16x16", true, false, 0.6f, 0),
            MockPuzzle("3", "Forest Path", "Rare", 6, "20x20", true, false, 0.3f, 50),
            MockPuzzle("4", "City Lights", "Rare", 7, "24x24", false, false, 0.0f, 50),
            MockPuzzle("5", "Aurora Borealis", "Epic", 8, "32x32", false, false, 0.0f, 150),
            MockPuzzle("6", "Dragon's Lair", "Legendary", 10, "40x40", false, false, 0.0f, 500),
        )
    }
    
    val rarities = listOf("All", "Common", "Rare", "Epic", "Legendary")
    
    val filteredPuzzles = if (selectedRarity == "All") {
        mockPuzzles
    } else {
        mockPuzzles.filter { it.rarity == selectedRarity }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            
            Text(
                text = "Puzzle Library",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = { /* Upload image */ }) {
                Icon(Icons.Default.CloudUpload, contentDescription = "Upload")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Rarity Filter
        ScrollableTabRow(
            selectedTabIndex = rarities.indexOf(selectedRarity),
            edgePadding = 0.dp
        ) {
            rarities.forEach { rarity ->
                Tab(
                    selected = selectedRarity == rarity,
                    onClick = { selectedRarity = rarity },
                    text = { Text(rarity) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Puzzles List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredPuzzles) { puzzle ->
                PuzzleCard(
                    puzzle = puzzle,
                    onPuzzleClick = {
                        if (puzzle.isUnlocked) {
                            navController.navigate(Screen.PuzzleGrid.createRoute(puzzle.id))
                        }
                    },
                    onUnlockClick = {
                        // Handle unlock logic
                    }
                )
            }
        }
    }
}

@Composable
fun PuzzleCard(
    puzzle: MockPuzzle,
    onPuzzleClick: () -> Unit,
    onUnlockClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = if (puzzle.isUnlocked) onPuzzleClick else { },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (puzzle.isUnlocked) 
                MaterialTheme.colorScheme.surface 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Puzzle Preview
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (puzzle.isUnlocked) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            Color.Gray.copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (puzzle.isUnlocked) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Puzzle preview",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Locked",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Puzzle Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = puzzle.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (puzzle.isCompleted) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    RarityChip(rarity = puzzle.rarity)
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    DifficultyStars(difficulty = puzzle.difficulty)
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = puzzle.size,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (puzzle.isUnlocked && puzzle.progress > 0) {
                    LinearProgressIndicator(
                        progress = puzzle.progress,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "${(puzzle.progress * 100).toInt()}% Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Action Button
            if (!puzzle.isUnlocked) {
                Button(
                    onClick = onUnlockClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getRarityColor(puzzle.rarity)
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Stars,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${puzzle.unlockCost}")
                        }
                        Text(
                            text = "Unlock",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            } else {
                IconButton(onClick = onPuzzleClick) {
                    Icon(
                        if (puzzle.progress > 0) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun RarityChip(rarity: String) {
    val color = getRarityColor(rarity)
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = rarity,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DifficultyStars(difficulty: Int) {
    Row {
        repeat(5) { index ->
            Icon(
                if (index < difficulty / 2) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

fun getRarityColor(rarity: String): Color {
    return when (rarity) {
        "Common" -> Color(0xFF9E9E9E)
        "Rare" -> Color(0xFF2196F3)
        "Epic" -> Color(0xFF9C27B0)
        "Legendary" -> Color(0xFFFF9800)
        else -> Color.Gray
    }
}