package com.example.companyhumanresources.ui.employeesList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.ui.common.Event
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmployees
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmptyData
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowLoading
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeListViewModel(
    private val repository: EmployeeRepository,
    private val coroutineDispatcher: CoroutineDispatcher = IO,
) : ViewModel() {
    private val _viewState = MutableLiveData<EmployeeListState>()
    val viewState: LiveData<EmployeeListState> get() = _viewState

    private val _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String> get() = _searchWord

    private val _isDataUpdated = MutableLiveData<Event<Boolean>>()
    val isDataUpdated: LiveData<Event<Boolean>> get() = _isDataUpdated

    fun getEmployees() {
        if (_isDataUpdated.value?.peekContent() == false)
            syncData()
        if (_searchWord.value.isNullOrEmpty())
            viewModelScope.launch {
                _viewState.value = ShowLoading
                val data = withContext(coroutineDispatcher) { repository.getAllEmployees() }
                _viewState.value =
                    if (data.isEmpty()) ShowEmptyData
                    else ShowEmployees(data)
            }
        else getNewEmployeesByName(_searchWord.value)

    }

    fun getNewEmployees() {
        viewModelScope.launch {
            _viewState.value = ShowLoading
            val data = withContext(coroutineDispatcher) { repository.getNewEmployees() }
            _viewState.value =
                if (data.isEmpty()) ShowEmptyData
                else ShowEmployees(data)
        }
    }

    fun getNewEmployeesByName(name: String?) {
        if (name.isNullOrEmpty())
            getEmployees()
        else {
            viewModelScope.launch {
                _viewState.value = ShowLoading
                val data = withContext(coroutineDispatcher) { repository.getNewEmployeesByName(name) }
                _viewState.value =
                    if (data.isEmpty()) ShowEmptyData
                    else ShowEmployees(data)
            }
        }
    }

    fun getEmployeesByWage() {
        viewModelScope.launch {
            _viewState.value = ShowLoading
            val data = withContext(coroutineDispatcher) { repository.getEmployeesByWage() }
            _viewState.value =
                if (data.isEmpty()) (ShowEmptyData)
                else ShowEmployees(data)
        }
    }

    fun syncData() {
        viewModelScope.launch {
            withContext(coroutineDispatcher) {
                repository.syncData()
                _isDataUpdated.postValue(Event(true))
            }
        }
    }

    fun safeSearched(query: String?) {
        _searchWord.value = query ?: ""
    }
}

sealed class EmployeeListState {
    object ShowLoading : EmployeeListState()
    object ShowEmptyData : EmployeeListState()
    class ShowEmployees(val data: List<EmployeeItem>) : EmployeeListState()
}