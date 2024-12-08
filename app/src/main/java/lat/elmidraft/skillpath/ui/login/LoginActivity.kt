package lat.elmidraft.skillpath.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lat.elmidraft.skillpath.R
import lat.elmidraft.skillpath.databinding.ActivityLoginBinding
import lat.elmidraft.skillpath.databinding.ActivitySplashBinding
import lat.elmidraft.skillpath.ui.main.MainActivity
import lat.elmidraft.skillpath.ui.signup.SignupActivity
import lat.elmidraft.skillpath.ui.splash.SplashViewModel
import lat.elmidraft.skillpath.utils.LoadingDialogUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            tvDontHaveAccount.setOnClickListener{
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
                finish()
            }
            btnSignin.setOnClickListener{
                viewModel.login(
                    this@LoginActivity,
                    etEmailsignin.text.toString(),
                    etPasswordsignin.text.toString()
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
        viewModel.isSuccess.observe(this){ isSuccess->
            if(isSuccess){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
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