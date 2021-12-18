package at.htl.leonding.coronatesttracker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels

import at.htl.leonding.coronatesttracker.Model.CoronaReportAppModel

class MainActivity : AppCompatActivity() {

    private val coronaReportAppModel: CoronaReportAppModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}