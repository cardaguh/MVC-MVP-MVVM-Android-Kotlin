package co.cyclopsapps.perrosmvvmmvp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.cyclopsapps.perrosmvvmmvp.databinding.ViewMediaItemBinding
import kotlin.properties.Delegates

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */

private typealias MediaListener = (MediaItem) -> Unit

class MediaAdapter(items: List<MediaItem> = emptyList(), private val listener: MediaListener) :
RecyclerView.Adapter<MediaAdapter.ViewHolder>()
{
    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.view_media_item, false)
        return ViewHolder(v, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View, private val listener: MediaListener):
        RecyclerView.ViewHolder(view) {
        private val binding = ViewMediaItemBinding.bind(view)

        fun bind(mediaItem: MediaItem) {
            with(binding) {
                mediaTitle.text = mediaItem.title
                mediaThumb.loadUrl(mediaItem.url)
                root.setOnClickListener { listener(mediaItem) }

                mediaVideoIndicator.visibility = when (mediaItem.type) {
                    MediaItem.Type.PHOTO -> View.GONE
                    MediaItem.Type.VIDEO -> View.VISIBLE
               }
            }
        }
    }

}