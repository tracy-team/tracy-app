package com.alcorp.tracy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcorp.tracy.data.AppRepository
import com.alcorp.tracy.data.remote.response.MetaResponse
import com.alcorp.tracy.data.remote.response.User
import com.alcorp.tracy.data.remote.response.UserLogin
import com.alcorp.tracy.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: AppRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    val message = MutableLiveData<String>()

    fun regisUser(user: User) {
        _isLoading.value = true
        val response: Call<MetaResponse> = repository.regisUser(user)
        response.enqueue(object : Callback<MetaResponse> {
            override fun onResponse(call: Call<MetaResponse>, response: Response<MetaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    message.value = "Success"
                } else {
                    message.value = response.body()!!.meta!!.message!!
                }
            }

            override fun onFailure(call: Call<MetaResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }

    fun loginUser(userModel: UserLogin) {
        _isLoading.value = true
        val response: Call<UserResponse> = repository.loginUser(userModel)
        response.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    message.value = "Success"
                    _user.value = response.body()!!.data!!.user!!
                    _token.value = response.body()!!.data!!.token!!
                } else {
                    message.value = "Login Failed"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }
}