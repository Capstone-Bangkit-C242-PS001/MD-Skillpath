package lat.elmidraft.skillpath.ui.recommendation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import lat.elmidraft.skillpath.data.model.ErrorResponse
import lat.elmidraft.skillpath.data.network.ApiConfig
import lat.elmidraft.skillpath.data.model.login.LoginRequest
import lat.elmidraft.skillpath.data.model.login.LoginResponse
import lat.elmidraft.skillpath.data.model.predict.PredictRequest
import lat.elmidraft.skillpath.data.model.predict.PredictResponse
import lat.elmidraft.skillpath.data.model.predict.PredictResponseData
import lat.elmidraft.skillpath.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _listRecommendation = MutableLiveData<List<PredictResponseData>>()
    val listRecommendation: LiveData<List<PredictResponseData>> = _listRecommendation

    fun loadRecommendation(token: String, query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).predict(PredictRequest(query.split(",")))
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { predictionResponse ->
                       _listRecommendation.value = predictionResponse.data
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

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}