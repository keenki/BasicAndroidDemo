package me.keen.baselibs.mvp

interface IPresenter<in V : IView> {
    /**
     * 绑定 View
     */
    fun attachView(mView: V)

    /**
     * 解绑 View
     */
    fun detachView()
}