package com.example.colorseeker.domain.model

enum class PuzzleRarity(
    val displayName: String,
    val unlockCost: Int,
    val completionReward: Int,
    val color: String
) {
    COMMON("Common", 0, 10, "#9E9E9E"),
    RARE("Rare", 50, 25, "#2196F3"),
    EPIC("Epic", 150, 50, "#9C27B0"),
    LEGENDARY("Legendary", 500, 100, "#FF9800")
}