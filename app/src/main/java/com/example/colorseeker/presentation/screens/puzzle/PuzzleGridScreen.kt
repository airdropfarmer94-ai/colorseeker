package com.example.colorseeker.presentation.screens.puzzle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleGridScreen(
    puzzleId: String,
    navController: NavController
) {
    var selectedColor by remember { mutableStateOf(Color.Red) }
    var grid by remember { mutableStateOf(createMockGrid()) }
    var seekEarned by remember { mutableStateOf(0) }
    var showCompletionDialog by remember { mutableStateOf(false) }
    
    val colors = listOf(
        Color.Red, Color.Blue, Color.Green, Color.Yellow,
        Color.Magenta, Color.Cyan, Color.Black, Color.White,
        Color.Gray, Color(0xFFFF9800), Color(0xFF9C27B0), Color(0xFF4CAF50)
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        PuzzleHeader(
            puzzleTitle = "Sunset Landscape",
            progress = calculateProgress(grid),
            seekEarned = seekEarned,
            onBackClick = { navController.popBackStack() }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Color Palette
        ColorPalette(
            colors = colors,
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Puzzle Grid
        PuzzleGrid(
            grid = grid,
            selectedColor = selectedColor,
            onPixelColored = { x, y ->
                grid = grid.mapIndexed { rowIndex, row ->
                    if (rowIndex == y) {
                        row.mapIndexed { colIndex, pixel ->
                            if (colIndex == x) selectedColor else pixel
                        }
                    } else row
                }
                seekEarned += 2 // Mock SEEK reward
                
                if (calculateProgress(grid) >= 1.0f) {
                    showCompletionDialog = true
                }
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* Show hint */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Lightbulb, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Hint (10 SEEK)")
            }
            
            Button(
                onClick = { /* Share progress */ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Share")
            }
        }
    }
    
    // Completion Dialog
    if (showCompletionDialog) {
        CompletionDialog(
            seekReward = 50,
            onDismiss = { showCompletionDialog = false },
            onMintNFT = { 
                showCompletionDialog = false
                // Navigate to NFT minting
            },
            onContinue = { 
                showCompletionDialog = false
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun PuzzleHeader(
    puzzleTitle: String,
    progress: Float,
    seekEarned: Int,
    onBackClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = puzzleTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Epic • 16x16",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Stars,
                            contentDescription = "SEEK",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = seekEarned.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(
                text = "${(progress * 100).toInt()}% Complete",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ColorPalette(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Color Palette",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(colors) { color ->
                    ColorSwatch(
                        color = color,
                        isSelected = color == selectedColor,
                        onClick = { onColorSelected(color) }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorSwatch(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = CircleShape
            )
            .pointerInput(Unit) {
                detectTapGestures { onClick() }
            }
    )
}

@Composable
fun PuzzleGrid(
    grid: List<List<Color>>,
    selectedColor: Color,
    onPixelColored: (Int, Int) -> Unit
) {
    val density = LocalDensity.current
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val gridSize = size.width / grid[0].size
                        val x = floor(offset.x / gridSize).toInt()
                        val y = floor(offset.y / gridSize).toInt()
                        
                        if (x in 0 until grid[0].size && y in 0 until grid.size) {
                            onPixelColored(x, y)
                        }
                    }
                }
        ) {
            drawPuzzleGrid(grid)
        }
    }
}

fun DrawScope.drawPuzzleGrid(grid: List<List<Color>>) {
    val cellSize = size.width / grid[0].size
    
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val color = grid[y][x]
            drawRect(
                color = color,
                topLeft = Offset(x * cellSize, y * cellSize),
                size = Size(cellSize, cellSize)
            )
            
            // Draw grid lines
            drawRect(
                color = Color.Black.copy(alpha = 0.2f),
                topLeft = Offset(x * cellSize, y * cellSize),
                size = Size(cellSize, cellSize),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
            )
        }
    }
}

@Composable
fun CompletionDialog(
    seekReward: Int,
    onDismiss: () -> Unit,
    onMintNFT: () -> Unit,
    onContinue: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Puzzle Completed!")
            }
        },
        text = {
            Column {
                Text("Congratulations! You've completed the puzzle.")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Stars, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Earned $seekReward SEEK tokens")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onContinue) {
                Text("Continue")
            }
        },
        dismissButton = {
            TextButton(onClick = onMintNFT) {
                Text("Mint NFT (0.1 SOL)")
            }
        }
    )
}

fun createMockGrid(): List<List<Color>> {
    return List(16) { y ->
        List(16) { x ->
            // Create a simple pattern for demonstration
            when {
                y < 4 -> Color(0xFF87CEEB) // Sky blue
                y < 8 -> Color(0xFFFFE4B5) // Sunset
                y < 12 -> Color(0xFF228B22) // Forest
                else -> Color(0xFF8B4513) // Ground
            }
        }
    }
}

fun calculateProgress(grid: List<List<Color>>): Float {
    // Mock progress calculation
    return 0.75f
}