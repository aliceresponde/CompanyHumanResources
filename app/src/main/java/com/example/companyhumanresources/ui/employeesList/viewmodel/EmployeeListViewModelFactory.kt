package com.example.companyhumanresources.ui.employeesList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.companyhumanresources.repository.EmployeeRepository

class EmployeeListViewModelFactory(private val repository: EmployeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EmployeeRepository::class.java).newInstance(repository)
    }
}
