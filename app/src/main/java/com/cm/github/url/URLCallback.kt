package com.cm.github.url

abstract class URLCallback<T> {
    abstract fun onSuccess(`object`: T)

    abstract fun onFailed(code: Int, title: String, message: String)

    abstract fun onFailure(message: String?)
}