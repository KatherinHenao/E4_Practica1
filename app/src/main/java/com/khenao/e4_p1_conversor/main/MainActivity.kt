package com.khenao.e4_p1_conversor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.khenao.e4_p1_conversor.R
import com.khenao.e4_p1_conversor.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask

import androidx.lifecycle.ViewModelProvider
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainModelView: MainModelView
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainModelView = ViewModelProvider(this)[MainModelView::class.java]

        with(mainBinding) {
            mainModelView.currencyEmptyLiveData.observe(this@MainActivity) { currencyEmpty ->
                if (currencyEmpty) {
                    currency1EditText.setHintTextColor(
                        ContextCompat.getColor
                            (applicationContext, R.color.main_currency_empty)
                    )
                    resultTextView.text = getString(R.string.main_solve_currency_empty)
                    timer.schedule(
                        timerTask {
                            currency1EditText.setHintTextColor(
                                ContextCompat.getColor
                                    (applicationContext, R.color.main_currency_hint)
                            )
                            resultTextView.text = ""
                        },
                        1000
                    )
                }
            }

            mainModelView.proceedLiveData.observe(this@MainActivity) {
                    proceed ->
                if(proceed){
                    val choiceCurrency1 = choice1Spinner.selectedItem.toString()
                    val choiceCurrency2 = choice2Spinner.selectedItem.toString()
                    mainModelView.process(choiceCurrency1, choiceCurrency2)
                }
            }

            mainModelView.resultLiveData.observe(this@MainActivity) {
                    result->
                resultTextView.text = result
            }

            solveButton.setOnClickListener {
                val currency1 = currency1EditText.text.toString()
                mainModelView.validate(currency1)
            }
        }
    }
}