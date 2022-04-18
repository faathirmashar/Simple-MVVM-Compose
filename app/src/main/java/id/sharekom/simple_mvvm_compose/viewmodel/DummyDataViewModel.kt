package id.sharekom.simple_mvvm_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.sharekom.simple_mvvm_compose.model.DummyData
import id.sharekom.simple_mvvm_compose.remote.ApiConfig

class DummyDataViewModel: ViewModel() {

    fun getDummyData(): LiveData<DummyData> {
        val dummyData = MutableLiveData<DummyData>()

        ApiConfig.retrofit.getPosts().enqueue(object : retrofit2.Callback<DummyData> {
            override fun onResponse(call: retrofit2.Call<DummyData>, response: retrofit2.Response<DummyData>) {
                dummyData.value = response.body()
            }

            override fun onFailure(call: retrofit2.Call<DummyData>, t: Throwable) {
                dummyData.value = null
            }
        })

        return dummyData
    }
}