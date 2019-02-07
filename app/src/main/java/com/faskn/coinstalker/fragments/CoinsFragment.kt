package com.faskn.coinstalker.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.adapters.CoinAdapter
import com.faskn.coinstalker.base.BaseFragment
import com.faskn.coinstalker.model.Coin
import com.faskn.coinstalker.utils.FilterDialogHelper
import com.faskn.coinstalker.utils.ListPaddingDecoration
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import kotlinx.android.synthetic.main.fragment_coins.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CoinsFragment : BaseFragment() {

    private var RECYCLER_ANIMATION_FLAG = 0
    private var filterDialog: AlertDialog? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CoinsViewModel::class.java)
    } // Create vm.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coins, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_coins_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filtrele -> {
            Toast.makeText(this.context, "Filtrele", Toast.LENGTH_LONG).show()
            showDialog()
            true
        }

        R.id.action_info -> {
            Toast.makeText(this.context, "Bilgilendirme metni", Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_coins.visibility = View.VISIBLE


        val coinsRecyclerView =
            view.findViewById<RecyclerView>(R.id.container_coins)
        coinsRecyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        coinsRecyclerView.addItemDecoration(ListPaddingDecoration(activity as Activity, 40, 40, 0))
        val itemOnClick: (View, Int) -> Unit = { _, id ->
            val action =
                CoinsFragmentDirections.actionCoinsFragmentToCoinInfoFragment(id) // Transfer id to CoinInfo Fragment.
            findNavController().navigate(action)
        }

        viewModel.getCoins(getBase(), getSort(), getTimePeriod()) // Get data
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

    @SuppressLint("CommitPrefEdits")
    private fun showDialog() {
        if (filterDialog == null) {
            filterDialog = showFilterDialog {
                cancelable = false

                radioGroupClickListener()
                retrieveChoices()
                exitButtonClickListener {
                    navigate(R.id.action_coinsFragment_pop)
                }
            }
            //  and showing
            filterDialog?.show()
        }
    }

    private inline fun Fragment.showFilterDialog(func: FilterDialogHelper.() -> Unit): AlertDialog =
        FilterDialogHelper(this.context!!).apply {
            func()
        }.create()

}
