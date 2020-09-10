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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeListViewModel(private val repository: EmployeeRepository) : ViewModel() {
    private val _viewState = MutableLiveData<Event<EmployeeListState>>()
    val viewState: LiveData<Event<EmployeeListState>> get() = _viewState

    private val _searchWord = MutableLiveData<Event<String>>()
    val searchWord: LiveData<Event<String>> get() = _searchWord

    private val _isDataUpdated = MutableLiveData<Event<Boolean>>()
    val isDataUpdated: LiveData<Event<Boolean>> get() = _isDataUpdated


    fun getEmployees() {
        viewModelScope.launch {
            val data = withContext(IO) { repository.getAllEmployees() }
            _viewState.value =
                if (data.isEmpty()) Event(ShowEmptyData)
                else Event(ShowEmployees(data))
        }
    }

    fun getNewEmployees() {
        viewModelScope.launch {
            _viewState.value = Event(ShowLoading)
            val data = withContext(IO) { repository.getNewEmployees() }
            _viewState.value =
                if (data.isEmpty()) Event(ShowEmptyData)
                else Event(ShowEmployees(data))
        }
    }

    fun getNewEmployeesByName(name: String) {
        if (name.isEmpty()) getEmployees()
        else
            viewModelScope.launch {
                _viewState.value = Event(ShowLoading)
                val data = withContext(IO) { repository.getNewEmployeesByName(name) }
                _viewState.value =
                    if (data.isEmpty()) Event(ShowEmptyData)
                    else Event(ShowEmployees(data))
            }
    }

    fun syncData() {
        viewModelScope.launch {
            withContext(IO) {
                repository.syncData()
            }
            _isDataUpdated.postValue(Event(true))
        }
    }
}

sealed class EmployeeListState {
    object ShowLoading : EmployeeListState()
    object ShowEmptyData : EmployeeListState()
    class ShowEmployees(val data: List<EmployeeItem>) : EmployeeListState()
}