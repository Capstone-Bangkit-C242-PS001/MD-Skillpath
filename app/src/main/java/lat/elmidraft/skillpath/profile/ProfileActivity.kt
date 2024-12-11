package lat.elmidraft.skillpath.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lat.elmidraft.skillpath.R
import lat.elmidraft.skillpath.databinding.ActivityMainBinding
import lat.elmidraft.skillpath.databinding.ActivityProfileBinding
import lat.elmidraft.skillpath.ui.login.LoginActivity
import lat.elmidraft.skillpath.ui.recommendationdetail.RecommendationDetailActivity
import lat.elmidraft.skillpath.utils.SharedPrefUtils

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val user = SharedPrefUtils.getUser(this@ProfileActivity)
            userEmail.setText(user.email)
            userUsername.setText(user.username)
            btnBack.setOnClickListener{
                finish()
            }
            btnLogout.setOnClickListener{
                SharedPrefUtils.removeUserToken(this@ProfileActivity)
                finishAffinity()
                startActivity(
                    Intent(
                        this@ProfileActivity,
                        LoginActivity::class.java
                    )
                )
            }
        }

    }
}