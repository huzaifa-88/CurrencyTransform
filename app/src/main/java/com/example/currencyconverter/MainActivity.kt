package com.example.currencyconverter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner

    private lateinit var edt1: EditText
    private lateinit var edt2: EditText

    var currencies = arrayOf<String?>("PKR", "USD", "EURO", "SAUDI RIYAL", "INR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        edt1 = findViewById(R.id.edt1)
        edt2 = findViewById(R.id.edt2)

//        Edit text when input is change
        edt1.doOnTextChanged { _, _, _, _ ->
            if (edt1.isFocused) {
                val amt = if (edt1.text.isEmpty()) 0.0 else edt1.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner1.selectedItem.toString(),
                    spinner2.selectedItem.toString()
                ).toString()

                edt2.setText(convertedCurrency.toString())
            }
        }

        edt2.doOnTextChanged { _, _, _, _ ->
            if (edt2.isFocused) {
                val amt = if (edt2.text.isEmpty()) 0.0 else edt2.text.toString().toDouble()
                val convertedCurrency = convertCurrency(
                    amt,
                    spinner2.selectedItem.toString(),
                    spinner1.selectedItem.toString()
                ).toString()

                edt1.setText(convertedCurrency.toString())
            }
        }
//        For Spinner 1
        spinner1.onItemSelectedListener = this
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            currencies)

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner1.adapter = ad

//        For Spinner 2
        spinner2.onItemSelectedListener = this
        val ad2: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            currencies)

        // set simple layout resource file
        // for each item of spinner
        ad2.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner2.adapter = ad2
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id){
            R.id.spinner1 -> {
                val amt = if (edt1.text.isEmpty()) 0.0 else (edt1.text.toString().toDouble())
                edt2.setText(
                    convertCurrency(amt, spinner1.selectedItem.toString(), spinner2.selectedItem.toString()).toString()
                )
            }
            R.id.spinner2 -> {
                val amt = if (edt2.text.isEmpty()) 0.0 else (edt2.text.toString().toDouble())
                edt1.setText(
                    convertCurrency(amt, spinner2.selectedItem.toString(), spinner1.selectedItem.toString()).toString()
                )
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun convertCurrency(amt: Double, firstCurrency: String, secondCurrency: String): Double{
        val pkr = convertOtherToPKR(amt, firstCurrency)
        return convertPKRToOther(pkr, secondCurrency)
    }

    private fun convertPKRToOther(pkr: Double, secondCurrency: String): Double {
        return pkr * when(secondCurrency){
            "PKR" -> 1.0
            "USD" -> 0.004
            "EURO" -> 0.003
            "SAUDI RIYAL" -> 0.013
            "INR" -> 0.299
            else -> 0.0
        }
    }

    private fun convertOtherToPKR(amt: Double, firstCurrency: String): Double {
        return amt * when (firstCurrency){
            "PKR" -> 1.0
            "USD" -> 278.7
            "EURO" -> 299.452
            "SAUDI RIYAL" -> 74.292
            "INR" -> 3.341
            else -> 0.0
        }
    }
}