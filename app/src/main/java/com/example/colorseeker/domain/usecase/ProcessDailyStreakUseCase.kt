package com.example.colorseeker.domain.usecase

import com.example.colorseeker.domain.repository.UserRepository
import javax.inject.Inject

class ProcessDailyStreakUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<StreakResult> {
        return try {
            val newStreak = userRepository.updateLoginStreak(userId).getOrThrow()
            
            val seekReward = when {
                newStreak >= 30 -> 50 // 30 day milestone
                newStreak >= 7 -> 20  // 7 day milestone
                newStreak >= 3 -> 10  // 3 day milestone
                else -> 5 // Daily reward
            }
            
            userRepository.updateSeekBalance(userId, seekReward)
            
            val isMilestone = newStreak in listOf(3, 7, 14, 30, 50, 100)
            
            Result.success(
                StreakResult(
                    newStreak = newStreak,
                    seekReward = seekReward,
                    isMilestone = isMilestone
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class StreakResult(
    val newStreak: Int,
    val seekReward: Int,
    val isMilestone: Boolean
)