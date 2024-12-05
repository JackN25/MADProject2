package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import org.w3c.dom.Text




class MainActivity : AppCompatActivity() {

    lateinit var percentageDisplay: TextView
    lateinit var grandTotal: TextView
    lateinit var tipAmount:TextView
    lateinit var tipStatus: TextView
    lateinit var billTotal:TextView
    lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        percentageDisplay = findViewById(R.id.percentageDisplay)
        grandTotal = findViewById(R.id.grandTotal)
        tipAmount = findViewById(R.id.tipAmount)
        tipStatus = findViewById(R.id.tipStatus)
        billTotal = findViewById(R.id.billTotal)
        seekBar = findViewById(R.id.seekBar)

        if (savedInstanceState != null) {
            percentageDisplay.text = savedInstanceState.getString("percentageDisplay")
            grandTotal.text = savedInstanceState.getString("grandTotal")
            tipAmount.text = savedInstanceState.getString("tipAmount")
            tipStatus.text = savedInstanceState.getString("tipStatus")
            billTotal.text = savedInstanceState.getString("billTotal")
            seekBar.progress = savedInstanceState.getInt("seekBar")
            changeTipStatus(seekBar.progress)
        }



        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        seekBar.progress = prefs.getInt("tipPercent", 20)

        percentageDisplay.text = "${seekBar.progress}%"
        changeTipStatus(seekBar.progress)


        seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = seekBar.progress
                percentageDisplay.text = "$progress%"
                changeTipStatus(seekBar.progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                val progress = seekBar.progress
                percentageDisplay.text = "$progress%"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val progress = seekBar.progress
                percentageDisplay.text = "$progress%"
                calculateBills()
            }

        })

        billTotal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                calculateBills()
            }

        })
    }

    override fun onStop() {
        super.onStop()
        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        prefEditor.putInt("tipPercent", seekBar.progress)
        prefEditor.apply()
    }

    fun changeTipStatus(percent: Int) {
        when (percent) {
            in(0..9) -> {tipStatus.text = "Poor"
                tipStatus.setTextColor(Color.RED)}
            in(10..15) -> {tipStatus.text = "OK"
                tipStatus.setTextColor(Color.parseColor("#FFA500"))}
            in (16..20) -> {tipStatus.text = "Good"
                tipStatus.setTextColor(Color.parseColor("#90EE90"))}
            in(21..25) -> {tipStatus.text = "Great"
                tipStatus.setTextColor(Color.GREEN)}
            in(26..35) -> {tipStatus.text = "Awesome"
                tipStatus.setTextColor(Color.CYAN)}
        }
    }

    fun isPrecise(num: Double): Boolean{
        return num.toString().split(".")[1].length <= 2
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("percentageDisplay", percentageDisplay.text.toString())
        outState.putString("grandTotal", grandTotal.text.toString())
        outState.putString("tipAmount", tipAmount.text.toString())
        outState.putString("tipStatus", tipStatus.text.toString())
        outState.putString("billTotal", billTotal.text.toString())
        outState.putInt("seekBar", seekBar.progress)
        outState.putString("tipStatusColor", tipStatus.textColors.toString())
    }

    fun calculateBills() {
        if (billTotal.text.isNotEmpty() && isPrecise(billTotal.text.toString().toDouble())){
            tipAmount.text = String.format("%.2f", billTotal.text.toString().toDouble() * (seekBar.progress.toDouble()/100))
            grandTotal.text = "$" + String.format("%.2f", tipAmount.text.toString().toDouble() + billTotal.text.toString().toDouble())
            tipAmount.text = "$" + tipAmount.text
        } else{
            tipAmount.text = ""
            grandTotal.text = ""
        }
    }
}