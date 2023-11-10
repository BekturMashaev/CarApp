package com.example.carapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carapp.R
import com.example.carapp.cache.CarCacheManager
import com.example.carapp.databinding.AddMainBinding
import com.example.carapp.models.CarModel
import com.google.android.material.snackbar.Snackbar

class AddMainActivity : AppCompatActivity() {
    private val binding: AddMainBinding by lazy {
        AddMainBinding.inflate(layoutInflater)
    }
    private val sharedPref by lazy {
        CarCacheManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addBtn.setOnClickListener {
            addCar()
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun addCar() = binding.apply {
        if (carNAme.text?.isNotEmpty() == true && carDescTxt.text?.isNotEmpty() == true && urlOfCarPicture.text?.isNotEmpty() == true) {
            sharedPref.saveCar(
                CarModel(
                    carName = binding.carNAme.text.toString(),
                    carDescription = binding.carDescTxt.text.toString(),
                    carURL = binding.urlOfCarPicture.text.toString()
                )
            )
            startActivity(Intent(this@AddMainActivity, MainActivity::class.java))
        } else showToastManager(getString(R.string.complete_field))
    }

    private fun showToastManager(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}