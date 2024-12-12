package lat.elmidraft.skillpath.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lat.elmidraft.skillpath.data.model.predict.PredictResponseData
import lat.elmidraft.skillpath.databinding.ItemRecommendationBinding


class PredictionAdapter(private val listener: PredictionAdapterListener) : RecyclerView.Adapter<PredictionAdapter.ViewHolder>() {
    var predictionList: List<PredictResponseData> = ArrayList()

    fun setList(predictionList: List<PredictResponseData>){
        this.predictionList = predictionList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(prediction: PredictResponseData) {
            with(binding){
                tvProvider.text = prediction.provider
                namaKursus.text = prediction.title
                descKursus.text = prediction.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = predictionList[position]
        holder.itemView.setOnClickListener{
            this.listener.onCLick(book)
        }
        holder.bindData(book)
    }

    override fun getItemCount(): Int {
        return predictionList.size
    }

    public interface PredictionAdapterListener {
        fun onCLick(prediction: PredictResponseData)
    }
}