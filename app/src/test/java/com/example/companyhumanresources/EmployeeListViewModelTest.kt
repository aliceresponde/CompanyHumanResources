package com.example.companyhumanresources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.ui.common.Event
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmptyData
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowLoading
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
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
    private lateinit var viewStareObserver: Observer<Event<EmployeeListState>>

    @Mock
    private lateinit var isUpdateDataObserver: Observer<Event<Boolean>>

    @Mock
    private lateinit var searchWordObserver: Observer<String>

    @Mock
    private lateinit var repositoryMock: EmployeeRepository
    private lateinit var sut: EmployeeListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = EmployeeListViewModel(repositoryMock)
        sut.viewState.observeForever(viewStareObserver)
        sut.searchWord.observeForever(searchWordObserver)
        sut.isDataUpdated.observeForever(isUpdateDataObserver)
    }

    @Test
    fun `get EmployeesByName that return EmptyLis then ShowNoData`() = runBlocking(IO) {
        //given
        val data = listOf<EmployeeItem>()
        val name = "Ali"
        given(repositoryMock.getNewEmployeesByName(name)).willReturn(listOf())
        //when
        sut.getNewEmployeesByName(name)
        //then
        verify(viewStareObserver).onChanged(Event(ShowLoading))
        verify(viewStareObserver).onChanged(Event(ShowEmptyData))
    }
}