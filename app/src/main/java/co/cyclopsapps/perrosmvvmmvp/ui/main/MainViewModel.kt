package co.cyclopsapps.perrosmvvmmvp.ui.main

import android.app.usage.UsageEvents
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.cyclopsapps.perrosmvvmmvp.Event
import co.cyclopsapps.perrosmvvmmvp.data.Filter
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import co.cyclopsapps.perrosmvvmmvp.data.MediaProviderImpl
import co.cyclopsapps.perrosmvvmmvp.data.MediaProviderImpl.getItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.viewModelScope

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class MainViewModel(
    private val mediaProvider: MediaProvider = MediaProviderImpl,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO) : ViewModel() {

    private val _items = MutableLiveData<List<MediaItem>>()
    val items: LiveData<List<MediaItem>> get() = _items

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail

    fun onFilterSelected(filter: Filter) {
        viewModelScope.launch {
            _progressVisible.value = true
            _items.value = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            _progressVisible.value = false
        }
    }

    private fun getFilteredItems(filter: Filter): List<MediaItem> {
        return mediaProvider.getItems().let { media ->
            when (filter) {
                Filter.None -> media
                is Filter.ByType -> media.filter { it.type == filter.type }
            }
        }
    }

    fun onItemClicked(item: MediaItem) {
        _navigateToDetail.value = Event(item.id)
    }
}