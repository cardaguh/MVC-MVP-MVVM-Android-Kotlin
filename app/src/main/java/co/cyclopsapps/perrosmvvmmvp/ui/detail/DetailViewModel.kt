package co.cyclopsapps.perrosmvvmmvp.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import co.cyclopsapps.perrosmvvmmvp.data.MediaProviderImpl
import co.cyclopsapps.perrosmvvmmvp.data.MediaProviderImpl.getItems
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class DetailViewModel(private val mediaProvider: MediaProviderImpl
                      , private val ioDispatcher: CoroutineContext = Dispatchers.IO) : ViewModel() {

    private val _item = MutableLiveData<MediaItem>()
    val item: LiveData<MediaItem> get() = _item

    fun onCreate(itemId: Int) {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) { mediaProvider.getItems() }
            val item = items.find { it.id == itemId }

            item?.let {
                _item.value = item
            }
        }
    }
}