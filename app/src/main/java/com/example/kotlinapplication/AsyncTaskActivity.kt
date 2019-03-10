package com.example.kotlinapplication

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*

class AsyncTaskActivity : AppCompatActivity() {

    lateinit var  context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)
        context = this
        getQuestions().execute()
    }

    internal inner class getQuestions : AsyncTask<Void,Void,String>(){

        lateinit var progressDialog: ProgressDialog
        var  hasIntent = false

        override fun doInBackground(vararg params: Void?): String {
           if(isNetworkAvailable()) {

               hasIntent = true
               val client = OkHttpClient()
               val url = "https://script.googleusercontent.com/marcos/echo?user_content_key=1tgBN"
               val request = Request.Builder.url(url).build()
               val response = client.newCall(request).execute()
               return response.body()?.string().toString()
           }
            else{

               return ""
           }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()

            if(hasIntent){
                tv_result.text = result
            }
            else
                tv_result.text = "No Internet"
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
