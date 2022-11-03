package kb.petproject.currencyconverter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import kb.petproject.currencyconverter2.databinding.ActivityMainBinding
import kb.petproject.currencyconverter2.model.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: CurrencyViewModel by viewModels()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.currencyAmountMainEditText.doAfterTextChanged {
            viewModel.setMainCurrencyAmount(it.toString())
        }

        binding.currencyRate1EditText.doAfterTextChanged {
            viewModel.setMainCurrencyRate(1, it.toString())
        }

        binding.currencyRate2EditText.doAfterTextChanged {
            viewModel.setMainCurrencyRate(2, it.toString())
        }

    }

}