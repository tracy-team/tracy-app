package com.alcorp.tracy.ui.laporan

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alcorp.tracy.R
import com.alcorp.tracy.data.remote.response.Map
import com.alcorp.tracy.data.remote.response.Report
import com.alcorp.tracy.databinding.ActivityTambahLaporanBinding
import com.alcorp.tracy.utils.LoadingDialog
import com.alcorp.tracy.viewmodel.ReportViewModel
import com.alcorp.tracy.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class TambahLaporanActivity : AppCompatActivity(), View.OnClickListener, LocationListener {

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var locationManager: LocationManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityTambahLaporanBinding
    private val reportViewModel: ReportViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    private val locationPermissionCode = 2
    private var lat = "0"
    private var long = "0"
    private var address = ""
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        getLocation()
        setDate()

        loadingDialog = LoadingDialog(this)

        token = sharedPreferences.getString("token", "").toString()

        val jenis = intent.getStringExtra("jenis").toString()

        if (jenis != "lainnya") {
            binding.edtJenisKejahatan.setText(jenis)
        }

        binding.ivBack.setOnClickListener(this)
        binding.btnKirim.setOnClickListener(this)
    }

    private fun setDate() {
        val calendar = Calendar.getInstance()

        val getMonth = SimpleDateFormat("MMMM")
        val getDay = SimpleDateFormat("EEEE")
        val d = Date()

        val dayName = getDay.format(d)
        val month = getMonth.format(calendar.time)

        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_WEEK]

        binding.tvTanggal.text = "$dayName, $day $month $year"

        sharedPreferences = this.getSharedPreferences("account", Context.MODE_PRIVATE)
        binding.tvUser.text = "Hey, ${sharedPreferences.getString("username", "username")}"
        Glide.with(this)
            .load(sharedPreferences.getString("photo", "photoUrl"))
            .apply(RequestOptions().override(125, 125))
            .placeholder(ContextCompat.getDrawable(this, R.drawable.foto))
            .error(ContextCompat.getDrawable(this, R.drawable.foto))
            .into(binding.ivProfile)
    }

    override fun onClick(view: View) {
        when(view) {
            binding.ivBack -> { finish() }

            binding.btnKirim -> {
                getLocation()
                val jenis = binding.edtJenisKejahatan.text.toString()
                val keterangan = binding.edtKetarangan.text.toString()

                if (jenis == "" || keterangan == "") {
                    Toast.makeText(this@TambahLaporanActivity, "Please fill the blank column", Toast.LENGTH_SHORT).show()
                } else {
                    reportViewModel.getNearbyMap("Bearer $token", lat, long)

                    reportViewModel.isLoading.observe(this) {
                        showLoading(it)
                    }

                    reportViewModel.nearbyMap.observe(this) {
                        val namaKantor = it.namaKantor.toString()
                        val email = it.email.toString()
                        val picture = it.picture.toString()
                        val alamat = it.alamat.toString()

                        val report = Report(
                            jenis,
                            keterangan,
                            email
                        )

                        reportViewModel.createReport("Bearer $token", report)
                        reportViewModel.message.observe(this) { message ->
                            if (message.toString() == "Success") {
                                reportViewModel.insertedId.observe(this) { id ->
                                    val map = Map(
                                        id,
                                        lat,
                                        long,
                                        address
                                    )
                                    reportViewModel.createMap("Bearer $token", map)
                                }

                                val intent = Intent(this@TambahLaporanActivity, KantorPolisiActivity::class.java)
                                intent.putExtra("namaKantor", namaKantor)
                                intent.putExtra("picture", picture)
                                intent.putExtra("alamat", alamat)
                                startActivity(intent)
                                finish()
                            }
                            Toast.makeText(this@TambahLaporanActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude.toString()
        long = location.longitude.toString()

        val geocoder = Geocoder(this@TambahLaporanActivity)
        address = geocoder.getFromLocation(location.latitude, location.longitude,1).toString()
    }
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.showDialog() else loadingDialog.hideDialog()
    }
}