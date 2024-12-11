package lat.elmidraft.skillpath.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import lat.elmidraft.skillpath.R
import lat.elmidraft.skillpath.databinding.ActivityMainBinding
import lat.elmidraft.skillpath.profile.ProfileActivity
import lat.elmidraft.skillpath.ui.login.LoginActivity
import lat.elmidraft.skillpath.ui.recommendation.RecommendationActivity

class MainActivity : AppCompatActivity() {
    data class Tag(
        val textView: TextView,
        var isSelected: Boolean = false
    )
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val listTag = arrayOf(
                Tag(tvBusiness),
                Tag(tvProjectManagement),
                Tag(tvProgramming),
                Tag(tvComputerScience),
                Tag(tvDataAnalysis),
                Tag(tvDataScience),
                Tag(tvLeadership),
                Tag(tvLanguage),
                Tag(tvLifeScience),
                Tag(tvEngineering),
                Tag(tvCoding),
                Tag(tvMachineLearning),
                Tag(tvModeling),
                Tag(tvMathematics),
                Tag(tvDataSecurity),
                Tag(tvSoftwareDevelopment),
                Tag(tvStatistics),
                Tag(tvPython)
            )

            for(tag in listTag){
                tag.textView.setOnClickListener{
                    tag.isSelected=!tag.isSelected
                    tag.textView.background = ContextCompat.getDrawable(this@MainActivity,
                        if(tag.isSelected)
                            R.drawable.tag_selected_background
                        else
                            R.drawable.tag_background
                    )
                }
            }
            ivProfile.setOnClickListener{
                startActivity(
                    Intent(
                        this@MainActivity,
                        ProfileActivity::class.java
                    )
                )
            }
            btnSubmit.setOnClickListener{
                var query = etSearch.text.toString()
                for(tag in listTag){
                    if(tag.isSelected){
                        query+=",${tag.textView.text}"
                    }
                }
                if(query!=""){
                    startActivity(
                        Intent(
                            this@MainActivity,
                            RecommendationActivity::class.java
                        ).putExtra("query",query)
                    )
                }else{
                    val dialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle("Message")
                        .setMessage("Please fill in or select one tag")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                    dialog.show()
                }
            }
        }
    }
}