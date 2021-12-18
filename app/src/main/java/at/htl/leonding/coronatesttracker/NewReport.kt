package at.htl.leonding.coronatesttracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import at.htl.leonding.coronatesttracker.Model.CoronaReportAppModel
import at.htl.leonding.coronatesttracker.Model.Report
import at.htl.leonding.coronatesttracker.databinding.FragmentNewReportBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.logging.Logger

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
    private var idUnique: Boolean = true;
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

        setListener()


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

    private fun validate(view: View?) {
        val report: Report = Report(
            binding.etInputId.text.toString(),
            LocalDateTime.of(date, time),
            binding.switchInputPositiv.isChecked,
            binding.dropdownPlace.text.toString()
        )
        coronaReportAppModel.addReport(report)
        view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)

    }

    private fun setListener() {
        binding.btSave.setOnClickListener {
            coronaReportAppModel.reportList.value?.forEach {
                if (binding.etInputId.text.toString() == it.id) {
                    idUnique = false
                    binding.etInputId.text.clear()
                 } else if (binding.etInputId.text.toString() == "") {
                    idUnique = false
                }
            }
            if (idUnique) {
                validate(it)
            }
            else {
                idUnique = true
                setListener()
                Logger.getLogger("warnung").warning("id entweder nicht eindeutig oder leer")
            }
        }
    }
}


