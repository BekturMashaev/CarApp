package com.example.carapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.carapp.R
import com.example.carapp.adapters.CarAdapter
import com.example.carapp.cache.CarCacheManager
import com.example.carapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPref by lazy {
        CarCacheManager(this)
    }
    private val adapter: CarAdapter by lazy {
        CarAdapter(onDeleteNoteClick = { index ->
            sharedPref.deleteNoteByIndex(index)
            saveCars()
        }, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        saveCars()
        setUpClickListener()
    }

    private fun saveCars() {
        val listOfCars = sharedPref.getAllCars()
        if (listOfCars.isNotEmpty()) {
            binding.carRw.visibility = View.VISIBLE
            binding.nothingTxt.visibility = View.GONE
            adapter.updateList(listOfCars)
            binding.carRw.adapter = adapter
        } else {
            binding.carRw.visibility = View.GONE
            binding.nothingTxt.visibility = View.VISIBLE
        }
    }

    private fun setUpClickListener() = binding.apply {
        val listOfCars = sharedPref.getAllCars()
        addFBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddMainActivity::class.java))
        }
        deleteCard.setOnClickListener {
            if (listOfCars.isEmpty()) showToastManager(getString(R.string.nothing_to_delete))
            else showConfirmDialog()
        }
    }

    private fun deleteAllCars() {
        sharedPref.deleteAllCars()
        adapter.updateList(emptyList())
        binding.carRw.visibility = View.GONE
        binding.nothingTxt.visibility = View.VISIBLE
    }


    private fun showConfirmDialog() {
        val alterDialog = MaterialAlertDialogBuilder(this)
        alterDialog.setMessage(getString(R.string.really_want_to_delete))
        alterDialog.setPositiveButton(getString(R.string.confirmation_yes_txt)) { dialog, _ ->
            deleteAllCars()
            dialog.dismiss()
        }
        alterDialog.setNegativeButton(getString(R.string.confirmation_no_txt)) { dialog, _ ->
            dialog.dismiss()
        }
        alterDialog.create().show()
    }

    private fun showToastManager(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}