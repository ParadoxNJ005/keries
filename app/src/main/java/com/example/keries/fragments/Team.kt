package com.example.keries.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.keries.R
import com.example.keries.adapter.TeamAdapter
import com.example.keries.dataClass.TeamMember
import com.example.keries.databinding.FragmentTeamBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Team : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var teamAdapter: TeamAdapter
    private var loadedRecyclerViewCount: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Fresco.initialize(requireContext())

        binding.festiveCordi.layoutManager = LinearLayoutManager(context)
        binding.financerelcyler.layoutManager = LinearLayoutManager(context)
        binding.eventesmangereycl.layoutManager = LinearLayoutManager(context)
        binding.coroprate.layoutManager = LinearLayoutManager(context)
        binding.apppp.layoutManager = LinearLayoutManager(context)
        binding.tecnialreycler.layoutManager = LinearLayoutManager(context)
        binding.pr.layoutManager = LinearLayoutManager(context)
        binding.mediaaaa.layoutManager = LinearLayoutManager(context)
        binding.wed.layoutManager = LinearLayoutManager(context)
        binding.cco.layoutManager = LinearLayoutManager(context)
        binding.flimingdevven.layoutManager = LinearLayoutManager(context)
        binding.brnding.layoutManager = LinearLayoutManager(context)
        binding.creativesreyc.layoutManager = LinearLayoutManager(context)
        binding.hospitalllty.layoutManager = LinearLayoutManager(context)

        fetchAndPopulateData("Coordinator", binding.festiveCordi)
        fetchAndPopulateData("FINANCE", binding.financerelcyler)
        fetchAndPopulateData("EVENTS & MANAGEMENT", binding.eventesmangereycl)
        fetchAndPopulateData("CORPORATE RELATIONS", binding.coroprate)
        fetchAndPopulateData("APP OPERATIONS", binding.apppp)
        fetchAndPopulateData("TECHNICAL", binding.tecnialreycler)
        fetchAndPopulateData("PUBLIC RELATIONS", binding.pr)
        fetchAndPopulateData("MEDIA", binding.mediaaaa)
        fetchAndPopulateData("WEB OPERATIONS", binding.wed)
        fetchAndPopulateData("OOC", binding.cco)
        fetchAndPopulateData("FILMING", binding.flimingdevven)
        fetchAndPopulateData("BRANDING & LOGISTICS", binding.brnding)
        fetchAndPopulateData("CREATIVES", binding.creativesreyc)
        fetchAndPopulateData("HOSPITALITY & TRAVEL", binding.hospitalllty)
        binding.swiperefreshteam.setOnRefreshListener {
            binding.swiperefreshteam.isRefreshing = false
        }
    }

    private fun fetchAndPopulateData(wing: String, recyclerView: RecyclerView) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val teamMemberList = fetchDataFromFirestore(wing)
                val teamAdapter = TeamAdapter(teamMemberList)
                recyclerView.adapter = teamAdapter
                loadedRecyclerViewCount++
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchDataFromFirestore(wing: String): List<TeamMember> = withContext(
        Dispatchers.IO
    ) {
        try {
            val querySnapshot = db.collection("team").whereEqualTo("wing", wing).get().await()

            val teamMemberList = mutableListOf<TeamMember>()
            for (document in querySnapshot) {
                val noString = document.get("no")?.toString() ?: ""
                val no = noString.toIntOrNull() ?: 0
                val name = document.getString("name") ?: ""
                val url = document.getString("url") ?: ""
                teamMemberList.add(TeamMember(name, wing, url, no))
            }
            return@withContext teamMemberList
        } catch (e: Exception) {
            throw e
        }
    }
}