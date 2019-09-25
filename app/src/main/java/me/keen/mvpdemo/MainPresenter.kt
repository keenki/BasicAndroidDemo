package me.keen.mvpdemo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.keen.baselibs.mvp.BasePresenter
import me.keen.baselibs.rx.BaseObserver
import me.keen.mvpdemo.http.Banner
import me.keen.mvpdemo.http.CustomerRetrofit
import me.keen.mvpdemo.http.HttpResult

/**
 * @author xiekeen
 * @date 2019-09-24
 * @desc MainPresenter
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>() {
    fun getBanner() {
        addDisposable(
            CustomerRetrofit.service.getHomeBanner()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseObserver<HttpResult<MutableList<Banner>>>(mView!!) {
                    override fun onSuccess(t: HttpResult<MutableList<Banner>>) {
                        mView?.showBannerList(t.data)
                    }

                })
        )
    }


}