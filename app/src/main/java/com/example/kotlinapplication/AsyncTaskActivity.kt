package com.example.kotlinapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinapplication.models.Question
import kotlinx.android.synthetic.main.activity_async_task.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException

@Suppress("DEPRECATION")
class AsyncTaskActivity : AppCompatActivity() {

    lateinit var  context: Context
    var QuestionList : MutableList<Question>  = ArrayList()
    var index = 1
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)
        context = this
        btn_next.isEnabled = true
        btn_next.alpha-0.5.toFloat()
        getQuestions().execute()
    }

    fun   UpdateQuestion(){

        val selected = rg_choice.checkedRadioButtonId
        if(selected == -1)
        {
            Toast.makeText(this,"Please Select Answer. ",Toast.LENGTH_SHORT).show()
            return
        }

        if(index<QuestionList.size){

            when(selected){

                rb_choice1.id ->{

                    if(QuestionList[index].Answer == 1)
                        score++
                }
                rb_choice2.id ->{

                    if(QuestionList[index].Answer == 2)
                        score++
                }
                rb_choice3.id ->{

                    if(QuestionList[index].Answer == 3)
                        score++
                }
                rb_choice4.id ->{

                    if(QuestionList[index].Answer == 4)
                        score++
                }
            }

            index ++

            if(index < QuestionList.size){

                tv_question.text = QuestionList[index].question
                rb_choice1.text = QuestionList[index].option1
                rb_choice2.text = QuestionList[index].option2
                rb_choice3.text = QuestionList[index].option3
                rb_choice4.text = QuestionList[index].option4

                rg_choice.clearCheck()

                if ((index+1) == QuestionList.size){
                    btn_next.text = "finish"
                }

            }else{

                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Your Score")
                dialog.setMessage("You have answered  "+score + " out of "+ QuestionList.size)
                dialog.setPositiveButton("Close",{
                    dialogInterface: DialogInterface, i: Int ->dialogInterface.dismiss()
                    finish()
                })

                dialog.show()
            }
        }
    }

    internal inner class getQuestions : AsyncTask<Void,Void,String>(){

        lateinit var progressDialog: ProgressDialog
        var  hasIntent = false

        override fun doInBackground(vararg params: Void?): String {
           if(isNetworkAvailable()) {

               hasIntent = true
               val client = OkHttpClient()
               val url = "https://script.googleusercontent.com/marcos/echo?user_content_key=1tgBN"
               val request = Request.Builder().url(url).build()
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

            if(hasIntent) {

                try {

                    val resutArray = JSONArray(result)

                    for (i in 0 .. (resutArray.length() -1))
                    {

                        val currentObject = resutArray.getJSONObject(i)
                        val obj = Question()
                        obj.question = currentObject.getString("Question")
                        obj.option1 = currentObject.getString("option1")
                        obj.option2 = currentObject.getString("option2")
                        obj.option3 = currentObject.getString("option3")
                        obj.option4 = currentObject.getString("option4")
                        obj.Answer = currentObject.getInt("Answer")
                        QuestionList.add(obj)
                    }

                    if (index == -1){

                        index++

                        tv_question.text = QuestionList[index].question
                        rb_choice1.text = QuestionList[index].option1
                        rb_choice2.text = QuestionList[index].option2
                        rb_choice3.text = QuestionList[index].option3
                        rb_choice4.text = QuestionList[index].option4
                    }

                    btn_next.isEnabled = true
                    btn_next.alpha = 1.toFloat()

                    btn_next.setOnClickListener {
                        UpdateQuestion()
                    }

                }catch (e:JSONException){


                }
            }
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
