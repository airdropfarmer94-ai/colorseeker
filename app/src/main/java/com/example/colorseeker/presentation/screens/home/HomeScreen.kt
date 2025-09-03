package com.example.colorseeker.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.colorseeker.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val mockSeekBalance = 1250
    val mockStreak = 7
    val mockPuzzlesCompleted = 23
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with user stats
        UserStatsCard(
            seekBalance = mockSeekBalance,
            streak = mockStreak,
            puzzlesCompleted = mockPuzzlesCompleted
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Daily Challenge Section
        DailyChallengeSection()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Quick Actions
        QuickActionsGrid(navController = navController)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Recent Puzzles
        RecentPuzzlesSection(navController = navController)
    }
}

@Composable
fun UserStatsCard(
    seekBalance: Int,
    streak: Int,
    puzzlesCompleted: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Color Seeker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Stars,
                    value = seekBalance.toString(),
                    label = "SEEK"
                )
                StatItem(
                    icon = Icons.Default.LocalFire,
                    value = streak.toString(),
                    label = "Day Streak"
                )
                StatItem(
                    icon = Icons.Default.CheckCircle,
                    value = puzzlesCompleted.toString(),
                    label = "Completed"
                )
            }
        }
    }
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DailyChallengeSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Daily Challenge",
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Daily Challenge",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Complete 3 pixels for 25 SEEK bonus",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = { /* Navigate to daily challenge */ }
            ) {
                Text("Start")
            }
        }
    }
}

@Composable
fun QuickActionsGrid(navController: NavController) {
    Text(
        text = "Quick Actions",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard(
            icon = Icons.Default.GridOn,
            title = "Puzzles",
            subtitle = "Browse & Play",
            modifier = Modifier.weight(1f)
        ) {
            navController.navigate(Screen.PuzzleLibrary.route)
        }
        
        QuickActionCard(
            icon = Icons.Default.Leaderboard,
            title = "Leaderboard",
            subtitle = "Rankings",
            modifier = Modifier.weight(1f)
        ) {
            navController.navigate(Screen.Leaderboards.route)
        }
    }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RecentPuzzlesSection(navController: NavController) {
    Text(
        text = "Continue Playing",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) { index ->
            RecentPuzzleCard(
                title = "Puzzle ${index + 1}",
                progress = (index + 1) * 0.3f,
                rarity = when (index) {
                    0 -> "Common"
                    1 -> "Rare"
                    else -> "Epic"
                }
            ) {
                navController.navigate(Screen.PuzzleGrid.createRoute("puzzle_${index + 1}"))
            }
        }
    }
}

@Composable
fun RecentPuzzleCard(
    title: String,
    progress: Float,
    rarity: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Puzzle preview",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = rarity,
                style = MaterialTheme.typography.bodySmall,
                color = when (rarity) {
                    "Common" -> Color.Gray
                    "Rare" -> Color.Blue
                    "Epic" -> Color.Magenta
                    else -> Color.Gray
                }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}