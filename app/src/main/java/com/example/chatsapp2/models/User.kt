package com.example.chatsapp2.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String,val email:String) : Parcelable {
  constructor() : this("", "", "","")
}