package com.example.companyhumanresources.ui.emplogeeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.companyhumanresources.repository.EmployeeRepository

class EmployeeDetailViewModelFactory(private val repository: EmployeeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = EmployeeDetailViewModel(repository) as T
}
