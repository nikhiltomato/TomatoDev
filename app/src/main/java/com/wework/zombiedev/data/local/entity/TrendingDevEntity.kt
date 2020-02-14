package com.wework.zombiedev.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_dev")
class TrendingDevEntity(@PrimaryKey(autoGenerate = true)
                 val id: Int,
                        val username : String?,
                        val name : String?,
                        val url : String?,
                        val avatar : String?
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(username)
        writeString(name)
        writeString(url)
        writeString(avatar)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TrendingDevEntity> =
            object : Parcelable.Creator<TrendingDevEntity> {
                override fun createFromParcel(source: Parcel): TrendingDevEntity =
                    TrendingDevEntity(source)

                override fun newArray(size: Int): Array<TrendingDevEntity?> = arrayOfNulls(size)
            }
    }
}