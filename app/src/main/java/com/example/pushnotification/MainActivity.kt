package com.example.pushnotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val URL_POST = "https://mcc-users-api.herokuapp.com/add_new_user"
    lateinit var mRequestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.hide()

        goSignUpScreen.setOnClickListener {
            var intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        getRegToken()

        signUp_btn.setOnClickListener {
            postToServer()
        }
    }

    private fun getRegToken() {
      var token =  FirebaseInstanceId.getInstance().getToken()
        Log.e("MyApp","Token: " +token)
    }

    fun postToServer() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext())
        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_POST,
            Response.Listener { response ->
                Log.e("sad", response)
                val ob = JSONObject(response)
            },
            Response.ErrorListener { error ->
                Log.e("sad", error.message.toString())
            }) {

            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["firstName"] = Fname.text.toString()
                map["secondName"] = Lname.text.toString()
                map["email"] = email.text.toString()
                map["password"] = password.text.toString()
                return map
            }
        }

        mRequestQueue.add(stringRequest)
    }
}