package com.alcorp.tracy.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.FragmentHomeBinding
import com.alcorp.tracy.ui.auth.LoginActivity
import com.alcorp.tracy.ui.laporan.JenisLaporanActivity
import com.alcorp.tracy.viewmodel.MainViewModel
import com.alcorp.tracy.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private var token = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLogin()
        init()
    }

    private fun init() {
        val calendar = Calendar.getInstance()

        val getMonth = SimpleDateFormat("MMMM")
        val getDay = SimpleDateFormat("EEEE")
        val d = Date()

        val dayName = getDay.format(d)
        val month = getMonth.format(calendar.time)

        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_WEEK]

        binding.tvTanggal.text = "$dayName, $day $month $year"

        val sharedPreferences = requireContext().getSharedPreferences("account", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", "").toString()
        binding.tvUser.text = "Hey, ${sharedPreferences.getString("username", "username")}"
        Glide.with(requireContext())
            .load(sharedPreferences.getString("photo", "photoUrl"))
            .apply(RequestOptions().override(125, 125))
            .placeholder(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.foto))
            .error(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.foto))
            .into(binding.ivProfile)

        binding.btnLapor.setOnClickListener {
            startActivity(Intent(context, JenisLaporanActivity::class.java))
        }

        getData()
    }

    private fun getData() {
        mainViewModel.getListReport("Bearer $token")
        mainViewModel.report.observe(viewLifecycleOwner) {
            val reportAdapter = ReportAdapter(it)
            reportAdapter.notifyDataSetChanged()

            binding.rvHome.setHasFixedSize(true)
            binding.rvHome.layoutManager = LinearLayoutManager(context)
            binding.rvHome.adapter = reportAdapter
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLogin() {
        val sharedPreferences = requireContext().getSharedPreferences("account", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        if (token == ""){
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }
}