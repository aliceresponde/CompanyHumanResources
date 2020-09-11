package com.example.companyhumanresources.ui.emplogeeDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.companyhumanresources.R
import com.example.companyhumanresources.databinding.EmployeeNameItemBinding
import com.example.companyhumanresources.ui.emplogeeDetail.StringAdapter.StringViewHolder

class StringAdapter(private var list: List<String> = listOf()) :
    RecyclerView.Adapter<StringViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.employee_name_item, parent, false)
        return StringViewHolder(view)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(data: List<String>) {
        list = data
        notifyDataSetChanged()
    }

    inner class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = EmployeeNameItemBinding.bind(itemView)
        fun bind(name: String) {
            binding.nama.text = name
        }
    }
}


