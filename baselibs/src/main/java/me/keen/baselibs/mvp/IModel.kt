package me.keen.baselibs.mvp

import io.reactivex.disposables.Disposable

interface IModel {
    fun addDisposable(disposable: Disposable?)

    fun onDetach()
}
