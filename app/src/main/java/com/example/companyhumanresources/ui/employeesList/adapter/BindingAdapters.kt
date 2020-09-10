package com.example.companyhumanresources.ui.employeesList.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("employeeWage")
fun formatPrice(view: TextView, wage: Long) {
    val formatter = DecimalFormat("$ #,###")
    val text = "${formatter.format(wage)}"
    view.text = text
}