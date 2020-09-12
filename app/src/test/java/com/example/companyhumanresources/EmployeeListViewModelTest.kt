package com.example.companyhumanresources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmployees
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmptyData
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowLoading
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewStateObserver: Observer<EmployeeListState>

    @Mock
    private lateinit var searchWordObserver: Observer<String>

    @Mock
    private lateinit var repositoryMock: EmployeeRepository
    private lateinit var sut: EmployeeListViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        sut = EmployeeListViewModel(repositoryMock, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `syncData mark as true`() = runBlockingTest{
        // given
        given(repositoryMock.syncData()).willReturn( print("e"))
        //when
        sut.syncData()
        //then
        verify(repositoryMock, times(1)).syncData()
        Assert.assertEquals(true, sut.isDataUpdated.value?.peekContent())
    }

    @Test
    fun `safeSearched save the query word`(){
        // given
        sut.searchWord.observeForever(searchWordObserver)
        //when
        val name = "Ali"
        sut.safeSearched(name)
        verify(searchWordObserver).onChanged(name)
    }

    @Test
    fun `get EmployeesByName that return EmptyLis then ShowEmptyData`() = runBlockingTest {
        //given
        val data = listOf<EmployeeItem>()
        val name = "Ali"
        sut.viewState.observeForever(viewStateObserver)
        given(repositoryMock.getNewEmployeesByName(name)).willReturn(data)
        //when
        sut.getNewEmployeesByName(name)
        //then
        verify(viewStateObserver).onChanged(ShowLoading)
        verify(viewStateObserver).onChanged(ShowEmptyData)
    }

//    @Test
//    fun `get EmployeesByName that return none employees then ShowEmployees`() = runBlockingTest {
//        //given
//        val data = listOf(EmployeeItem(1,"","", 2, true))
//        val name = "Ali"
//        sut.viewState.observeForever(viewStateObserver)
//        given(repositoryMock.getNewEmployeesByName(name)).willReturn(data)
//        //when
//        sut.getNewEmployeesByName(name)
//        //then
//        verify(viewStateObserver).onChanged(ShowLoading)
//        verify(viewStateObserver).onChanged(ShowEmployees(data))
//    }
}