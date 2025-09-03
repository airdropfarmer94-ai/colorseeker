package com.example.colorseeker.domain.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val seekReward: Int,
    val iconUrl: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

enum class AchievementType {
    FIRST_PUZZLE,
    STREAK_7_DAYS,
    STREAK_30_DAYS,
    COMPLETE_10_PUZZLES,
    COMPLETE_100_PUZZLES,
    UNLOCK_RARE_PUZZLE,
    UNLOCK_EPIC_PUZZLE,
    UNLOCK_LEGENDARY_PUZZLE,
    REFER_5_FRIENDS,
    EARN_1000_SEEK,
    MINT_FIRST_NFT,
    COMPLETE_LANDSCAPE_SET
}