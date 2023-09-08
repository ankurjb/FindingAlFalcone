package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.ankurjb.lengaburu.MainCoroutineRule
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.repo.MainRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResultViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val token = "token"
    private val planets = arrayOf("Planets")
    private val vehicles = arrayOf("Vehicles")
    private val timeTaken = 150.0

    private lateinit var viewModel: ResultViewModel

    private val savedStateHandle: SavedStateHandle = mockk {
        every {
            get<Array<String>>(ResultViewModel.PLANETS)
        } returns planets
        every {
            get<Array<String>>(ResultViewModel.VEHICLES)
        } returns vehicles
        every {
            get<Double>(ResultViewModel.TIME_TAKEN)
        } returns timeTaken
    }

    private val repository: MainRepository = mockk {
        coEvery {
            getToken()
        } returns UiState.Success(token)
        coEvery {
            findFalcone(any(), any(), any())
        } returns UiState.Success("success")
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ResultViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository
        )
    }

    @Test
    fun retry() = runTest {
        viewModel.retry()
        coVerify(exactly = 2) { // 1st API call made on init second call made on retry()
            repository.getToken()
            repository.findFalcone(token, planets, vehicles)
        }
    }

    @Test
    fun shouldUpdateUiStateTimeTakenOnInit() {
        Truth.assertThat(viewModel.uiState.value.timeTaken).isEqualTo(timeTaken.toString())
    }

    @Test
    fun shouldPassTokenToRepoOnInit() {
        val repoToken = "repoToken"

        coEvery {
            repository.getToken()
        } returns UiState.Success(repoToken)

        viewModel = ResultViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository
        )

        coVerify(exactly = 1) {
            repository.findFalcone(
                repoToken,
                any(),
                any()
            )
        }
    }
}
