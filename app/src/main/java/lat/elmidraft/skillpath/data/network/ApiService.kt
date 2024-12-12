package lat.elmidraft.skillpath.data.network

import lat.elmidraft.skillpath.data.model.login.LoginRequest
import lat.elmidraft.skillpath.data.model.login.LoginResponse
import lat.elmidraft.skillpath.data.model.predict.PredictRequest
import lat.elmidraft.skillpath.data.model.predict.PredictResponse
import lat.elmidraft.skillpath.data.model.rating.RatingRequest
import lat.elmidraft.skillpath.data.model.rating.RatingResponse
import lat.elmidraft.skillpath.data.model.register.RegisterRequest
import lat.elmidraft.skillpath.data.model.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("predict")
    fun predict(@Body request: PredictRequest): Call<PredictResponse>

    @POST("rating")
    fun rating(@Body request: RatingRequest): Call<RatingResponse>
}