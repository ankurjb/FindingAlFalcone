package com.ankurjb.lengaburu.viewmodels

import com.ankurjb.lengaburu.ApiDataMapper
import com.ankurjb.lengaburu.MainCoroutineRule
import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.mapper.ViewDataMapper
import com.ankurjb.lengaburu.repo.MainRepository
import com.ankurjb.lengaburu.repo.MainRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @RelaxedMockK
    private lateinit var repository: MainRepository

    private val mapper: ViewDataMapper = ViewDataMapper()

    @RelaxedMockK
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        /*repository = MainRepositoryImpl(
            apiService = apiService,
            mapper = ApiDataMapper(),
            ioDispatcher = Dispatchers.Main
        )*/
        viewModel = MainViewModel(repository = repository, mapper = mapper)
    }

    @Test
    fun `on viewModel init`() = runTest {

    }

    @Test
    fun getPlanetList() {
        viewModel.getUnitTest()
        coVerify(exactly = 1) {
            repository.getUnitTest()
        }
    }

    @Test
    fun getVehicleList() {
    }

    @Test
    fun getPlanets() {
    }

    @Test
    fun getTimeTaken() {
    }

    @Test
    fun let() {
    }

    @Test
    fun isVehicleEnabled() {
    }
}
