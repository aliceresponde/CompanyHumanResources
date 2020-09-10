package com.example.companyhumanresources.ui.employeesList

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
import com.example.companyhumanresources.databinding.FragmentEmployeeListBinding
import com.example.companyhumanresources.ui.common.EventObserver
import com.example.companyhumanresources.ui.employeesList.adapter.EmployeeAdapter
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListState
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModelFactory
import com.example.companyhumanresources.ui.gone
import com.example.companyhumanresources.ui.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        viewModel.isDataUpdated.observe(viewLifecycleOwner, EventObserver {
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
        val singleItems =
            arrayOf(getString(R.string.dialog_opc_antiquity), getString(R.string.dialog_opc_wege))
        val checkedItem = 1
        val context = activity?.baseContext!!
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialog_filter_title))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which -> }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->

            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    private fun showEmployeeDetail(employeeItem: EmployeeItem) {
        val navDirections =
            EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailFragment(
                employeeItem)
        findNavController().navigate(navDirections)
    }
}