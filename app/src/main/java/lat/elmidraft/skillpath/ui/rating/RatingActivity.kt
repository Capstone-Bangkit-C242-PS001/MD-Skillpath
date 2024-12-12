package lat.elmidraft.skillpath.ui.rating

import android.os.Bundle
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import lat.elmidraft.skillpath.databinding.ActivityRatingBinding
import lat.elmidraft.skillpath.utils.LoadingDialogUtils
import lat.elmidraft.skillpath.utils.SharedPrefUtils

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private val viewModel : RatingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id",-1)

        with(binding){
            btnSubmitRating.setOnClickListener{
                viewModel.rating(SharedPrefUtils.getUser(this@RatingActivity).token, id, ratingBar.rating.toInt())
            }
            ratingBar.onRatingBarChangeListener = OnRatingBarChangeListener { _, rating, _ ->
                tvRatingValue.text = "Rating: ${rating.toInt()}"
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
        viewModel.message.observe(this){ message->
            val dialog = AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
    }
}