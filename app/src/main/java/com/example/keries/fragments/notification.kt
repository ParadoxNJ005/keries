package com.example.keries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keries.R
import com.example.keries.adapter.NotificationAdapter
import com.example.keries.dataClass.NotificationModel
import com.example.keries.databinding.FragmentNotificationBinding
import com.google.firebase.firestore.FirebaseFirestore

class notification : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter
    private var NotificatonList: MutableList<NotificationModel> = mutableListOf()
    private var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.takmeBacknotfiy.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        notificationAdapter = NotificationAdapter(NotificatonList)
        binding.notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }
        binding.notificationRecyclerView.adapter = notificationAdapter
        fetchFirestoreData()
    }

    private fun fetchFirestoreData() {
        db.collection("Notification").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val info = document.getString("info") ?: ""
//                        val image = document.getString("c")?:""
//                        val time = document.getString("time")?:""
                val item = NotificationModel(info)
                NotificatonList.add(item)
            }
            notificationAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}