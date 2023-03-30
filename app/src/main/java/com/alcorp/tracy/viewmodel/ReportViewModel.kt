package com.alcorp.tracy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcorp.tracy.data.AppRepository
import com.alcorp.tracy.data.remote.response.*
import com.alcorp.tracy.data.remote.response.Map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportViewModel(private val repository: AppRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _insertedId = MutableLiveData<String>()
    val insertedId: LiveData<String> = _insertedId

    private val _nearbyMap = MutableLiveData<DataMap>()
    val nearbyMap: LiveData<DataMap> = _nearbyMap

    val message = MutableLiveData<String>()

    fun createReport(token: String, report: Report) {
        _isLoading.value = true
        val response: Call<ReportResponse> = repository.createReport(token, report)
        response.enqueue(object : Callback<ReportResponse> {
            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    message.value = "Success"
                    _insertedId.value = response.body()!!.data!!.InsertedID!!
                } else {
                    message.value = "Failed"
                }
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }

    fun createMap(token: String, map: Map) {
        _isLoading.value = true
        val response: Call<MetaResponse> = repository.createMap(token, map)
        response.enqueue(object : Callback<MetaResponse> {
            override fun onResponse(call: Call<MetaResponse>, response: Response<MetaResponse>) {
                _isLoading.value = false
                message.value = response.body()!!.meta!!.message!!
            }

            override fun onFailure(call: Call<MetaResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }

    fun getNearbyMap(token: String, lat: String, lng: String) {
        _isLoading.value = true
        val response: Call<MapResponse> = repository.getNearbyMap(token, lat, lng)
        response.enqueue(object : Callback<MapResponse> {
            override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    message.value = "Success"
                    _nearbyMap.value = response.body()!!.data!!
                } else {
                    message.value = "Failed"
                }
            }

            override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                _isLoading.value = false
                message.value = t.message
            }
        })
    }
}