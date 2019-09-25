package me.keen.mvpdemo

import io.reactivex.Observable
import me.keen.baselibs.mvp.IModel
import me.keen.baselibs.mvp.IView
import me.keen.mvpdemo.http.Banner
import me.keen.mvpdemo.http.HttpResult

/**
 * @author xiekeen
 * @date 2019-09-24
 * @desc MainContract
 */
interface MainContract {
    interface View : IView {
        fun showBannerList(bannerList: MutableList<Banner>)
    }

    interface Model:IModel{
        fun getBanner(): Observable<HttpResult<MutableList<Banner>>>
    }
}