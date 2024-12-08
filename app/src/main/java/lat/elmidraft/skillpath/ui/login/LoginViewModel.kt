package lat.elmidraft.skillpath.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import lat.elmidraft.skillpath.data.model.ErrorResponse
import lat.elmidraft.skillpath.data.network.ApiConfig
import lat.elmidraft.skillpath.data.model.login.LoginRequest
import lat.elmidraft.skillpath.data.model.login.LoginResponse
import lat.elmidraft.skillpath.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun login(ctx:Context, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(LoginRequest(email,password))
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        SharedPrefUtils.saveUser(loginResponse.data.token, email, loginResponse.data.name, ctx)
                        _isSuccess.value = true
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            _errorMessage.value = errorResponse.message
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("A", "onFailure: "+t.message)
            }
        })
    }
}