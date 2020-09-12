package com.example.companyhumanresources.ui.employeesList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.companyhumanresources.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineDispatcher

class EmployeeListViewModelFactory(private val repository: EmployeeRepository, private val coroutineDispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EmployeeRepository::class.java, CoroutineDispatcher::class.java).newInstance(repository,coroutineDispatcher)
    }
}
