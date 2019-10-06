package com.example.ass8_mysqlqueryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
    }

    fun addEmployee(v: View) {
        var radioGroup: RadioGroup = findViewById(R.id.radio_group_gender)
        var selectedId:Int = radioGroup.checkedRadioButtonId
        var radioButton: RadioButton = findViewById(selectedId)
        val api: EmployeeApi = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") // Call PHP : http://10.0.2.2/movie_test/
            .addConverterFactory(GsonConverterFactory.create()) //Moshi
            .build()
            .create(EmployeeApi::class.java)
        api.insertEmp(
            edt_name.text.toString(),
            radioButton.text.toString(),
            edt_email.text.toString(),
            edt_salary.text.toString().toInt()
        ).enqueue(object : Callback<Employee> {
            // FOR PHP Callback<List<mapMovieDB>>
            override fun onResponse(
                call: Call<Employee>,
                response: retrofit2.Response<Employee>
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(
                    applicationContext, "Error onFailure " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }
    fun reset(v:View){
        edt_name.text.clear()
        radio_group_gender.clearCheck()
        edt_email.text.clear()
        edt_salary.text.clear()
    }
}