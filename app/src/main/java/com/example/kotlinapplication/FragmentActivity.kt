package com.example.kotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinapplication.fargment.FragmentTwo
import com.example.kotlinapplication.fargment.Fragmentone
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    var  isFragmentOneLoaded = true
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
           showFragmentOne()
        btn_change.setOnClickListener {

            if(isFragmentOneLoaded)
                showFragmentTwo()
            else
                showFragmentOne()
        }
    }

    fun showFragmentOne(){

        val transaction = manager.beginTransaction()
        val  fragment = Fragmentone()
        transaction.replace(R.id.fragmentHolder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = true
    }


    fun showFragmentTwo(){

        val transaction = manager.beginTransaction()
        val  fragment = FragmentTwo()
        transaction.replace(R.id.fragmentHolder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = false
    }
}
