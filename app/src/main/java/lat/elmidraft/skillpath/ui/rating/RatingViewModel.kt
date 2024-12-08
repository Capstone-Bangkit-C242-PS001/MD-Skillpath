package lat.elmidraft.skillpath.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import lat.elmidraft.skillpath.data.model.ErrorResponse
import lat.elmidraft.skillpath.data.model.rating.RatingRequest
import lat.elmidraft.skillpath.data.model.rating.RatingResponse
import lat.elmidraft.skillpath.data.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun rating(token: String, id: Int, rating: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).rating(RatingRequest(id, rating))
        client.enqueue(object : Callback<RatingResponse> {
            override fun onResponse(
                call: Call<RatingResponse>,
                response: Response<RatingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { registerResponse ->
                        _message.value = registerResponse.message
                    }
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

            override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}