package com.example.ass8_mysqlqueryinsert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var employeetList: ArrayList<Employee> = arrayListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager =
            LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?
        recycler_view.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                recycler_view.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view.addOnItemTouchListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Toast.makeText(
                    applicationContext, "You click on : " + employeetList[position].emp_name,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        callEmployeeData()
    }

    private fun callEmployeeData() {
        employeetList.clear();
        val serv: EmployeeApi = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") // Call PHP : http://10.0.2.2/movie_test/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeApi::class.java)
        serv.retrieveStudent()
            .enqueue(object : Callback<List<Employee>> {
                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {
                    response.body()?.forEach {
                        employeetList.add(
                            Employee(
                                it.emp_name,
                                it.emp_gender,
                                it.emp_email,
                                it.emp_salary
                            )
                        )
                    }
                    recycler_view.adapter = EmployeeAdapter(employeetList, applicationContext)
                    text1.text = "Employee List : " + employeetList.size.toString() + " person."
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) =
                    t.printStackTrace()
            })
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemTouchListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view?.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }

    fun addEmp(view: View) {
        val intent = Intent(this, InsertActivity::class.java)
        startActivity(intent)
    }
}