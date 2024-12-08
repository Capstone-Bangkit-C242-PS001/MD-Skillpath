package lat.elmidraft.skillpath.ui.recommendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import lat.elmidraft.skillpath.adapter.PredictionAdapter
import lat.elmidraft.skillpath.data.model.predict.PredictResponseData
import lat.elmidraft.skillpath.databinding.ActivityRecommendationBinding
import lat.elmidraft.skillpath.profile.ProfileActivity
import lat.elmidraft.skillpath.ui.recommendationdetail.RecommendationDetailActivity
import lat.elmidraft.skillpath.utils.LoadingDialogUtils
import lat.elmidraft.skillpath.utils.SharedPrefUtils

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding
    private val viewModel : RecommendationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = intent.getStringExtra("query")!!
        viewModel.loadRecommendation(SharedPrefUtils.getUser(this).token, query)

        val adapter = PredictionAdapter(object : PredictionAdapter.PredictionAdapterListener{
            override fun onCLick(prediction: PredictResponseData) {
                startActivity(
                    Intent(
                        this@RecommendationActivity,
                        RecommendationDetailActivity::class.java
                    ).putExtra("id",prediction.id).putExtra("query",query)
                )
            }
        })

        with(binding){
            backButton.setOnClickListener{
                finish()
            }
            profileImage.setOnClickListener{
                startActivity(
                    Intent(
                        this@RecommendationActivity,
                        ProfileActivity::class.java
                    )
                )
            }
            rvRecomedation.layoutManager = LinearLayoutManager(this@RecommendationActivity)
            rvRecomedation.adapter = adapter
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
            adapter.setList(listRecommendation)
        }

        viewModel.errorMessage.observe(this){ errorMessage->
            val dialog = AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
    }
}