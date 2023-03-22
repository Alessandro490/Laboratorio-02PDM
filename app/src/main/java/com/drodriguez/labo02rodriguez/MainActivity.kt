package com.drodriguez.labo02rodriguez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.LineHeightSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var actionCalculate: Button
    private lateinit var bmiTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var infoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind()
        setListener()
    }

    private fun bind () {
        weightEditText = findViewById(R.id.weightParameterEditText)
        heightEditText = findViewById(R.id.heightParameterEditText)
        actionCalculate = findViewById(R.id.action_calculate)
        bmiTextView = findViewById(R.id.bmiTextView)
        resultTextView = findViewById(R.id.statusTextView)
        infoTextView = findViewById(R.id.conditionTextView)
    }

    private fun setListener(){
            actionCalculate.setOnClickListener {
                val weight = weightEditText.text.toString()
                val height = heightEditText.text.toString()

                if (!validateInput(height, weight)){
                    clearTextView()
                    return@setOnClickListener
            }

                val bmi = calculateBmi(weight.toFloat(), height.toFloat())
                val bmiTwoDigits = String.format(".2f", bmi).toFloat()

                clearFocus()
                displayResult(bmiTwoDigits)
            }

    }

    private fun validateInput(height: String?, weight: String?): Boolean {
        when{
            height.isNullOrEmpty()-> {
                Toast.makeText(this, "Height is empty!!", Toast.LENGTH_SHORT).show()
                return false
            }
            weight.isNullOrEmpty()-> {
                Toast.makeText(this, "Height is empty!!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    //W -> kg
    // H -> m
    private fun calculateBmi(weight: Float, height:Float ): Float{
        return  weight /( ( height/100) * (height/100) )
    }

    private fun displayResult(bmi: Float) {
        bmiTextView.text = bmi.toString()
        infoTextView.text = "(normal range is 18.5 -24.9)"

        var informartionResult = ""
        var color = 0

        when{
            bmi < 18.50 -> {
                informartionResult = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                informartionResult = "Healthy"
                color = R.color.normal_weight
            }
            bmi in 25.0..29.99 -> {
                informartionResult = "Overweight"
                color = R.color.over_weight
            }
            bmi > 30.0 -> {
                informartionResult = "Obese"
                color = R.color.obese
            }
        }

        resultTextView.text = informartionResult
        resultTextView.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun clearFocus(){
        weightEditText.clearFocus()
        heightEditText.clearFocus()
    }

    private fun clearTextView(){
        bmiTextView.text = ""
        resultTextView.text = ""
        infoTextView.text = ""
    }
}