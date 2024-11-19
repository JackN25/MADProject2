package com.example.myapplication

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

lateinit var percentageDisplay: TextView
lateinit var grandTotal: TextView
lateinit var tipAmount:TextView
lateinit var tipStatus: TextView
lateinit var billTotal:TextView
lateinit var seekBar: SeekBar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        percentageDisplay = findViewById(R.id.percentageDisplay)
        grandTotal = findViewById(R.id.grandTotal)
        tipAmount = findViewById(R.id.tipAmount)
        tipStatus = findViewById(R.id.tipStatus)
        billTotal = findViewById(R.id.billTotal)
        seekBar = findViewById(R.id.seekBar)

        seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = seekBar.progress
                percentageDisplay.text = "$progress%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
    }
}