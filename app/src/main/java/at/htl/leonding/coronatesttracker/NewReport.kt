package at.htl.leonding.coronatesttracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import at.htl.leonding.coronatesttracker.Model.CoronaReportAppModel
import at.htl.leonding.coronatesttracker.Model.Report
import at.htl.leonding.coronatesttracker.databinding.FragmentNewReportBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [NewReport.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewReport : Fragment() {
    private val coronaReportAppModel: CoronaReportAppModel by activityViewModels()

    var datePickerDialog: DatePickerDialog? = null
    var timePickerDialog: TimePickerDialog? = null

    var date: LocalDate = LocalDate.now()
    var time: LocalTime = LocalTime.now()

    private lateinit var binding: FragmentNewReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_report, container, false
        )

        binding.etDatePicker.inputType = 0
        binding.etTimePicker.inputType = 0

        val offices = resources.getStringArray(R.array.offices)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, offices)
        binding.dropdownPlace.setAdapter(arrayAdapter)

        binding.btSave.setOnClickListener {
            val report: Report = Report(
                binding.etInputId.text.toString(),
                //TODO: Monat muss noch richtig gesetzt werden
                LocalDateTime.of(date, time),
                binding.switchInputPositiv.isChecked,
                binding.dropdownPlace.text.toString()
            )
            coronaReportAppModel.addReport(report)
            it.findNavController().navigate(R.id.action_newReport_to_reportList)
        }

        binding.etDatePicker.setOnClickListener { view ->
            showDatePickerDialog(view)
        }

        binding.etTimePicker.setOnClickListener { view ->
            showTimePickerDialog(view)
        }

        return binding.root;

    }

    private fun showTimePickerDialog(view: View?) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minutes = calendar[Calendar.MINUTE]
        timePickerDialog = TimePickerDialog(
            context,
            { _, sHour, sMinute ->
                run {
                    binding.etTimePicker.setText("${sHour}:$sMinute")
                    time = LocalTime.of(sHour, sMinute)
                }
            },
            hour,
            minutes,
            true
        )
        timePickerDialog!!.show()
    }

    private fun showDatePickerDialog(view: View?) {
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        datePickerDialog =
            context?.let {
                DatePickerDialog(
                    it,
                    { _, year, monthOfYear, dayOfMonth ->
                        run {
                            binding.etDatePicker.setText(
                                dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                            )
                            date = LocalDate.of(year, monthOfYear, dayOfMonth)
                        }
                    },
                    year,
                    month,
                    day
                )
            }
        datePickerDialog!!.show()
    }


}