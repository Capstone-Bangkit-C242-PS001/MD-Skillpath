package lat.elmidraft.skillpath.data.model.predict

data class PredictResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: List<PredictResponseData>
)
