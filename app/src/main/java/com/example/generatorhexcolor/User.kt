package com.example.generatorhexcolor

import android.os.Parcel
import android.os.Parcelable

data class User(
    val nama: String,
    val nim: String,
    val programStudi: String,
    val gender: String,
    val hobi: ArrayList<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        nama= parcel.readString() ?: "",
        nim= parcel.readString() ?: "",
        programStudi= parcel.readString() ?: "",
        gender= parcel.readString() ?: "",
        hobi= parcel.createStringArrayList() ?: arrayListOf()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeString(nim)
        parcel.writeString(programStudi)
        parcel.writeString(gender)
        parcel.writeStringList(hobi)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<User>{
        override fun createFromParcel(parcel: Parcel): User = User(parcel)
        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
}
