package miworkgroup.safefix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class CaptionedImagesAdapter(private var captions: ArrayList<String>,
                             private val imageIds: ArrayList<Int>,
                             private val category: ArrayList<String>,
                             private val time: ArrayList<String>,
                             private val idArray: ArrayList<Int>
) :

    RecyclerView.Adapter<CaptionedImagesAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageID : ImageView
        var money : TextView
        var categoryHold : TextView
        var times : TextView
        var id : TextView
        init {
            imageID = itemView.findViewById(R.id.info_image)
            money = itemView.findViewById(R.id.info_text)
            categoryHold = itemView.findViewById(R.id.info_category)
            times = itemView.findViewById(R.id.info_time)
            id = itemView.findViewById(R.id.textView_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptionedImagesAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_cap_im2, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.times.text = time.get(position)
        holder.money.text = captions.get(position)
        holder.categoryHold.text = category.get(position)
        holder.id.text = idArray.get(position).toString()
        holder.imageID.setImageResource(imageIds[position])
    }

    override fun getItemCount(): Int {
        return captions.size
    }
}