package kb.petproject.currencyconverter2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class CurrencyViewModel: ViewModel() {

    private val _currencyAmountMain = MutableLiveData<Double>()
    val currencyAmountMain: LiveData<Double> = _currencyAmountMain

    private val _currencyAmount1 = MutableLiveData<Double>()
    val currencyAmount1: LiveData<Double> = _currencyAmount1

    private val _currencyAmount2 = MutableLiveData<Double>()
    val currencyAmount2: LiveData<Double> = _currencyAmount2

    private val _currencyRate1 = MutableLiveData<Double>()
    val currencyRate1: LiveData<Double> = _currencyRate1

    private val _currencyRate2 = MutableLiveData<Double>()
    val currencyRate2: LiveData<Double> = _currencyRate2

    init {
        resetValues(false)
    }

    private fun count() {

        if (currencyAmountMain.value == null || currencyAmountMain.value == 0.0) {
            resetValues(true)
        }

        currencyRate1.value?.let {
            _currencyAmount1.value = roundNumber(currencyAmountMain.value!!.div(it))
        }

        currencyRate2.value?.let {
            _currencyAmount2.value = roundNumber(currencyAmountMain.value!!.div(it))
        }
    }

    private fun roundNumber(number: Double): Double {
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.HALF_UP
        return decimalFormat.format(number).toDouble()
    }

    // replace with 2 functions: resetAmounts() and resetRates()
    private fun resetValues(amountsOnly: Boolean) {
        _currencyAmountMain.value = 0.0
        _currencyAmount1.value = 0.0
        _currencyAmount2.value = 0.0

        if (!amountsOnly) {
            _currencyRate1.value = 1.0
            _currencyRate2.value = 1.0
        }
    }

    fun setMainCurrencyAmount(amount: String) {
        _currencyAmountMain.value = convertToDouble(amount)
        count()
    }

    fun setMainCurrencyRate(id: Int, rate: String) {
        var newRate = convertToDouble(rate)
        if (newRate == 0.0) newRate = 1.0
        when (id) {
            1 -> _currencyRate1.value = newRate
            2 -> _currencyRate2.value = newRate
        }
        count()
    }

    private fun convertToDouble(number: String): Double {
        return if (number.isEmpty()) {
            0.0
        } else {
            number.toDouble()
        }
    }
}
