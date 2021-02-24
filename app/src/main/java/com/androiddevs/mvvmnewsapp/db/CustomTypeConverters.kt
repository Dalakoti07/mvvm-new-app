package com.androiddevs.mvvmnewsapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.api.responses.Source

class CustomTypeConverters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(string:String):Source{
        return Source(string,string)
    }
}