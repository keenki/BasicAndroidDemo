package me.keen.baselibs.base

import me.keen.baselibs.mvp.IPresenter
import me.keen.baselibs.mvp.IView

abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    protected lateinit var mPresenter: P

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}