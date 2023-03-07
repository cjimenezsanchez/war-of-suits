package com.jime.game.domain.di

import com.jime.game.domain.GameHelper
import com.jime.game.domain.WarOfSuitsHelper
import com.jime.game.domain.use_case.GetLastRoundWinnerUseCase
import com.jime.game.domain.use_case.GetGameWinnerUseCase
import com.jime.game.domain.use_case.IsGameFinishedUseCase
import com.jime.game.domain.use_case.play_round.*
import com.jime.game.domain.use_case.start_game.InitDeckUseCase
import com.jime.game.domain.use_case.start_game.InitSuitsWeightUseCase
import com.jime.game.domain.use_case.start_game.SplitDeckInHalfUseCase
import com.jime.game.domain.use_case.start_game.StartNewGameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object GameModule {

    @Provides
    @ViewModelScoped
    fun provideGameHelper(
        startNewGameUseCase: StartNewGameUseCase,
        getPlayerNextCardUseCase: GetPlayerNextCardUseCase,
        playNextRoundUseCase: PlayNextRoundUseCase,
        getLastRoundWinnerUseCase: GetLastRoundWinnerUseCase,
        isGameFinishedUseCase: IsGameFinishedUseCase,
        getGameWinnerUseCase: GetGameWinnerUseCase
    ): GameHelper {
        return WarOfSuitsHelper(
            startNewGameUseCase,
            getPlayerNextCardUseCase,
            playNextRoundUseCase,
            isGameFinishedUseCase,
            getLastRoundWinnerUseCase,
            getGameWinnerUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideStartNewGameUseCase(): StartNewGameUseCase {
        return StartNewGameUseCase(
            initSuitsWeightUseCase = InitSuitsWeightUseCase(),
            initDeckUseCase = InitDeckUseCase(),
            splitDeckUseCase = SplitDeckInHalfUseCase()
        )
    }

    @Provides
    @ViewModelScoped
    fun providePLayNextRoundUseCase(): PlayNextRoundUseCase {
        return PlayNextRoundUseCase(
            getPlayerNextCard = GetPlayerNextCardUseCase(),
            getHigherCardFromPairOfCards = GetHigherCardFromPairOfCardsUseCase(),
            addPlayerPoints = AddPointsToPlayerUseCase(),
            addRoundWinner = AddRoundWinnerUseCase(),
            removePlayedCardFromPlayerPile = RemovePlayedCardFromPlayerPileUseCase()
        )
    }

    @Provides
    @ViewModelScoped
    fun provideIsGameFinishedUseCase() = IsGameFinishedUseCase()

    @Provides
    @ViewModelScoped
    fun providesGetWinnerUseCase() = GetGameWinnerUseCase()

    @Provides
    @ViewModelScoped
    fun providesGetLastRoundWinnerUseCase() = GetLastRoundWinnerUseCase()

    @Provides
    @ViewModelScoped
    fun provideGetNextPlayerCardUseCase() = GetPlayerNextCardUseCase()

}