package kb.petproject.currencyconverter2

import android.app.Application
import kb.petproject.currencyconverter2.data.CurrencyRoomDatabase

class CurrencyApplication : Application() {
    val database: CurrencyRoomDatabase by lazy { CurrencyRoomDatabase.getDatabase(this) }
}