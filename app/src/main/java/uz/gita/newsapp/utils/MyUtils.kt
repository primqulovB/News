package uz.gita.newsapp.utils

import timber.log.Timber

fun showLog(message: String) {
    Timber.tag("TTT").d(message)
}