package id.sharekom.simple_mvvm_compose.remote

import id.sharekom.simple_mvvm_compose.model.DummyData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<DummyData>
}