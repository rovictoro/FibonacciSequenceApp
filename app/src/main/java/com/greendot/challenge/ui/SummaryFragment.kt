package com.greendot.challenge.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.greendot.challenge.R
import com.greendot.challenge.model.Summary
import com.greendot.challenge.model.SummaryListAdapter
import com.greendot.challenge.model.SummaryViewModel
import kotlinx.android.synthetic.main.summary_fragment.*

class SummaryFragment : Fragment() {

    lateinit var adapter: SummaryListAdapter
    private lateinit var viewModel: SummaryViewModel
    lateinit var summaryIn: Summary


    companion object {
        val ARG_NUMBER = "argument_name"
        val ARG_TIME = "argument_time"

        fun newInstance() = SummaryFragment()


        fun newInstance(summary: Summary):SummaryFragment{
            val args: Bundle = Bundle()
            args.putString(ARG_NUMBER, summary.number)
            args.putString(ARG_TIME, summary.time)

            val fragment = SummaryFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            summaryIn = Summary(it.getString(ARG_NUMBER) ?: "", it.getString(ARG_TIME) ?: "")
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.summary_fragment, container, false)
        return view//inflater.inflate(R.layout.summary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SummaryListAdapter(this.requireContext())
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProviders.of(this).get(SummaryViewModel::class.java)
         // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        if(summaryIn.number.isNotEmpty() && summaryIn.time.isNotEmpty()){
            viewModel.insert(summaryIn)
        }

        viewModel.allSummaries.observe(this, Observer { summaries ->
            // Update the cached copy of the words in the adapter.
            summaries?.let { adapter.setSummaries(it) }
        })
    }

}
