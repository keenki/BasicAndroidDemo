package me.keen.baselibs.base

import android.view.View
import me.keen.baselibs.mvp.IPresenter
import me.keen.baselibs.mvp.IView

/**
 * @author xiekeen
 * @date 2019-09-23
 * @desc BaseMvpFragment
 */
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected lateinit var mPresenter: P

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }
}