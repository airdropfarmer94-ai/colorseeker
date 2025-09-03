package com.example.colorseeker.utils

object Constants {
    // SEEK Token Configuration
    const val SEEK_PER_CORRECT_PIXEL_COMMON = 1
    const val SEEK_PER_CORRECT_PIXEL_RARE = 2
    const val SEEK_PER_CORRECT_PIXEL_EPIC = 3
    const val SEEK_PER_CORRECT_PIXEL_LEGENDARY = 5
    
    const val SEEK_REFERRAL_BONUS = 10
    const val SEEK_DAILY_LOGIN_BASE = 5
    const val SEEK_STREAK_BONUS_7_DAYS = 20
    const val SEEK_STREAK_BONUS_30_DAYS = 50
    
    // Puzzle Unlock Costs
    const val UNLOCK_COST_RARE = 50
    const val UNLOCK_COST_EPIC = 150
    const val UNLOCK_COST_LEGENDARY = 500
    
    // Completion Rewards
    const val COMPLETION_REWARD_COMMON = 10
    const val COMPLETION_REWARD_RARE = 25
    const val COMPLETION_REWARD_EPIC = 50
    const val COMPLETION_REWARD_LEGENDARY = 100
    
    // Blockchain Costs (in SOL)
    const val NFT_MINT_COST_SOL = 0.1
    const val PUZZLE_UNLOCK_COST_SOL = 0.05
    
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_PUZZLES = "puzzles"
    const val COLLECTION_LEADERBOARDS = "leaderboards"
    const val COLLECTION_TRANSACTIONS = "transactions"
    const val COLLECTION_ACHIEVEMENTS = "achievements"
    
    // Shared Preferences Keys
    const val PREF_USER_ID = "user_id"
    const val PREF_WALLET_ADDRESS = "wallet_address"
    const val PREF_LAST_LOGIN = "last_login"
    const val PREF_ONBOARDING_COMPLETED = "onboarding_completed"
    
    // Puzzle Grid Sizes
    const val GRID_SIZE_SMALL = 12
    const val GRID_SIZE_MEDIUM = 16
    const val GRID_SIZE_LARGE = 24
    const val GRID_SIZE_EXTRA_LARGE = 32
    
    // Image Processing
    const val MAX_COLORS_IN_PALETTE = 12
    const val DEFAULT_SVG_CELL_SIZE = 10
    
    // API Endpoints (placeholder)
    const val BASE_API_URL = "https://api.colorseeker.app/"
    const val LEADERBOARD_ENDPOINT = "leaderboards"
    const val USER_ENDPOINT = "users"
    const val PUZZLE_ENDPOINT = "puzzles"
    
    // Social Sharing
    const val SHARE_TEXT_TEMPLATE = "I just completed a puzzle in Color Seeker and earned %d SEEK! Join me with my referral code: %s"
    const val APP_DOWNLOAD_URL = "https://colorseeker.app/download"
}