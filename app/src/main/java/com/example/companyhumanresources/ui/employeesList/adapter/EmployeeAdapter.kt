package com.example.companyhumanresources.ui.employeesList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.companyhumanresources.ui.employeesList.EmployeeItem

class EmployeeAdapter(
    private var data: MutableList<EmployeeItem> = mutableListOf(),
    private val onClickListener: (EmployeeItem) -> Unit
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    fun update(newItemList: List<EmployeeItem>) {
        val oldList = data
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ShowItemDiffCallback(oldList, newItemList)
        )
        data.clear()
        data.addAll(newItemList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder.from(parent)

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener)
    }

    override fun getItemCount(): Int = data.size

}

class ShowItemDiffCallback(
    private var oldItemList: List<EmployeeItem>,
    private var newItemList: List<EmployeeItem>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemList[oldItemPosition].id == newItemList[newItemPosition].id

    override fun getOldListSize(): Int = oldItemList.size

    override fun getNewListSize(): Int = newItemList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemList[oldItemPosition] == newItemList[newItemPosition]
}


