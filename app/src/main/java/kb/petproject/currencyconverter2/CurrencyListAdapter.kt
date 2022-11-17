package kb.petproject.currencyconverter2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kb.petproject.currencyconverter2.data.Currency
import kb.petproject.currencyconverter2.data.getFormattedAmount
import kb.petproject.currencyconverter2.data.getFormattedRate
import kb.petproject.currencyconverter2.databinding.CurrencyListItemBinding

class CurrencyListAdapter() :
    ListAdapter<Currency, CurrencyListAdapter.CurrencyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            CurrencyListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val current = getItem(position)
//        holder.itemView.setOnClickListener {
//            onCurrencyClicked(current)
//        }
        holder.bind(current)
    }

    class CurrencyViewHolder(private var binding: CurrencyListItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(currency: Currency) {
            binding.apply {
                currencyAlfa3.text = currency.alfa3
                currencyRate.text = currency.getFormattedRate()
                currencyAmount.text = currency.getFormattedAmount()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem.alfa3 == newItem.alfa3
            }
        }
    }
}