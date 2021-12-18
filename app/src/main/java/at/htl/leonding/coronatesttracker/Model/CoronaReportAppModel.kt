package at.htl.leonding.coronatesttracker.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CoronaReportAppModel : ViewModel() {
    private val _reportList: MutableLiveData<MutableList<Report>> =
        MutableLiveData(LinkedList<Report>())
    private var teststring: String = ""

    //TODO: change MutableList to ImmutableList
    val reportList: LiveData<MutableList<Report>>
        get() = _reportList


    public fun addReport(report: Report) {
        _reportList.value?.add(report)
    }

    public fun getReport(place: String) {
        teststring = reportList.value.toString()
    }

}