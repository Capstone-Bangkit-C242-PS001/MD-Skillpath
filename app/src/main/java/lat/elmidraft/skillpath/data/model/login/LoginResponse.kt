package lat.elmidraft.skillpath.data.model.login

data class LoginResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: LoginResponseData
)