package com.wework.zombiedev.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repo")
class TrendingRepoEntity(@PrimaryKey(autoGenerate = true)
                 val id: Int,
                 val author : String?,
                 val name : String?,
                 val avatar : String?,
                 val url : String?,
                 val description : String?,
                 val language : String?,
                 val languageColor : String?,
                 val stars: Int,
                 val forks: Int
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(author)
        writeString(name)
        writeString(avatar)
        writeString(url)
        writeString(description)
        writeString(language)
        writeString(languageColor)
        writeInt(stars)
        writeInt(forks)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TrendingRepoEntity> =
            object : Parcelable.Creator<TrendingRepoEntity> {
                override fun createFromParcel(source: Parcel): TrendingRepoEntity =
                    TrendingRepoEntity(source)

                override fun newArray(size: Int): Array<TrendingRepoEntity?> = arrayOfNulls(size)
            }
    }
}