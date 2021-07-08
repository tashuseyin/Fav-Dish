package com.example.favdish.extensions

import android.content.ContentResolver
import android.net.Uri

val Float.sizeInKb get() = this / 1024
val Float.sizeInMb get() = sizeInKb / 1024
val Float.sizeInGb get() = sizeInMb / 1024
val Float.sizeInTb get() = sizeInGb / 1024

fun Float.sizeInHumanReadable(): String {
    if ((1 < sizeInTb) && sizeInTb <= 1024) {
        return "${String.format("%.2f", sizeInTb)} Tb"
    } else if ((1 <= sizeInGb) && sizeInGb < 1024) {
        return "${String.format("%.2f", sizeInGb)} Gb"
    } else if ((1 <= sizeInMb) && sizeInMb < 1024) {
        return "${String.format("%.2f", sizeInMb)} Mb"
    } else if ((1 <= sizeInKb) && sizeInKb < 1024) {
        return "${String.format("%.2f", sizeInKb)} kB"
    }
    return "${String.format("%.2f", sizeInMb)} Mb"
}

fun ContentResolver.sizeOfUri(uri:Uri): String{
    return (openInputStream(uri)?.readBytes()?.size ?: 0).toFloat().sizeInHumanReadable()
}