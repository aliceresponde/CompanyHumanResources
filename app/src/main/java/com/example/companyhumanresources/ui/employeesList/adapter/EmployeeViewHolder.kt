package com.example.companyhumanresources.ui.employeesList.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.EmployeeItemBinding
import com.example.companyhumanresources.ui.employeesList.EmployeeItem

class EmployeeViewHolder private constructor(private val binding: EmployeeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): EmployeeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EmployeeItemBinding.inflate(layoutInflater, parent, false)
            return EmployeeViewHolder(binding)
        }
    }

    fun bind(employeeItem: EmployeeItem, onClick: (EmployeeItem) -> Unit) {
        val context = itemView.context
        binding.apply {
            itemContainer.setOnClickListener { onClick.invoke(employeeItem) }
            this.item = EmployeeViewHolder@employeeItem
            if (employeeItem.isNew) {
                isNew.text = context.getString(R.string.new_employee)
                isNew.setTextColor(Color.GREEN)
            } else {
                isNew.text = context.getString(R.string.old_employee)
                isNew.setTextColor(Color.BLUE)
            }
            executePendingBindings()
        }
    }
}