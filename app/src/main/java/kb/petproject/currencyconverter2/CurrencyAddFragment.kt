package kb.petproject.currencyconverter2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kb.petproject.currencyconverter2.data.Currency
import kb.petproject.currencyconverter2.databinding.CurrencyAddFragmentBinding
import kb.petproject.currencyconverter2.model.CurrencyViewModel
import kb.petproject.currencyconverter2.model.CurrencyViewModelFactory

class CurrencyAddFragment : Fragment() {

    private val viewModel: CurrencyViewModel by activityViewModels {
        CurrencyViewModelFactory(
            (activity?.application as CurrencyApplication).database.currencyDao()
        )
    }

    lateinit var currency: Currency

    private val navigationArgs: CurrencyAddFragmentArgs by navArgs()

    private var _binding: CurrencyAddFragmentBinding? = null
    private val binding get() = _binding!!

    private fun bind(currency: Currency) {
        val rate = "%.4f".format(currency.rate)
        binding.apply {
            currencyAlfa3.setText(currency.alfa3, TextView.BufferType.SPANNABLE)
            currencyRate.setText(rate, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateCurrency() }
        }
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewCurrency(
                this.binding.currencyAlfa3.text.toString(),
                this.binding.currencyRate.text.toString()
            )
        }
        val action = CurrencyAddFragmentDirections.actionCurrencyAddFragmentToCurrencyListFragment()
        findNavController().navigate(action)
    }

    private fun updateCurrency() {
        if (isEntryValid()) {
            viewModel.updateCurrency(
                this.navigationArgs.currencyId,
                this.binding.currencyAlfa3.text.toString(),
                this.binding.currencyRate.text.toString()
            )
            val action = CurrencyAddFragmentDirections.actionCurrencyAddFragmentToCurrencyListFragment()
            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.currencyAlfa3.text.toString(),
            binding.currencyRate.text.toString()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyAddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.currencyId
        if (id > 0) {
            viewModel.retrieveCurrency(id).observe(this.viewLifecycleOwner) { selectedItem ->
                currency = selectedItem
                bind(currency)
            }
            binding.saveAction.setOnClickListener {
                updateCurrency()
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
        }
    }
}