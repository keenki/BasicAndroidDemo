package me.keen.baselibs.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("platform", "Android")
                .addQueryParameter("phoneModel", "")
                .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}