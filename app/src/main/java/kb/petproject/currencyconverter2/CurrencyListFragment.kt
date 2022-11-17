package kb.petproject.currencyconverter2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kb.petproject.currencyconverter2.databinding.CurrencyListFragmentBinding
import kb.petproject.currencyconverter2.model.CurrencyViewModel
import kb.petproject.currencyconverter2.model.CurrencyViewModelFactory

class CurrencyListFragment : Fragment() {

    private var _binding: CurrencyListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurrencyViewModel by activityViewModels{
        CurrencyViewModelFactory(
            (activity?.application as CurrencyApplication).database.currencyDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CurrencyListAdapter()

        binding.recyclerView.adapter = adapter

        viewModel.allCurrencies.observe(this.viewLifecycleOwner) { currencies ->
            currencies.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floatingActionButton.setOnClickListener {
            val action = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyAddFragment(
                getString(R.string.add_new_currency)
            )
            this.findNavController().navigate(action)
        }

        binding.countButton.setOnClickListener {
            viewModel.updateAmounts(binding.currencyAmountMainEditText.text.toString().toDouble())
        }
    }

}