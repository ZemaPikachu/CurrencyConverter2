package kb.petproject.currencyconverter2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val alfa3: String,
    @ColumnInfo
    val rate: Double,
    @ColumnInfo
    val amount: Double
)

fun Currency.getFormattedRate(): String =
    NumberFormat.getNumberInstance().format(rate)

fun Currency.getFormattedAmount(): String =
    NumberFormat.getNumberInstance().format(amount)