package com.example.carapp.cache

import android.content.Context
import com.example.carapp.models.CarModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
const val SHARED_PREF = "SHARED_PREF"

class CarCacheManager(
    private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE
    )

    fun getAllCars(): List<CarModel> {
        val gson = Gson()
        val json = sharedPreferences.getString(SHARED_PREF, null)
        val type: Type = object : TypeToken<ArrayList<CarModel?>?>() {}.type
        val carList = gson.fromJson<List<CarModel>>(json, type)
        return carList ?: emptyList()
    }

    fun saveCar(carModel: CarModel) {
        val cars = getAllCars().toMutableList()
        cars.add(0, carModel)
        val carsGson = Gson().toJson(cars)
        sharedPreferences.edit().putString(SHARED_PREF, carsGson).apply()
    }

    fun deleteAllCars() {
        sharedPreferences.edit().clear().apply()
    }
    fun deleteNoteByIndex(index: Int) {
        val getAllCars = getAllCars().toMutableList()
        if (index in 0 until getAllCars.size) {
            getAllCars.removeAt(index)
            val carsGsonString = Gson().toJson(getAllCars)
            sharedPreferences.edit().putString(SHARED_PREF, carsGsonString).apply()
        }
    }
}