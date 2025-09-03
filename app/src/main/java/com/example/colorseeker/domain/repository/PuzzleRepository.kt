package com.example.colorseeker.domain.repository

import com.example.colorseeker.domain.model.Puzzle
import com.example.colorseeker.domain.model.PuzzleRarity
import kotlinx.coroutines.flow.Flow

interface PuzzleRepository {
    suspend fun getAllPuzzles(): Flow<List<Puzzle>>
    suspend fun getPuzzleById(id: String): Puzzle?
    suspend fun getPuzzlesByRarity(rarity: PuzzleRarity): Flow<List<Puzzle>>
    suspend fun updatePuzzleProgress(puzzleId: String, x: Int, y: Int, color: Int): Result<Puzzle>
    suspend fun unlockPuzzle(puzzleId: String, paymentMethod: PaymentMethod): Result<Boolean>
    suspend fun markPuzzleCompleted(puzzleId: String): Result<Int> // Returns SEEK reward
    suspend fun createPuzzleFromImage(imageUri: String, title: String, rarity: PuzzleRarity): Result<Puzzle>
    suspend fun getUnlockedPuzzles(userId: String): Flow<List<Puzzle>>
    suspend fun getCompletedPuzzles(userId: String): Flow<List<Puzzle>>
}

enum class PaymentMethod {
    SEEK,
    SOL,
    USDC
}