package com.example.android.unscramble.ui.test

import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.getUnscrambledWord
import com.example.android.unscramble.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()
        currentGameUiState = viewModel.uiState.value
        // Assert that checkUserGuess() method updates isGuessedWordWrong is updated correctly.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Assert that score is updated correctly.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }
    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Given an incorrect word as input
        val currentGameUiState = viewModel.uiState.value
        val incorrectPlayerWord = "and"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertNotEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val currentGameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        assertNotEquals(unScrambledWord, currentGameUiState.currentScrambledWord)

        assertEquals(currentGameUiState.score, 0)
        assertEquals(currentGameUiState.isGuessedWordWrong, false)
        assertEquals(currentGameUiState.isGameOver, false)
        assertEquals(currentGameUiState.currentWordCount, 1)
    }


}