package lat.elmidraft.skillpath.ui.recommendationdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import lat.elmidraft.skillpath.adapter.PredictionAdapter
import lat.elmidraft.skillpath.data.model.predict.PredictResponseData
import lat.elmidraft.skillpath.databinding.ActivityDetailBinding
import lat.elmidraft.skillpath.ui.rating.RatingActivity
import lat.elmidraft.skillpath.ui.recommendation.RecommendationViewModel
import lat.elmidraft.skillpath.utils.LoadingDialogUtils
import lat.elmidraft.skillpath.utils.SharedPrefUtils

class RecommendationDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel : RecommendationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id",-1)
        val query = intent.getStringExtra("query")!!
        viewModel.loadRecommendation(SharedPrefUtils.getUser(this).token, query)

        val adapter = PredictionAdapter(object : PredictionAdapter.PredictionAdapterListener{
            override fun onCLick(prediction: PredictResponseData) {
                startActivity(
                    Intent(
                        this@RecommendationDetailActivity,
                        RecommendationDetailActivity::class.java
                    ).putExtra("id",prediction.id).putExtra("query",query)
                )
            }
        })

        var url = ""

        with(binding){
            recyclerView.layoutManager = LinearLayoutManager(this@RecommendationDetailActivity)
            recyclerView.adapter = adapter
            backButton.setOnClickListener{
                finish()
            }
            goToCourseButton.setOnClickListener{
                if(url.isNotEmpty()){
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    startActivity(intent)
                }
            }
            giveRatingButton.setOnClickListener{
                startActivity(
                    Intent(
                        this@RecommendationDetailActivity,
                        RatingActivity::class.java
                    ).putExtra("id",id)
                )
            }
        }

        val loadingDialog = LoadingDialogUtils(this)
        viewModel.isLoading.observe(this){ isLoading->
            if(isLoading){
                loadingDialog.show()
            }else{
                loadingDialog.dismiss()
            }
        }

        viewModel.listRecommendation.observe(this){ listRecommendation->
            val selected = mutableListOf<PredictResponseData>()
            for(rec in listRecommendation){
                if(rec.id==id){
                    with(binding){
                        titleText.text = rec.title
                        descriptionText.text = rec.description
                        url = rec.provider_url
                    }
                }else{
                    selected.add(rec)
                }
            }
            adapter.setList(listRecommendation)
        }

        viewModel.errorMessage.observe(this){ errorMessage->
            val dialog = AlertDialog.Builder(this)
                .setTitle("Pesan")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
    }
}