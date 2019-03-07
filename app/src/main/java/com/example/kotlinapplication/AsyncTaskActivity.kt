package com.example.kotlinapplication

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog

class AsyncTaskActivity : AppCompatActivity() {

    lateinit var  context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)
        context = this
    }

    internal inner class getQuestions : AsyncTask<Void,Void,String>(){

        lateinit var progressDialog: ProgressDialog
        var  hasIntent = false

        override fun doInBackground(vararg params: Void?): String {
            
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Downloading Questions .... ")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }
    }

    private fun isNetworkAvailable(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return  activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
