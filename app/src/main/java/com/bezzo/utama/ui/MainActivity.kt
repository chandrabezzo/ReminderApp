package com.bezzo.utama.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bezzo.utama.R
import com.bezzo.utama.utils.AppConstants
import com.google.firebase.iid.FirebaseInstanceId
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                launchFragment(R.id.fl_main, AlarmFragment::class.java)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                launchFragment(R.id.fl_main, MessagingFragment::class.java)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) launchFragment(R.id.fl_main, AlarmFragment::class.java)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                val fcmToken = it.result?.token
                Hawk.put(AppConstants.FCM_TOKEN, fcmToken)
            }
    }

    fun launchFragment(contentReplace: Int, classFragment: Class<*>,
                                         init: Bundle.() -> Unit = {}){
        var fragment: Fragment? = null

        try {
            fragment = classFragment.newInstance() as Fragment
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val transaction = this.supportFragmentManager.beginTransaction()

        val data = Bundle()
        data.init()
        fragment?.arguments = data

        fragment?.let { transaction.replace(contentReplace, it) }

        transaction.commit()
    }
}
