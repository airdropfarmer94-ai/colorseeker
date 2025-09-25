# Color Seeker

A full-featured pixel puzzle game built with Jetpack Compose, featuring SEEK token rewards, NFT minting, and Solana blockchain integration.

## Features

### 🎨 Core Gameplay
- **Pixel Puzzle Coloring**: Interactive pixel grid puzzles with color palettes
- **Progressive Difficulty**: Four rarity tiers (Common, Rare, Epic, Legendary)
- **Real-time Progress Tracking**: Visual completion indicators and progress saving
- **Smart Color System**: Quantized color palettes for optimal puzzle experience

### 💎 SEEK Token System
- **Earn SEEK**: Get rewards for correct pixel coloring based on puzzle rarity
- **Completion Bonuses**: Extra SEEK tokens for completing entire puzzles
- **Daily Streaks**: Bonus rewards for consecutive daily logins
- **Referral System**: Earn 10 SEEK for each friend you refer

### 🏆 Social Features
- **Dual Leaderboards**: Top SEEK holders and top puzzle completers
- **Achievement System**: Unlock badges and milestones
- **Puzzle Sharing**: Export and share completed puzzle artwork
- **Community Rankings**: Compare progress with other players

### 🖼️ Image Processing & NFTs
- **Image Upload**: Convert personal images into puzzles
- **ARGB Processing**: Advanced color quantization and grid generation
- **SVG Previews**: Scalable vector graphics for puzzle previews
- **NFT Minting**: Turn completed puzzles into Solana NFTs using Metaplex Core

### ⛓️ Blockchain Integration
- **Solana Wallet**: Integrated wallet for SOL and USDC transactions
- **Payment System**: Pay with SOL/USDC to unlock premium puzzles
- **NFT Gallery**: View and manage your puzzle NFT collection
- **Future SPL Token**: SEEK designed for future SPL token launch

### 📱 Modern Android Architecture
- **Jetpack Compose UI**: Modern declarative UI framework
- **MVVM Pattern**: Clean architecture with ViewModels and repositories
- **Coroutines**: Asynchronous programming for smooth performance
- **Navigation Component**: Type-safe navigation between screens
- **Firebase Integration**: User data, leaderboards, and real-time sync

## Setup Instructions

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- Android SDK 24+ (minimum)
- Firebase project (for backend features)
- Solana wallet setup (for blockchain features)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/airdropfarmer94-ai/colorseeker.git
   cd colorseeker
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

3. **Firebase Setup**
   - Create a new Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
   - Add an Android app with package name `com.example.colorseeker`
   - Download `google-services.json` and replace the placeholder file in `app/`
   - Enable Firestore, Authentication, and Storage in Firebase console

4. **Build and Run**
   ```bash
   ./gradlew build
   ```
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use `./gradlew installDebug`

### Development Setup

1. **Dependencies Installation**
   All dependencies are managed via Gradle. The main libraries include:
   - Jetpack Compose for UI
   - Hilt for dependency injection
   - Retrofit for networking
   - Firebase SDK for backend
   - Coil for image loading

2. **Environment Configuration**
   - Copy `app/google-services.json.template` to `app/google-services.json`
   - Add your Firebase configuration
   - Update blockchain endpoints in `Constants.kt` if needed

3. **USB Debugging**
   - Enable Developer Options on your Android device
   - Enable USB Debugging
   - Connect device and accept debugging permissions

## Project Structure

```
app/src/main/java/com/example/colorseeker/
├── data/                    # Data layer (repositories, network, local storage)
│   ├── models/             # Data models and DTOs
│   ├── repository/         # Repository implementations
│   ├── network/            # API services and network models
│   └── local/              # Local database and preferences
├── domain/                 # Domain layer (business logic)
│   ├── model/              # Domain models
│   ├── repository/         # Repository interfaces
│   └── usecase/            # Use cases and business logic
├── presentation/           # Presentation layer (UI)
│   ├── screens/            # Compose screens
│   ├── components/         # Reusable UI components
│   ├── navigation/         # Navigation setup
│   └── viewmodel/          # ViewModels
├── di/                     # Dependency injection modules
├── utils/                  # Utility classes
└── ui/theme/               # App theming and colors
```

## Key Components

### Puzzle System
- **PuzzleRepository**: Manages puzzle data and progress
- **ColorPixelUseCase**: Handles pixel coloring logic and SEEK rewards
- **ImageProcessor**: Converts images to puzzle grids with color quantization

### Blockchain Integration
- **BlockchainUtils**: Solana wallet and transaction management
- **NFTMinting**: Metaplex Core NFT creation (stubbed for development)
- **Payment Processing**: SOL/USDC transaction handling

### User Management
- **UserRepository**: Profile, balance, and streak management
- **LeaderboardRepository**: Real-time ranking system
- **AchievementSystem**: Badge and milestone tracking

## Development Guidelines

### Building
```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing)
./gradlew assembleRelease

# Run tests
./gradlew test
```

### Testing
```bash
# Unit tests
./gradlew testDebugUnitTest

# Instrumented tests
./gradlew connectedDebugAndroidTest
```

### Code Style
- Follow Android Kotlin style guide
- Use ktlint for formatting
- Prefer composition over inheritance
- Write descriptive commit messages

## Roadmap

### Phase 1: Foundation ✅
- Basic puzzle gameplay
- SEEK token system
- Firebase integration

### Phase 2: Social Features 🚧
- Leaderboards implementation
- Achievement system
- Referral program

### Phase 3: Blockchain Integration 📅
- Solana wallet integration
- NFT minting with Metaplex
- SPL token launch for SEEK

### Phase 4: Advanced Features 📅
- AI-generated puzzles
- Multiplayer challenges
- Tournament system
- Advanced image processing

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add some amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- 📧 Email: support@colorseeker.app
- 🐛 Issues: [GitHub Issues](https://github.com/airdropfarmer94-ai/colorseeker/issues)
- 💬 Discord: [Color Seeker Community](https://discord.gg/colorseeker)

---

**Ready to start your Color Seeker journey? Build, run, and start earning SEEK! 🎨✨**
