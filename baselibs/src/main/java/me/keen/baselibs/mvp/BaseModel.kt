package me.keen.baselibs.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author xiekeen
 * @date 2019-09-24
 * @desc BaseModel
 */
abstract class BaseModel : IModel, LifecycleObserver {
    private var mCompositeDisposable: CompositeDisposable? = null

    override fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) mCompositeDisposable = CompositeDisposable()
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    override fun onDetach() {
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}