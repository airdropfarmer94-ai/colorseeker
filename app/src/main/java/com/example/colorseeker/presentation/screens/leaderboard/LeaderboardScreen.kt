package com.example.colorseeker.presentation.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class LeaderboardUser(
    val rank: Int,
    val username: String,
    val score: Int,
    val change: Int // +/- from last week
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val seekLeaders = remember {
        listOf(
            LeaderboardUser(1, "PixelMaster", 15420, 2),
            LeaderboardUser(2, "ColorWizard", 12350, -1),
            LeaderboardUser(3, "ArtistPro", 11200, 1),
            LeaderboardUser(4, "SeekHunter", 9800, 0),
            LeaderboardUser(5, "PuzzleKing", 8950, 3),
            LeaderboardUser(6, "CryptoArt", 7600, -2),
            LeaderboardUser(7, "DigitalDreamer", 6500, 1),
            LeaderboardUser(8, "NFTCollector", 5900, 0),
            LeaderboardUser(9, "ColorSeeker42", 5200, 4),
            LeaderboardUser(10, "PixelPainter", 4800, -1)
        )
    }
    
    val puzzleLeaders = remember {
        listOf(
            LeaderboardUser(1, "PuzzleMaster", 247, 5),
            LeaderboardUser(2, "CompletionKing", 189, 2),
            LeaderboardUser(3, "GridGuru", 156, -1),
            LeaderboardUser(4, "PixelPro", 134, 1),
            LeaderboardUser(5, "ColorChamp", 128, 0),
            LeaderboardUser(6, "ArtSolver", 112, 3),
            LeaderboardUser(7, "PuzzleAddict", 98, -2),
            LeaderboardUser(8, "SeekMaster", 87, 1),
            LeaderboardUser(9, "GridSolver", 76, 0),
            LeaderboardUser(10, "PixelHero", 65, 2)
        )
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
                text = "Leaderboards",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = { /* Refresh */ }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab Row
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Stars, 
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("SEEK Holders")
                    }
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.GridOn, 
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Puzzle Masters")
                    }
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User's Current Rank Card
        CurrentUserRankCard(
            rank = if (selectedTab == 0) 45 else 23,
            score = if (selectedTab == 0) 2140 else 34,
            type = if (selectedTab == 0) "SEEK" else "Puzzles"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Leaderboard List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val currentLeaders = if (selectedTab == 0) seekLeaders else puzzleLeaders
            
            itemsIndexed(currentLeaders) { _, user ->
                LeaderboardItem(
                    user = user,
                    scoreType = if (selectedTab == 0) "SEEK" else "Puzzles"
                )
            }
        }
    }
}

@Composable
fun CurrentUserRankCard(
    rank: Int,
    score: Int,
    type: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "You",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Your Rank",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "#$rank",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$score $type",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Keep climbing!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun LeaderboardItem(
    user: LeaderboardUser,
    scoreType: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank with special styling for top 3
            RankBadge(rank = user.rank)
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // User Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when (user.rank) {
                            1 -> Color(0xFFFFD700) // Gold
                            2 -> Color(0xFFC0C0C0) // Silver
                            3 -> Color(0xFFCD7F32) // Bronze
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.username.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (user.rank <= 3) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Username
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                // Change indicator
                if (user.change != 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (user.change > 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = if (user.change > 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${if (user.change > 0) "+" else ""}${user.change}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (user.change > 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Score
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (scoreType == "SEEK") {
                        Icon(
                            Icons.Default.Stars,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = user.score.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = scoreType,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RankBadge(rank: Int) {
    val (backgroundColor, textColor) = when (rank) {
        1 -> Color(0xFFFFD700) to Color.White // Gold
        2 -> Color(0xFFC0C0C0) to Color.White // Silver
        3 -> Color(0xFFCD7F32) to Color.White // Bronze
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rank.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}