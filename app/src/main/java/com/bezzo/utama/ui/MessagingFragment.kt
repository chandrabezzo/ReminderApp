package com.bezzo.utama.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.bezzo.utama.BuildConfig
import com.bezzo.utama.R
import com.bezzo.utama.model.Data
import com.bezzo.utama.model.NotificationRequest
import com.bezzo.utama.model.NotificationResponse
import com.bezzo.utama.utils.AppConstants
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_messaging.*

class MessagingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messaging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mb_kirim.setOnClickListener { sendNotif(et_title.text.toString(), et_message.text.toString()) }
    }

    fun sendNotif(title: String, message: String){
        val data = Data()
        data.title = title
        data.message = message

        val request = NotificationRequest()
        request.to = Hawk.get(AppConstants.FCM_TOKEN)
        request.data = data

        val headers = HashMap<String, String>()
        headers[AppConstants.AUTHORIZATION] = BuildConfig.FCM_KEY

        AndroidNetworking.post("https://fcm.googleapis.com/fcm/send")
            .addHeaders(headers)
            .addApplicationJsonBody(request)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(NotificationResponse::class.java, object : ParsedRequestListener<NotificationResponse>{
                override fun onResponse(response: NotificationResponse?) {
                    Toast.makeText(context, getString(R.string.send_fcm_success), Toast.LENGTH_LONG).show()
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(context, getString(R.string.send_fcm_error), Toast.LENGTH_LONG).show()
                }
            })
    }
}
