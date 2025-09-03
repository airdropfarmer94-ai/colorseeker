package com.example.colorseeker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.colorseeker.presentation.screens.home.HomeScreen
import com.example.colorseeker.presentation.screens.puzzle.PuzzleGridScreen
import com.example.colorseeker.presentation.screens.library.PuzzleLibraryScreen
import com.example.colorseeker.presentation.screens.leaderboard.LeaderboardScreen
import com.example.colorseeker.presentation.screens.profile.ProfileScreen

@Composable
fun ColorSeekerNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.PuzzleGrid.route) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId") ?: ""
            PuzzleGridScreen(
                puzzleId = puzzleId,
                navController = navController
            )
        }
        
        composable(Screen.PuzzleLibrary.route) {
            PuzzleLibraryScreen(navController = navController)
        }
        
        composable(Screen.Leaderboards.route) {
            LeaderboardScreen(navController = navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}