package com.example.kotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_auto_complete.*

class AutoCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_complete)

        var suggestion = arrayOf("Apple","Google","Samsung","Huawei","HTC")

        var  adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,suggestion)
        autoCompleteView.threshold = 0
        autoCompleteView.setAdapter(adapter)
        autoCompleteView.setOnFocusChangeListener({

            view , b -> if(b) autoCompleteView.showDropDown()
        })
    }
}
