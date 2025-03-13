package com.example.btvn_calculator

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import com.example.btvn_calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewDisplay: TextView
    private var currentInput = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewDisplay = findViewById(R.id.viewDisplay)

        // Gán sự kiện click cho tất cả các nút
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1,R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5,R.id.btn6, R.id.btn7,R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv, R.id.btnEqual,
            R.id.btnC, R.id.btnCE, R.id.btnBS, R.id.btnDot, R.id.btnAddOrSub
        )

        for(id in buttonIds){
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        if (view is Button) {
            when (view.id) {
                in R.id.btn0..R.id.btn9 -> appendNumber(view.text.toString())
                R.id.btnAdd -> setOperation("+")
                R.id.btnSub -> setOperation("-")
                R.id.btnMul -> setOperation("×")
                R.id.btnDiv -> setOperation("÷")
                R.id.btnEqual -> calculateResult()
                R.id.btnCE -> clearAll()
                R.id.btnBS -> removeLastDigit()
            }
        }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        viewDisplay.text = currentInput
    }

    private fun setOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDouble()
            operation = op
            currentInput = ""
            viewDisplay.text = operation
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty()) {
            secondNumber = currentInput.toDouble()
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×" -> firstNumber * secondNumber
                "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else "Error"
                else -> 0
            }
            viewDisplay.text = result.toString()
            currentInput = result.toString()
            firstNumber = 0.0
            secondNumber = 0.0
            operation = ""
        }
    }

    private fun clearAll() {
        currentInput = ""
        firstNumber = 0.0
        secondNumber = 0.0
        operation = ""
        viewDisplay.text = "0"
    }

    private fun removeLastDigit() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            viewDisplay.text = if (currentInput.isEmpty()) "0" else currentInput
        }
    }
}
