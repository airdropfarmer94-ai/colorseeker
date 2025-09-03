package com.example.colorseeker.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object PuzzleGrid : Screen("puzzle_grid/{puzzleId}") {
        fun createRoute(puzzleId: String) = "puzzle_grid/$puzzleId"
    }
    object PuzzleLibrary : Screen("puzzle_library")
    object Leaderboards : Screen("leaderboards")
    object Profile : Screen("profile")
    object Shop : Screen("shop")
    object Achievements : Screen("achievements")
    object Referrals : Screen("referrals")
    object NFTGallery : Screen("nft_gallery")
    object Settings : Screen("settings")
}