package com.example.colorseeker.domain.model

data class Puzzle(
    val id: String,
    val title: String,
    val rarity: PuzzleRarity,
    val difficulty: Int, // 1-10 scale
    val width: Int,
    val height: Int,
    val targetGrid: List<List<Int>>, // ARGB color values
    val currentGrid: List<List<Int?>>, // Current state, null = uncolored
    val colorPalette: List<Int>, // Available colors for this puzzle
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val completionPercentage: Float = 0f,
    val seekReward: Int = 0,
    val previewImageUrl: String? = null,
    val nftMinted: Boolean = false
) {
    fun isPixelCorrect(x: Int, y: Int): Boolean {
        return currentGrid.getOrNull(y)?.getOrNull(x) == targetGrid.getOrNull(y)?.getOrNull(x)
    }
    
    fun getCompletionPercentage(): Float {
        val totalPixels = width * height
        var correctPixels = 0
        
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (isPixelCorrect(x, y)) {
                    correctPixels++
                }
            }
        }
        
        return if (totalPixels > 0) correctPixels.toFloat() / totalPixels else 0f
    }
}