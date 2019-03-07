package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.activities.MainActivity
import com.faskn.coinstalker.adapters.CommentsAdapter
import com.faskn.coinstalker.model.CommentsDTO
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.container_commentbox.*
import kotlinx.android.synthetic.main.fragment_comments.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsFragment : Fragment() {

    val viewModel : CoinsViewModel by sharedViewModel()

    var databaseReference: DatabaseReference? = null

    private val coinSymbol by lazy { arguments?.getSerializable("coinSymbol") as String }

    companion object {
        fun newInstance(coinSymbol: String): Fragment {
            val commentsFragment = CommentsFragment()
            val bundle = Bundle()
            bundle.putSerializable("coinSymbol", coinSymbol)
            commentsFragment.arguments = bundle
            return commentsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        return view
    }


    override fun onResume() {
        super.onResume()
        initFirebase()
        createFirebaseListener()
        setupSendButton()

    }

    /**
     * Setup firebase
     */

    private fun initFirebase() {
        //init firebase

        FirebaseApp.initializeApp((activity as MainActivity).applicationContext)
        FirebaseApp.getInstance()

        //get reference to our db
        databaseReference = FirebaseDatabase.getInstance().reference

    }

    /**
     * Set listener for Firebase
     */

    private fun createFirebaseListener() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val toReturn: ArrayList<CommentsDTO> = ArrayList()

                for (data in dataSnapshot.children) {
                    val commentData = data.getValue<CommentsDTO>(CommentsDTO::class.java)

                    //unwrap
                    val comment = commentData?.let { it } ?: continue

                    toReturn.add(comment)
                }

                //sort so newest at bottom
                toReturn.sortBy { comment ->
                    comment.timestamp
                }
                checkCommentData(toReturn)
                recycler_comments.adapter?.notifyDataSetChanged()
                setupAdapter(toReturn)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //log error
            }
        }
        databaseReference?.child("comments-$coinSymbol")
            ?.addValueEventListener(postListener)
    }

    /**
     * Once data is here - display it
     */


    private fun setupAdapter(data: ArrayList<CommentsDTO>) {

        val recyclerComments = view?.findViewById<RecyclerView>(R.id.recycler_comments)
        recyclerComments!!.layoutManager =
                LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        val commentsAdapter = CommentsAdapter(data)
        recyclerComments.adapter = commentsAdapter

        recycler_comments?.adapter?.notifyDataSetChanged()

        //scroll to bottom
        recycler_comments?.scrollToPosition(data.size - 1)
    }

    /**
     * OnClick action for the send button
     */

    private fun setupSendButton() {
        btn_Send.setOnClickListener {
            if (!edt_comment.text.toString().isEmpty()) {
                sendData()
            } else {
                Toast.makeText(this.context, getString(R.string.mesajGonder), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    /**
     * Send data to firebase
     */

    private fun sendData() {
        databaseReference?.child("comments-$coinSymbol")
            ?.child(java.lang.String.valueOf(System.currentTimeMillis()))
            ?.setValue(CommentsDTO(edt_comment.text.toString()))
        //clear the text
        edt_comment.setText("")
        edt_comment.hint = getString(R.string.mesajGonder)
    }

    private fun checkCommentData(commentData: ArrayList<CommentsDTO>) {
        try {
            if (commentData.isEmpty()) {
                tv_noComment.visibility = View.VISIBLE
            } else {
                tv_noComment.visibility = View.GONE
            }
        } catch (error: IllegalStateException) {
            Log.v("Comment Data", "Garip bir şekilde boş geldi?")
        }

    }
}