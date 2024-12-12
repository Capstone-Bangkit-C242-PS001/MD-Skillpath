package lat.elmidraft.skillpath.ui.signup

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
import lat.elmidraft.skillpath.databinding.ActivitySignupBinding
import lat.elmidraft.skillpath.ui.login.LoginActivity
import lat.elmidraft.skillpath.ui.main.MainActivity
import lat.elmidraft.skillpath.ui.splash.SplashViewModel
import lat.elmidraft.skillpath.utils.LoadingDialogUtils

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel : SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            tvHaveAccount.setOnClickListener{
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            }
            btnSignup.setOnClickListener{
                viewModel.signup(
                    etUsername.text.toString(),
                    etEmailsignup.text.toString(),
                    etPasswordsignup.text.toString(),
                    etConfirmpassword.text.toString()
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
                resetForm()
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
            resetForm()
        }
    }

    fun resetForm(){
        with(binding){
            etUsername.setText(null)
            etEmailsignup.setText(null)
            etPasswordsignup.setText(null)
            etConfirmpassword.setText(null)
        }
    }
}