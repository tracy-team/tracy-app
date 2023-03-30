package com.alcorp.tracy.ui.laporan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.ActivityJenisLaporanBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class JenisLaporanActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJenisLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val sharedPreferences = this.getSharedPreferences("account", Context.MODE_PRIVATE)
        binding.tvUser.text = "Hey, ${sharedPreferences.getString("username", "username")}"
        Glide.with(this)
            .load(sharedPreferences.getString("photo", "photoUrl"))
            .apply(RequestOptions().override(125, 125))
            .placeholder(ContextCompat.getDrawable(this, R.drawable.foto))
            .error(ContextCompat.getDrawable(this, R.drawable.foto))
            .into(binding.ivProfile)

        binding.ivBack.setOnClickListener(this)
        binding.btnPencurian.setOnClickListener(this)
        binding.btnPerampokan.setOnClickListener(this)
        binding.btnPenculikan.setOnClickListener(this)
        binding.btnNarkoba.setOnClickListener(this)
        binding.btnLainnya.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = Intent(this@JenisLaporanActivity, TambahLaporanActivity::class.java)
        when(view) {
            binding.ivBack -> { finish() }

            binding.btnPencurian -> {
                intent.putExtra("jenis", "Pencurian")
                startActivity(intent)
            }

            binding.btnPerampokan -> {
                intent.putExtra("jenis", "Perampokan")
                startActivity(intent)
            }

            binding.btnPenculikan -> {
                intent.putExtra("jenis", "Penculikan")
                startActivity(intent)
            }

            binding.btnNarkoba -> {
                intent.putExtra("jenis", "Narkoba")
                startActivity(intent)
            }

            binding.btnLainnya -> {
                intent.putExtra("jenis", "lainnya")
                startActivity(intent)
            }
        }
    }
}