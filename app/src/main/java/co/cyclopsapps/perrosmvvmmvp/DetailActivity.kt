package co.cyclopsapps.perrosmvvmmvp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.cyclopsapps.perrosmvvmmvp.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:extraId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
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
        }
    }
}