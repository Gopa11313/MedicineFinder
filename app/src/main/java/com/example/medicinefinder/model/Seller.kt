package com.example.medicinefinder.model

import android.os.Parcel
import android.os.Parcelable

data class Seller (
    var _id:String?=null,
    var name:String?=null,
    var email:String?=null,
    var password:String?=null,
    var image:String?=null,
    var role:String?=null,
    var latitude:String?=null,
    var longitude:String?=null,
    var storename:String?=null
        ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            return Seller(parcel)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }
}