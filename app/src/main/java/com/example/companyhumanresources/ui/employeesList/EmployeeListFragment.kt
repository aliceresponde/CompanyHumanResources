package com.example.companyhumanresources.ui.employeesList

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.companyhumanresources.App
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.FilterDialogLayoutBinding
import com.example.companyhumanresources.databinding.FragmentEmployeeListBinding
import com.example.companyhumanresources.ui.common.EventObserver
import com.example.companyhumanresources.ui.employeesList.adapter.EmployeeAdapter
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmployees
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowEmptyData
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState.ShowLoading
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModelFactory
import com.example.companyhumanresources.ui.gone
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
        context
        binding.apply {
            lifecycleOwner = this@EmployeeListFragment
            employeeRecycler.adapter = adapter
            filterView.setOnClickListener { showFilerDialog() }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.safeSearched(query)
                    viewModel.getNewEmployeesByName(query)
                    searchView.setQuery("", false)
                    rootLayout.requestFocus()
                    val imm: InputMethodManager =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return true
                }
            })

            searchView.setOnCloseListener {
                viewModel.safeSearched("")
                viewModel.getEmployees()
                return@setOnCloseListener false
            }
        }

        viewModel.viewState.observe(
            viewLifecycleOwner,
            EventObserver { state ->
                when (state) {
                    ShowLoading -> showLoading()
                    ShowEmptyData -> showEmptyData()
                    is ShowEmployees -> showEmployees(state.data)
                }
            }
        )

        viewModel.isDataUpdated.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                viewModel.getEmployees()
            }
        })

        viewModel.syncData()
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
        activity?.let {
            val dialogBinding = FilterDialogLayoutBinding.inflate(LayoutInflater.from(it))

            AlertDialog.Builder(it)
                .setView(dialogBinding.root)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    run {
                        when (dialogBinding.filterGroup.checkedRadioButtonId) {
                            R.id.filterNewEmployee -> viewModel.getNewEmployees()
                            R.id.filterByWage -> viewModel.getEmployeesByWage()
                            else -> {
                            }
                        }
                    }
                }
                .show()
        }
    }

    private fun showEmployeeDetail(employeeItem: EmployeeItem) {
        val navDirections =
            EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailFragment(
                employeeItem)
        findNavController().navigate(navDirections)
    }
}