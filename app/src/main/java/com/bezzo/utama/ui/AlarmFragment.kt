package com.bezzo.utama.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.*

import com.bezzo.utama.R
import com.bezzo.utama.services.PeriodicService
import com.bezzo.utama.services.WorkManagerService
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.util.concurrent.TimeUnit

class AlarmFragment : Fragment() {

    lateinit var networkWorker : OneTimeWorkRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val constraintSyncWorker = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        btn_run.setOnClickListener {
            networkWorker = OneTimeWorkRequest.Builder(WorkManagerService::class.java)
                .setConstraints(constraintSyncWorker).build()
            WorkManager.getInstance().enqueue(networkWorker)
        }

        btn_periodic.setOnClickListener {
            val refreshWork = PeriodicWorkRequest.Builder(
                PeriodicService::class.java,
                et_looping.text.toString().toLong(), TimeUnit.MINUTES)
                .setConstraints(constraintSyncWorker)
                .build()

            WorkManager.getInstance().enqueue(refreshWork)
        }
    }
}
