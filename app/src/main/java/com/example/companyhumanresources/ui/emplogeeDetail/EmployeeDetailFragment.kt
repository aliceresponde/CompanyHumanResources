package com.example.companyhumanresources.ui.emplogeeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.companyhumanresources.App
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.FragmentEmployeeDetailBinding

class EmployeeDetailFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeDetailBinding
    private val adapter by lazy { }
    private val viewModel: EmployeeDetailViewModel by viewModels {
        EmployeeDetailViewModelFactory(App.repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_list, container, false)

        return binding.root
    }

}