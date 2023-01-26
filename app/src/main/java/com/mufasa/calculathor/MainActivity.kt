package com.mufasa.calculathor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    var lastDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDecimal = false
    }

    fun onCLear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDecimal){
            tvInput?.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDecimal = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() - second.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() + second.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() * second.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZeroAfterDot((first.toDouble() / second.toDouble()).toString())
                }

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+")
                    || value.contains("*")
                    || value.contains("/")
                    || value.contains("-")
        }
    }

}
