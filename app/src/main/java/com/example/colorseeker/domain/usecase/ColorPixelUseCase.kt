package com.example.colorseeker.domain.usecase

import com.example.colorseeker.domain.model.Puzzle
import com.example.colorseeker.domain.repository.PuzzleRepository
import com.example.colorseeker.domain.repository.UserRepository
import javax.inject.Inject

class ColorPixelUseCase @Inject constructor(
    private val puzzleRepository: PuzzleRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(puzzleId: String, x: Int, y: Int, color: Int): Result<ColorPixelResult> {
        return try {
            val updatedPuzzle = puzzleRepository.updatePuzzleProgress(puzzleId, x, y, color).getOrThrow()
            val isCorrect = updatedPuzzle.isPixelCorrect(x, y)
            
            var seekReward = 0
            if (isCorrect) {
                seekReward = when (updatedPuzzle.rarity) {
                    com.example.colorseeker.domain.model.PuzzleRarity.COMMON -> 1
                    com.example.colorseeker.domain.model.PuzzleRarity.RARE -> 2
                    com.example.colorseeker.domain.model.PuzzleRarity.EPIC -> 3
                    com.example.colorseeker.domain.model.PuzzleRarity.LEGENDARY -> 5
                }
                
                // Update user's SEEK balance
                userRepository.updateSeekBalance(updatedPuzzle.id, seekReward)
            }
            
            val isCompleted = updatedPuzzle.getCompletionPercentage() >= 1.0f
            var completionBonus = 0
            
            if (isCompleted && !updatedPuzzle.isCompleted) {
                completionBonus = updatedPuzzle.rarity.completionReward
                puzzleRepository.markPuzzleCompleted(puzzleId)
                userRepository.updateSeekBalance(updatedPuzzle.id, completionBonus)
            }
            
            Result.success(
                ColorPixelResult(
                    updatedPuzzle = updatedPuzzle,
                    isCorrect = isCorrect,
                    seekReward = seekReward,
                    isCompleted = isCompleted,
                    completionBonus = completionBonus
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class ColorPixelResult(
    val updatedPuzzle: Puzzle,
    val isCorrect: Boolean,
    val seekReward: Int,
    val isCompleted: Boolean,
    val completionBonus: Int
)