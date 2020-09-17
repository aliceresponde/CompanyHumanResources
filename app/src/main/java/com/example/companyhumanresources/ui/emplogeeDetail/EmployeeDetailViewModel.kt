package com.example.companyhumanresources.ui.emplogeeDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.ui.common.Event
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowBoss
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowEmptyData
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowLoading
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowSubordinates
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeDetailViewModel@ViewModelInject constructor(private val repository: EmployeeRepository) : ViewModel() {
    private val _viewState = MutableLiveData<Event<UiState>>()
    val viewState: LiveData<Event<UiState>> get() = _viewState


    fun getSubordinatesOf(bossId: Long) {
        viewModelScope.launch {
            _viewState.value = Event(ShowLoading)
            val data = withContext(Dispatchers.IO) { repository.getSubordinatesOf(bossId) }
            _viewState.value =
                if (data.isEmpty()) Event(ShowEmptyData)
                else Event(ShowSubordinates(data))
        }
    }

    fun updateBossAntiquity(employee: EmployeeItem) {
        viewModelScope.launch {
            withContext(IO) {
                repository.updateEmployee(employee.toEntity())
                _viewState.postValue(Event(ShowLoading))
                val data = repository.getEmployeeById(employee.id)
                _viewState.postValue(Event(ShowBoss(data)))
            }
        }
    }

    private fun EmployeeItem.toEntity() = Employee(id, name, position, wage, isNew)
}


sealed class UiState {
    object ShowLoading : UiState()
    object ShowEmptyData : UiState()
    class ShowBoss(val data: EmployeeItem) : UiState()
    class ShowSubordinates(val data: List<String>) : UiState()
}