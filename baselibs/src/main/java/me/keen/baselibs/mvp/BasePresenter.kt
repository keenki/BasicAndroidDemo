package me.keen.baselibs.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * @author xiekeen
 * @date 2019-09-23
 * @desc BasePresenter
 */
abstract class BasePresenter<M : IModel, V : IView> : IPresenter<V>, LifecycleObserver {

    protected var mModel: M? = null
    protected var mView: V? = null

    private val isViewAttached: Boolean
        get() = mView != null

    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * 创建 Model
     */
    open fun createModel(): M? = null

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    override fun attachView(mView: V) {
        this.mView = mView
        mModel = createModel()
        if (mView is LifecycleOwner) {
            mView.lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) mView.lifecycle.addObserver(mModel as LifecycleObserver)
        }
        if (useEventBus()) EventBus.getDefault().register(this)
    }

    override fun detachView() {
        if (useEventBus()) EventBus.getDefault().unregister(this)
        unDispose()// 保证activity结束时取消所有正在执行的订阅
        mModel?.onDetach()
        mView = null
        mModel = null
        mCompositeDisposable = null
    }

    open fun addDisposable(disposable: Disposable?) {
        mCompositeDisposable = mCompositeDisposable ?: CompositeDisposable()
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    private fun unDispose() {
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    open fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }


    private class MvpViewNotAttachedException internal constructor() :
        RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


}