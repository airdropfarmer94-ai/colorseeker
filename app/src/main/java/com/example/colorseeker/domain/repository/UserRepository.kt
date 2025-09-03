package com.example.colorseeker.domain.repository

import com.example.colorseeker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getCurrentUser(): Flow<User?>
    suspend fun updateSeekBalance(userId: String, amount: Int): Result<Boolean>
    suspend fun updateLoginStreak(userId: String): Result<Int> // Returns new streak
    suspend fun generateReferralCode(): String
    suspend fun validateReferralCode(code: String): Result<String> // Returns referrer userId
    suspend fun processReferral(newUserId: String, referrerCode: String): Result<Boolean>
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(user: User): Result<Boolean>
    suspend fun addAchievement(userId: String, achievementId: String): Result<Boolean>
}