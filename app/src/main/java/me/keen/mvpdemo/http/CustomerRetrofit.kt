package me.keen.mvpdemo.http

import me.keen.baselibs.http.RetrofitFactory

object CustomerRetrofit : RetrofitFactory<CustomerApi>() {

    override fun baseUrl() = Constant.BASE_URL

    override fun getService(): Class<CustomerApi> = CustomerApi::class.java
}