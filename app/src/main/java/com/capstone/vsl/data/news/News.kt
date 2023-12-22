package com.capstone.vsl.data.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News (
    var name: String,
    var photo: Int,
    var desc: String
): Parcelable