package com.example.companyhumanresources.ui.employeesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.companyhumanresources.App
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.FragmentEmployeeListBinding
import com.example.companyhumanresources.ui.common.EventObserver
import com.example.companyhumanresources.ui.employeesList.adapter.EmployeeAdapter
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModelFactory
import com.example.companyhumanresources.ui.gone
import com.example.companyhumanresources.ui.hideKeyboard
import com.example.companyhumanresources.ui.visible

class EmployeeListFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeListBinding
    private val viewModel: EmployeeListViewModel by viewModels { EmployeeListViewModelFactory(App.repository) }
    private val adapter: EmployeeAdapter by lazy { EmployeeAdapter(onClickListener = ::showEmployeeDetail) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_list, container, false)
        binding.apply {
            lifecycleOwner = this@EmployeeListFragment
            employeeRecycler.adapter = adapter
            filterView.setOnClickListener { showFilerDialog() }
            searchView.setQuery("a", true);
            searchView.clearFocus();
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()

                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.getNewEmployeesByName(query?: "")
                    return false
                }
            })
        }

        viewModel.isDataUpdated.observe( viewLifecycleOwner, EventObserver{
            if (!it) viewModel.syncData()
        })

        viewModel.viewState.observe(
            viewLifecycleOwner,
            EventObserver { state ->
                when (state) {
                    EmployeeListState.ShowLoading -> showLoading()
                    EmployeeListState.ShowEmptyData -> showEmptyData()
                    is EmployeeListState.ShowEmployees -> showEmployees(state.data)
                }
            }
        )

        viewModel.getEmployees()
        return binding.root
    }

    private fun showEmployees(data: List<EmployeeItem>) {
        with(binding) {
            loading.gone()
            noData.gone()
            employeeRecycler.visible()
        }
        adapter.update(data)
    }

    private fun showEmptyData() {
        with(binding) {
            loading.gone()
            noData.visible()
            employeeRecycler.gone()
        }
    }

    private fun showLoading() {
        with(binding) {
            loading.visible()
            noData.gone()
            employeeRecycler.gone()
        }
    }

    private fun showFilerDialog() {
        Log.d("TAG", "showEmployeeDetail: ")
    }

    private fun showEmployeeDetail(employeeItem: EmployeeItem) {
        val navDirections= EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailFragment(employeeItem)
        findNavController().navigate(navDirections)
    }
}