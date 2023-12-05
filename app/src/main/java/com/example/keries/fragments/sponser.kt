import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.keries.R
import com.example.keries.adapter.SponsorAdapter
import com.example.keries.dataClass.sponserDataClass
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class sponser : Fragment() {

    private lateinit var sponsorRecyclerView: RecyclerView
    private lateinit var sponseradapter: SponsorAdapter
    private var SponserList: MutableList<sponserDataClass> = mutableListOf()
    private lateinit var toolText: TextView
    private lateinit var logoTool: ImageView
    private lateinit var notifyTool: ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sponser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swiperefreshs)
        sponsorRecyclerView = view.findViewById(R.id.sponserRecylerView)
        sponseradapter = SponsorAdapter(SponserList)
        sponsorRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        sponsorRecyclerView.adapter = sponseradapter
        sponsorRecyclerView.layoutManager = com.jackandphantom.carouselrecyclerview.CarouselLayoutManager(
            true,
            true,
            0.5F,
            true,
            true,
            true,
            LinearLayoutManager.VERTICAL
        )
        (sponsorRecyclerView as CarouselRecyclerview).setInfinite(true)

        swipeRefreshLayout.setOnRefreshListener {
            fetchFirestoreData()
        }

        fetchFirestoreData()
    }

    private fun fetchFirestoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("sponsors")
            .get()
            .addOnSuccessListener { documents ->
                // Clear existing data
                SponserList.clear()

                for (document in documents) {
                    val name = document.getString("name") ?: ""
                    val url = document.getString("url") ?: ""
                    val desgination = document.getString("title") ?: ""
                    val item = sponserDataClass(name, desgination, url)
                    SponserList.add(item)
                }

                // Notify the adapter that the data has changed
                sponseradapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
    }
}
