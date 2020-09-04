package co.cyclopsapps.perrosmvvmmvp.ui.detail

import android.view.View
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class DetailPresenter(private val view: View, private val scope: CoroutineScope) {

    interface View {
        fun setTitle(title: String)
        fun setImage(url: String)
        fun setDetailIndicatorVisible(visible: Boolean)
    }

    fun onCreate(itemId: Int) {
        scope.launch {
            val items = withContext(Dispatchers.IO) { MediaProvider.getItems() }
            val item = items.find { it.id == itemId }

            item?.let {
                view.setTitle(item.title)
                view.setImage(item.url)
                view.setDetailIndicatorVisible(item.type == MediaItem.Type.VIDEO)
            }
        }
    }
}