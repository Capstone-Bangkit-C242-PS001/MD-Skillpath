package lat.elmidraft.skillpath.data.model.register

data class RegisterResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: RegisterResponseData
)