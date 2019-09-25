package me.keen.mvpdemo.http

import io.reactivex.Observable
import retrofit2.http.GET

interface CustomerApi {
    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>
}