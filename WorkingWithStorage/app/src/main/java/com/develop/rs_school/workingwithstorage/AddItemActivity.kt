package com.develop.rs_school.workingwithstorage

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.ActivityAddItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


const val dateInputFormat =  "dd.MM.yyyy"

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddItemBinding
    private val simpleDateFormat = SimpleDateFormat(dateInputFormat, Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.AddItemActivityTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.nameEt.doAfterTextChanged{ binding.nameEtWrapper.error = "" }
        binding.cityEt.doAfterTextChanged{ binding.cityEtWrapper.error = "" }
        binding.dobEt.doAfterTextChanged{ binding.dobEtWrapper.error = "" }

        binding.dobEt.setOnClickListener{
            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]
            DatePickerDialog(
                this,
                OnDateSetListener { _, pickerYear, monthOfYear, dayOfMonth ->
                    calendar.set(pickerYear, monthOfYear, dayOfMonth)
                    binding.dobEt.setText(simpleDateFormat.format(calendar.time))
                },
                year,
                month,
                day
            ).show()
        }


        binding.addButton.setOnClickListener {
            if(validateInput()){
                val intent = Intent()
                intent.putExtra(intentKeyFriend,
                            Friend(name = binding.nameEt.text.toString(),
                                    city = binding.cityEt.text.toString(),
                                    DOB = simpleDateFormat.parse(binding.dobEt.text.toString())!!))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun validateInput() : Boolean{
        if(binding.nameEt.text.toString().isEmpty()){
            binding.nameEtWrapper.error = getString(R.string.ErrorText)
            return false
        }
        if(binding.cityEt.text.toString().isEmpty()){
            binding.cityEtWrapper.error = getString(R.string.ErrorText)
            return false
        }
        try {
            simpleDateFormat.parse(binding.dobEt.text.toString())!!
        }
        catch (p : ParseException){
            binding.dobEtWrapper.error = getString(R.string.ErrorText)
            return false
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}