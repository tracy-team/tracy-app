package com.alcorp.tracy.ui.laporan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.ActivityKantorPolisiBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class KantorPolisiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityKantorPolisiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKantorPolisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val namaKantor = intent.getStringExtra("namaKantor")
        val picture = intent.getStringExtra("picture")
        val alamat = intent.getStringExtra("alamat")

        binding.tvNamaKantor.text = namaKantor
        binding.tvAlamat.text = alamat

        Glide.with(this)
            .load(picture)
            .apply(RequestOptions().override(100, 100))
            .placeholder(ContextCompat.getDrawable(this.applicationContext, R.drawable.foto))
            .error(ContextCompat.getDrawable(this.applicationContext, R.drawable.foto))
            .into(binding.ivProfile)

        binding.ivBack.setOnClickListener(this)
        binding.btnLiveChat.setOnClickListener(this)
        binding.btnVideoCall.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view) {
            binding.ivBack -> {
                finish()
            }
            binding.btnLiveChat -> {
                Toast.makeText(this, "This feature will available soon", Toast.LENGTH_SHORT).show()
            }
            binding.btnVideoCall -> {
                Toast.makeText(this, "This feature will available soon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}