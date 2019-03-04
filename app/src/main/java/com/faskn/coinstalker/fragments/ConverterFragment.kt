package com.faskn.coinstalker.fragments


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseFragment
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_converter.*


class ConverterFragment : BaseFragment() {

    private val coinNames by lazy { ArrayList<String>() }
    private val coinImageUrls by lazy { ArrayList<String>() }
    private var COIN_SPINNER_FLAG = 999
    private var firstCoinIndex: Int = 0
    private var secondCoinIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_converter, container, false)

        setHasOptionsMenu(true)

        viewModel.getCoins(getBase(), "price", "30d")
        viewModel.coinsLiveData.observe(this@ConverterFragment, Observer { Data ->
            if (COIN_SPINNER_FLAG != 0) {
                Data.coins.forEachIndexed { index, element ->
                    coinNames.add(element.symbol)
                    coinImageUrls.add(element.iconUrl)
                    COIN_SPINNER_FLAG = 0
                }

                val firstAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, coinNames)
                firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner_firstCoin.adapter = firstAdapter

                val secondAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, coinNames)
                secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner_secondCoin.adapter = secondAdapter

            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_converter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks(spinner_firstCoin, til_firstCoin)
        setOnClicks(spinner_secondCoin, til_secondCoin)

        val myWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_firstCoin.text.toString().isEmpty()) {
                    viewModel.getCoinInfo(
                        firstCoinIndex,
                        spinner_secondCoin.adapter.getItem(secondCoinIndex).toString()
                    )
                    viewModel.coinInfoLiveData.observe(this@ConverterFragment, Observer { Data ->
                        try {
                            val multiplier = edt_firstCoin.text.toString().toBigDecimal()
                            edt_secondCoin.setText((Data.coin.price * multiplier).toString())
                        } catch (e: Exception) {
                            Log.v("ConverterFragment", "Edittext i/o problem. : $e")
                        }

                    })
                }
            }
        }

        edt_firstCoin.addTextChangedListener(myWatcher)

    }

    private fun setOnClicks(spinner: Spinner, textView: TextInputLayout) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = spinner.adapter.getItem(position)
                textView.hint = item.toString()

                if (spinner == spinner_firstCoin && view != null) {

                    firstCoinIndex = position
                    val firstCoinURI: Uri = Uri.parse(coinImageUrls[position])
                    GlideToVectorYou.justLoadImage(
                        view.context as Activity,
                        firstCoinURI,
                        iv_converter_firstCoinLogo
                    )
                }

                if (spinner == spinner_secondCoin && view != null) {

                    secondCoinIndex = position
                    val secondCoinURI: Uri = Uri.parse(coinImageUrls[position])
                    GlideToVectorYou.justLoadImage(
                        view.context as Activity,
                        secondCoinURI,
                        iv_converter_secondCoinLogo
                    )
                }
            }
        }
    }
}

