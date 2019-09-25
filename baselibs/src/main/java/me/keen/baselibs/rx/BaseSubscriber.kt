package me.keen.baselibs.rx

import io.reactivex.observers.DisposableObserver
import me.keen.baselibs.bean.BaseBean
import me.keen.baselibs.http.ErrorCode
import me.keen.baselibs.http.exception.ExceptionHandle
import me.keen.baselibs.mvp.IView
import me.keen.baselibs.utils.NetWorkUtil

abstract class BaseObserver<T : BaseBean> : DisposableObserver<T> {

    private var mView: IView? = null
    private var mErrorMsg = ""
    private var bShowLoading = true

    constructor(view: IView) {
        this.mView = view
    }

    constructor(view: IView, bShowLoading: Boolean) {
        this.mView = view
        this.bShowLoading = bShowLoading
    }

    /**
     * 成功的回调
     */
    protected abstract fun onSuccess(t: T)

    /**
     * 错误的回调
     */
    protected fun onError(t: T) {}

    override fun onStart() {
        super.onStart()
        if (bShowLoading) mView?.showLoading()
        if (!NetWorkUtil.isConnected()) {
            onComplete()
        }
    }

    override fun onNext(t: T) {
        mView?.hideLoading()
        when {
            t.errorCode == ErrorCode.SUCCESS -> onSuccess(t)
            t.errorCode == ErrorCode.TOKEN_INVALID -> {
                // TODO Token 过期，重新登录
            }
            else -> {
                onError(t)
//                if (t.errorMsg.isNotEmpty())
            }
        }
    }

    override fun onError(e: Throwable) {
        mView?.hideLoading()
        if (mView == null) {
            throw RuntimeException("mView can not be null")
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(e)
        }
    }

    override fun onComplete() {
        mView?.hideLoading()
    }

}