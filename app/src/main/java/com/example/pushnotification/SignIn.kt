package com.example.pushnotification


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    val URL_POST = "https://mcc-users-api.herokuapp.com/add_reg_token"
    lateinit var token:String
    lateinit var mRequestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        getSupportActionBar()!!.hide()

        goSigninScreen.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        getRegToken()


        signIn_btn.setOnClickListener {
            postToServer()
        }


    }
    private fun getRegToken() {
         token = FirebaseInstanceId.getInstance().getToken().toString()
        Log.e("MyApp","Token: " +token)
    }
    fun postToServer() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext())
        val stringRequest = object : StringRequest(
                Request.Method.PUT, URL_POST,
                Response.Listener { response ->
                    Log.e("sad", response)
                    val ob = JSONObject(response)
                },
                Response.ErrorListener { error ->
                    Log.e("sad", error.message.toString())
                }) {

            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.put("email", name_sign.text.toString())
                map.put("password", pass_sign.text.toString())
                map.put("reg_token", token)
                return map
            }
        }

        mRequestQueue.add(stringRequest)
    }
}