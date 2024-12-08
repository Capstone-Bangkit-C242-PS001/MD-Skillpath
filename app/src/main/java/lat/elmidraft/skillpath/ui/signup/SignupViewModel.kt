package lat.elmidraft.skillpath.ui.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lat.elmidraft.skillpath.data.model.ErrorResponse
import lat.elmidraft.skillpath.data.model.login.LoginRequest
import lat.elmidraft.skillpath.data.model.login.LoginResponse
import lat.elmidraft.skillpath.data.model.register.RegisterRequest
import lat.elmidraft.skillpath.data.model.register.RegisterResponse
import lat.elmidraft.skillpath.data.network.ApiConfig
import lat.elmidraft.skillpath.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun signup(name:String, email: String, password: String, confirmPassword: String) {
        if(confirmPassword != password){
            _message.value = "Passwords do not match"
            return
        }
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(RegisterRequest(name, email, password))
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { registerResponse ->
                        _message.value = registerResponse.message
                    }
                    _isSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            _message.value = errorResponse.message
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}