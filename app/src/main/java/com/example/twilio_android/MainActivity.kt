package com.example.twilio_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity()
{
    private lateinit var mClient : OkHttpClient
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try
        {
            btnSend.setOnClickListener {
                sendSMS(applicationContext.getString(R.string.send_url) , object : Callback
                {
                    override fun onFailure(call : Call , e : IOException)
                    {
                        e.printStackTrace()
                    }

                    override fun onResponse(call : Call , response : Response)
                    {
                        runOnUiThread {
                            edtPhone.setText("")
                            edtMessage.setText((""))
                            Toast.makeText(applicationContext , "SMS Sent!" , Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                })
            }
        } catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    private fun sendSMS(url : String , callback : Callback) : Call
    {
        val formBody : RequestBody = FormBody.Builder().add("To" , edtPhone.text.toString())
            .add("Body" , edtMessage.text.toString()).build()
        val request : Request = Request.Builder().url(url).post(formBody).build()
        val response : Call = mClient.newCall(request)
        response.enqueue(callback)
        return response
    }
}