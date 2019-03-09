package com.example.kotlinapplication.models

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.example.kotlinapplication.R
import kotlinx.android.synthetic.main.my_custom_view_item.view.*
import kotlinx.android.synthetic.main.my_custom_view_layout.view.*

class MyCustomView(context:Context,attrs:AttributeSet):LinearLayout(context,attrs){

    private var selectedItems : MutableList<String> = ArrayList()
    private var all : MutableList<String> = ArrayList()

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view  = inflater.inflate(R.layout.my_custom_view_layout,this,true)

        autoCompleteText.threshold = 1

        add.setOnClickListener {

            val selectedString = autoCompleteText.text.toString().trim()
            when{

                selectedString.isEmpty()-> Toast.makeText(getContext(),"Please Enter the data",Toast.LENGTH_SHORT).show()
                selectedItems.contains(selectedString)->Toast.makeText(getContext(),"Item Already added. ",Toast.LENGTH_SHORT).show()
                else ->{

                    selectedItems.add(0,selectedString)
                }
            }
        }



    }

    fun refreshData(clearData : Boolean){

        listView.adapter = MyCustomViewAdapter(context,R.layout.my_custom_view_item,selectedItems)
        if(clearData)autoCompleteText.setText("")


    }

    private fun setListviewHeightBasedOnchildren(listView: ListView){

        val listAdapter = listView.adapter ?: return
        var totalHeight = listView.paddingTop + listView.paddingBottom
        for (i in 0 until listAdapter.count){

            val listItem  = listAdapter.getView(i,null,listView)
            (listItem as? ViewGroup)?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT)
            listItem.measure(0,0)
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams
    }

    inner class MyCustomViewAdapter(context: Context?, var resource:Int, var objects: MutableList<String>?)
        :ArrayAdapter<String>(context,resource,objects)
    {
        private val inflater:LayoutInflater = LayoutInflater.from(context)
        override fun getCount(): Int {
           return objects.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = inflater.inflate(resource,parent,false)
            name.text = objects.get(position)
            delete.setOnClickListener {
                selectedItems.removeAt(position)
            }

            return view
        }
    }
}