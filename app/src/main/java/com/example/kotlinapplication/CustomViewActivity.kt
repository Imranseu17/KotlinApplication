package com.example.kotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val list : MutableList<String> = ArrayList()

        list.add("Kotlin")
        list.add("Java")
        list.add("C++")
        list.add("Python")
        list.add("JavaScript")
        list.add("GO")
        list.add("PHP")
        list.add("C")
        list.add("Swift")
        list.add("Angular")

        customView.setData(list)
        customView.setTitle("Languages")
        btn_submit.setOnClickListener {

            Toast.makeText(this,"Selected Datas : "+customView.getSelectedData().toString(),Toast.LENGTH_SHORT).show()
        }
    }
}
