package com.example.ass8_mysqlqueryinsert

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.emp_item_layout.view.*

class EmployeeAdapter(val item: List<Employee>, val context: Context) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_item = LayoutInflater.from(parent.context).inflate(R.layout.emp_item_layout,parent,false)
        return ViewHolder(view_item)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName?.text =  holder.tvName.text.toString()+item[position].emp_name.capitalize()
        holder.tvGender?.text = holder.tvGender.text.toString()+item[position].emp_gender
        holder.tvEmail?.text = holder.tvEmail.text.toString()+item[position].emp_email.toString()
        holder.tvSalary?.text = holder.tvSalary.text.toString()+item[position].emp_salary.toString()
    }

    class ViewHolder (view:View) : RecyclerView.ViewHolder(view){
        val tvName : TextView = view.tv_name
        val tvGender : TextView = view.tv_gender
        val tvEmail : TextView = view.tv_email
        val tvSalary : TextView = view.tv_salary
    }

}