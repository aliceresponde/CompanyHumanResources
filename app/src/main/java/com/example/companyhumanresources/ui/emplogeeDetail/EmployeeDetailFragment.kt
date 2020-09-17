package com.example.companyhumanresources.ui.emplogeeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.companyhumanresources.App
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.FragmentEmployeeDetailBinding
import com.example.companyhumanresources.ui.common.EventObserver
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowBoss
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowSubordinates
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowEmptyData
import com.example.companyhumanresources.ui.emplogeeDetail.UiState.ShowLoading
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import com.example.companyhumanresources.ui.gone
import com.example.companyhumanresources.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeDetailBinding
    private val args: EmployeeDetailFragmentArgs by navArgs()
    private val viewModel: EmployeeDetailViewModel by viewModels()
    private val bossItem: EmployeeItem by lazy { args.employeeI }
    private lateinit var adapter: StringAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_detail, container, false)
        adapter = StringAdapter()
        binding.apply {
            boss = bossItem
            subordinatesRecycler.adapter = adapter
            isNew.text =
                  activity?.let {
                      if (bossItem.isNew) getString(R.string.new_employee) else getString(R.string.old_employee)
                  }
            isNew.setOnCheckedChangeListener { _, isChecked ->
                kotlin.run {
                    viewModel.updateBossAntiquity(bossItem.copy(isNew = isChecked))
                }
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner, EventObserver { state ->
            when (state) {
                ShowLoading -> showLoading()
                ShowEmptyData -> showNoSubordinates()
                is ShowBoss -> showBoss(state)
                is ShowSubordinates -> showSubordinates(state)
            }
        })

        viewModel.getSubordinatesOf(bossItem.id)
        return binding.root
    }

    private fun showBoss(state: ShowBoss) {
        binding.isNew.text =
            activity?.let {
                if (state.data.isNew) getString(R.string.new_employee) else getString(R.string.old_employee)
            }
        binding.boss = state.data
        binding.subordinatesRecycler.visible()
        binding.progress.gone()
    }

    private fun showSubordinates(state: ShowSubordinates) {
        binding.progress.gone()
        binding.subordinatesRecycler.visible()
        adapter.update(state.data)
    }

    private fun showLoading() {
        binding.progress.visible()
        binding.subordinatesRecycler.gone()
    }

    private fun showNoSubordinates() {
        binding.progress.gone()
        binding.subordinatesRecycler.gone()
        activity?.let {
            Toast.makeText(it, getString(R.string.no_subordinates), Toast.LENGTH_SHORT).show()
        }
    }
}