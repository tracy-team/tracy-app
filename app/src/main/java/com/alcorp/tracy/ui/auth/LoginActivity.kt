package com.alcorp.tracy.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alcorp.tracy.data.remote.response.UserLogin
import com.alcorp.tracy.databinding.ActivityLoginBinding
import com.alcorp.tracy.ui.main.MainActivity
import com.alcorp.tracy.utils.LoadingDialog
import com.alcorp.tracy.viewmodel.LoginViewModel
import com.alcorp.tracy.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        checkLogin()
    }

    private fun init() {
        loadingDialog = LoadingDialog(this)
        sharedPreferences = this.getSharedPreferences("account", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPassLogin.text.toString()

            if (email == "" || password == "") {
                Toast.makeText(this@LoginActivity, "Please fill the blank column", Toast.LENGTH_SHORT).show()
            } else {
                val user = UserLogin(
                    email,
                    password
                )
                loginViewModel.loginUser(user)

                loginViewModel.isLoading.observe(this) {
                    showLoading(it)
                }

                loginViewModel.message.observe(this) {
                    if (it.toString() == "Success") {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        loginViewModel.user.observe(this) { userResponse ->
                            editor.putString("username", userResponse.username)
                            editor.putString("name", userResponse.namaLengkap)
                            editor.putString("email", userResponse.email)
                            editor.putString("phone", userResponse.noHp)
                            editor.putString("dateOfBirth", userResponse.dateOfBirth)
                            editor.putString("address", userResponse.alamat)
                            editor.apply()
                        }
                        loginViewModel.token.observe(this) { token ->
                            editor.putString("token", token)
                            editor.apply()
                        }
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRegis.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisActivity::class.java))
        }
    }

    private fun checkLogin() {
        val token = sharedPreferences.getString("token", "")
        if (token != ""){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.showDialog() else loadingDialog.hideDialog()
    }
}