package lat.elmidraft.skillpath.data.model

data class ErrorResponse(
    val code: Int,
    val status: String,
    val message: String
)
