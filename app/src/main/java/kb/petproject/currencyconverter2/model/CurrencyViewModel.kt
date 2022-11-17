package kb.petproject.currencyconverter2.model

import androidx.lifecycle.*
import kb.petproject.currencyconverter2.data.Currency
import kb.petproject.currencyconverter2.data.CurrencyDao
import kotlinx.coroutines.launch

class CurrencyViewModel(private val currencyDao: CurrencyDao): ViewModel() {

    private val _mainAmount = MutableLiveData<Double>()
    val mainAmount: LiveData<Double> = _mainAmount

    val allCurrencies: LiveData<List<Currency>> = currencyDao.getCurrencies().asLiveData()

    init {
        _mainAmount.value = 4444444.0
    }

    fun retrieveCurrency(id: Int): LiveData<Currency> {
        return currencyDao.getCurrency(id).asLiveData()
    }

    private fun insertCurrency(currency: Currency) {
        viewModelScope.launch {
            currencyDao.insert(currency)
        }
    }

    private fun updateCurrency(currency: Currency) {
        viewModelScope.launch {
            currencyDao.update(currency)
        }
    }

    fun updateCurrency(
        currencyId: Int,
        currencyAlfa3: String,
        currencyRate: String
    ) {
        val updatedCurrency =
            getUpdatedCurrencyEntry(currencyId, currencyAlfa3, currencyRate)
        updateCurrency(updatedCurrency)
    }

    private fun getNewCurrencyEntry(alfa3: String, rate: Double): Currency {
        return Currency(
            alfa3 = alfa3,
            rate = rate,
            amount = mainAmount.value?.div(rate) ?: 0.0
        )
    }

    private fun getUpdatedCurrencyEntry(
        id: Int,
        alfa3: String,
        rate: String
    ): Currency {
        return Currency(
            id = id,
            alfa3 = alfa3,
            rate = rate.toDouble(),
            amount = mainAmount.value?.div(rate.toDouble()) ?: 0.0
        )
    }

    fun isEntryValid(currencyAlfa3: String, currencyRate: String): Boolean {
        if (currencyAlfa3.isBlank() || currencyRate.isBlank()) {
            return false
        }
        return true
    }

    fun addNewCurrency(alfa3: String, rate: String) {
        val newCurrency = getNewCurrencyEntry(alfa3, rate.toDouble())
        insertCurrency(newCurrency)
    }

    fun updateAmounts(amount: Double) {
        _mainAmount.value = amount
        mainAmount.value?.let { newAmount ->
            viewModelScope.launch {
                currencyDao.updateAmounts(newAmount)
            }
        }
    }
}

class CurrencyViewModelFactory(private val currencyDao: CurrencyDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(currencyDao) as T
        }
        throw  IllegalAccessException("Unknown ViewModel class")
    }
}
