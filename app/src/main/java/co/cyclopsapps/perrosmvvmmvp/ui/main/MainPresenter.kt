package co.cyclopsapps.perrosmvvmmvp.ui.main

import android.view.View
import co.cyclopsapps.perrosmvvmmvp.data.Filter
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class MainPresenter(private val view: View, private val scope: CoroutineScope) {

    interface View {
        fun setProgressVisible(visible: Boolean)
        fun updateItems(items: List<MediaItem>)
        fun navigateToDetail(id: Int)
    }

    fun onFilterSelected(filter: Filter) {
        scope.launch {
            view.setProgressVisible(true)
            val items = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            view.updateItems(items)
            view.setProgressVisible(false)
        }
    }

    private fun getFilteredItems(filter: Filter): List<MediaItem> {
        return MediaProvider.getItems().let { media ->
            when (filter) {
                Filter.None -> media
                is Filter.ByType -> media.filter { it.type == filter.type }
            }
        }
    }

    fun onItemClicked(item: MediaItem) {
        view.navigateToDetail(item.id)
    }
}