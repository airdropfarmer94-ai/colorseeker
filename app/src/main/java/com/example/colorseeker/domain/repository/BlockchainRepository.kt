package com.example.colorseeker.domain.repository

import com.example.colorseeker.domain.model.NFT
import com.example.colorseeker.domain.model.BlockchainTransaction
import com.example.colorseeker.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface BlockchainRepository {
    suspend fun mintNFT(puzzleId: String, userId: String): Result<NFT>
    suspend fun getUserNFTs(userId: String): Flow<List<NFT>>
    suspend fun processPayment(
        amount: Double, 
        currency: Currency, 
        fromAddress: String, 
        toAddress: String
    ): Result<BlockchainTransaction>
    suspend fun getTransactionHistory(userId: String): Flow<List<BlockchainTransaction>>
    suspend fun getWalletBalance(address: String, currency: Currency): Double
    suspend fun initializeWallet(): Result<String> // Returns wallet address
}