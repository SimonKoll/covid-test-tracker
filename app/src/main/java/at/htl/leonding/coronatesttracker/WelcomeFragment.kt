package at.htl.leonding.coronatesttracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import at.htl.leonding.coronatesttracker.Model.CoronaReportAppModel
import at.htl.leonding.coronatesttracker.databinding.FragmentWelcomeBinding
import java.time.LocalDateTime

class WelcomeFragment : Fragment() {
    private val coronaReportAppModel: CoronaReportAppModel by activityViewModels()
    private val now: LocalDateTime = LocalDateTime.now();

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        binding.btcreateNewReport.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_welcomeFragment_to_newReport)
        }

        binding.btViewReportList.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_welcomeFragment_to_reportList)
        }

        var positive = 0
        var negative = 0
        var haid = 0
        var leonding = 0
        var linz = 0
        coronaReportAppModel.reportList.value?.filter { //TODO only today
            true

        }?.forEach {

            when (it.isPositive) {
                true -> positive++
                else -> negative++
            }
            when (it.office) {
                "Linz-Stadtplatz" -> linz++
                "Leonding-Meixnerkreuzung" -> leonding++
                "Parkplatz-Haidcenter" -> haid++
            }
        }

        if (negative == 0) {
        } else {
            binding.progressBar.setProgress(positive / (positive + negative) * 100);
        }


        binding.tvQuantityHaid.text = haid.toString()
        binding.tvQuantityLeonding.text = leonding.toString()
        binding.tvQuantityLinzStadtplatz.text = linz.toString()
        binding.progressBar.max = positive + negative;
        binding.progressBar.min = 0
        binding.progressBar.progress = positive;


        return binding.root
    }
}