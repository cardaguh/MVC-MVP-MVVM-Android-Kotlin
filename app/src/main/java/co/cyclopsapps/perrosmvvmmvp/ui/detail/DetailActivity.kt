package co.cyclopsapps.perrosmvvmmvp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import co.cyclopsapps.perrosmvvmmvp.databinding.ActivityDetailBinding
import co.cyclopsapps.perrosmvvmmvp.loadUrl
import co.cyclopsapps.perrosmvvmmvp.setVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    companion object {
        const val EXTRA_ID = "DetailActivity:extraId"
    }

    private val presenter = DetailPresenter(this, lifecycleScope)
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onCreate(intent.getIntExtra(EXTRA_ID, -1))

        /*lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) { MediaProvider.getItems() }
            val item = items.find { it.id == intent.getIntExtra(EXTRA_ID, -1) }

            item?.let {
                supportActionBar?.title = item.title
                binding.detailThumb.loadUrl(item.url)
                binding.detailVideoIndicator.visibility = when (item.type) {
                    MediaItem.Type.PHOTO -> View.GONE
                    MediaItem.Type.VIDEO -> View.VISIBLE
                }
            }
        }*/
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setImage(url: String) {
        binding.detailThumb.loadUrl(url)
    }

    override fun setDetailIndicatorVisible(visible: Boolean) {
        binding.detailVideoIndicator.setVisible(visible)
    }

}