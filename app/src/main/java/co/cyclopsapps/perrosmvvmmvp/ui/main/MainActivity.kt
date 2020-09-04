package co.cyclopsapps.perrosmvvmmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import co.cyclopsapps.perrosmvvmmvp.R
import co.cyclopsapps.perrosmvvmmvp.data.Filter
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import co.cyclopsapps.perrosmvvmmvp.databinding.ActivityMainBinding
import co.cyclopsapps.perrosmvvmmvp.setVisible
import co.cyclopsapps.perrosmvvmmvp.startActivity
import co.cyclopsapps.perrosmvvmmvp.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val adapter by lazy {
        MediaAdapter {
            presenter.onItemClicked(
                it
            )
        }
    }
    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter(this, lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter
        //updateItems()
        presenter.onFilterSelected(Filter.None)
    }

    private fun itemClicked(mediaItem: MediaItem) {
        startActivity<DetailActivity>(
            DetailActivity.EXTRA_ID to mediaItem.id)
    }

    private fun updateItems(filter: Filter = Filter.None) {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val items = withContext(Dispatchers.IO) { getFilteredItems( filter )}
            adapter.items = items
            binding.progressBar.visibility = View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }

        presenter.onFilterSelected(filter)
        return true
    }

    override fun setProgressVisible(visible: Boolean) {
        binding.progressBar.setVisible(visible)
    }

    override fun updateItems(items: List<MediaItem>) {
        adapter.items = items
    }

    override fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}