package com.alcorp.tracy.ui.auth

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alcorp.tracy.data.remote.response.User
import com.alcorp.tracy.databinding.ActivityRegisBinding
import com.alcorp.tracy.utils.LoadingDialog
import com.alcorp.tracy.utils.setDatePickerNormal
import com.alcorp.tracy.viewmodel.LoginViewModel
import com.alcorp.tracy.viewmodel.ViewModelFactory
import java.util.*

class RegisActivity : AppCompatActivity(), View.OnClickListener {

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var binding: ActivityRegisBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        loadingDialog = LoadingDialog(this)

        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]

        binding.edtDateOfBirthRegis.setOnClickListener(this)
        binding.btnRegis.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    private fun setDate() = showDialog(1)

    override fun onCreateDialog(id: Int): Dialog {
        return DatePickerDialog(this, tgl, year, month, day)
    }

    private val tgl =
        DatePickerDialog.OnDateSetListener { _, thn, bln, hari ->
            binding.edtDateOfBirthRegis.setText(setDatePickerNormal(thn, bln + 1, hari))
        }

    override fun onClick(view: View) {
        when(view) {
            binding.edtDateOfBirthRegis -> {
                setDate()
            }

            binding.btnRegis -> {
                val username = binding.edtUsernameRegis.text.toString()
                val namaLengkap = binding.edtNameRegis.text.toString()
                val email = binding.edtEmailRegis.text.toString()
                val password = binding.edtPassRegis.text.toString()
                val confPassword = binding.edtConfPassRegis.text.toString()
                val noHp = binding.edtPhoneRegis.text.toString()
                val dateOfBirth = binding.edtDateOfBirthRegis.text.toString()
                val alamat = binding.edtAddressRegis.text.toString()

                if (username == "" || namaLengkap == "" || email == "" || password == "" || confPassword == "" || noHp == "" || dateOfBirth == "" || alamat == "") {
                    Toast.makeText(this@RegisActivity, "Please fill the blank column", Toast.LENGTH_SHORT).show()
                } else {
                    if (password == confPassword) {
                        val user = User(
                            username,
                            namaLengkap,
                            email,
                            password,
                            noHp,
                            dateOfBirth,
                            alamat
                        )
                        loginViewModel.regisUser(user)

                        loginViewModel.isLoading.observe(this) {
                            showLoading(it)
                        }

                        loginViewModel.message.observe(this) {
                            if (it == "Success") { finish() }
                            Toast.makeText(this@RegisActivity, it, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RegisActivity, "Password not same", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.btnLogin -> {
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.showDialog() else loadingDialog.hideDialog()
    }
}