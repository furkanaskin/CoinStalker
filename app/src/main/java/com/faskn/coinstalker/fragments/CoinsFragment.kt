package com.faskn.coinstalker.fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.adapters.CoinAdapter
import com.faskn.coinstalker.base.BaseFragment
import com.faskn.coinstalker.model.Coin
import com.faskn.coinstalker.utils.ListPaddingDecoration
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import kotlinx.android.synthetic.main.fragment_coins.*


class CoinsFragment : BaseFragment() {

    private var RECYCLER_ANIMATION_FLAG = 0 

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coins, container, false)
        val actionBar = activity?.actionBar
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_coins.visibility = View.VISIBLE

        val viewModel = ViewModelProviders.of(this).get(CoinsViewModel::class.java) // Create vm
        val coinsRecyclerView =
            view.findViewById<RecyclerView>(R.id.container_coins)
        coinsRecyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        coinsRecyclerView.addItemDecoration(
            ListPaddingDecoration(activity as Activity, 40, 40, 0)
        )
        val itemOnClick: (View, Int) -> Unit = { _, id ->
            val action = CoinsFragmentDirections.actionCoinsFragmentToCoinInfoFragment(id)
            findNavController().navigate(action)
        }

        viewModel.getCoins() // Get data
        viewModel.coinsLiveData.observe(this@CoinsFragment, Observer { Data ->
            // Observe the data
            val adapter = CoinAdapter(Data.coins as ArrayList<Coin>, Data.base, itemOnClick)
            coinsRecyclerView.swapAdapter(adapter, false) // Pass data to adapter
            pb_coins.visibility = View.GONE
            if (RECYCLER_ANIMATION_FLAG == 0) {
                runLayoutAnimation(coinsRecyclerView)
                RECYCLER_ANIMATION_FLAG += 1
            }
        })
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}
