package kb.petproject.currencyconverter2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("SELECT * FROM currency WHERE id = :id")
    fun getCurrency(id: Int): Flow<Currency>

    @Query("SELECT * FROM currency ORDER BY alfa3 ASC")
    fun getCurrencies(): Flow<List<Currency>>

}