package com.alcorp.tracy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcorp.tracy.data.AppRepository
import com.alcorp.tracy.data.remote.response.DataItemItem
import com.alcorp.tracy.data.remote.response.TestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _report = MutableLiveData<List<List<DataItemItem>>>()
    val report: LiveData<List<List<DataItemItem>>> = _report

    val message = MutableLiveData<String>()

    fun getListReport(token: String) {
        _isLoading.value = true
        val response: Call<TestResponse> = repository.getListReport(token)
        response.enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    message.value = "Success"
                    _report.value = response.body()!!.data!!
                } else {
                    message.value = "Failed"
                }
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }
}