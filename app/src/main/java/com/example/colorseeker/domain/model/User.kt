package com.example.colorseeker.domain.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val seekBalance: Int = 0,
    val solBalance: Double = 0.0,
    val usdcBalance: Double = 0.0,
    val totalPuzzlesCompleted: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val lastLoginDate: Long = 0L,
    val referralCode: String,
    val referredBy: String? = null,
    val totalReferrals: Int = 0,
    val nftCount: Int = 0,
    val achievements: List<Achievement> = emptyList(),
    val unlockedPuzzles: List<String> = emptyList(), // Puzzle IDs
    val completedPuzzles: List<String> = emptyList()
)