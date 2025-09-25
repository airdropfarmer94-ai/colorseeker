package com.example.colorseeker.domain.model

data class NFT(
    val id: String,
    val puzzleId: String,
    val ownerUserId: String,
    val mintAddress: String,
    val metadataUrl: String,
    val imageUrl: String,
    val name: String,
    val description: String,
    val attributes: List<NFTAttribute>,
    val mintedAt: Long,
    val isLandscapeSet: Boolean = false
)

data class NFTAttribute(
    val traitType: String,
    val value: String,
    val displayType: String? = null
)

data class BlockchainTransaction(
    val id: String,
    val type: TransactionType,
    val fromAddress: String,
    val toAddress: String,
    val amount: Double,
    val currency: Currency,
    val status: TransactionStatus,
    val signature: String,
    val timestamp: Long
)

enum class TransactionType {
    SEEK_PURCHASE,
    PUZZLE_UNLOCK,
    NFT_MINT,
    REFERRAL_REWARD
}

enum class Currency {
    SEEK,
    SOL,
    USDC
}

enum class TransactionStatus {
    PENDING,
    CONFIRMED,
    FAILED
}