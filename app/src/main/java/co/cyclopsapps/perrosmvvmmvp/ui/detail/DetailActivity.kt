package co.cyclopsapps.perrosmvvmmvp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.cyclopsapps.perrosmvvmmvp.data.MediaItem
import co.cyclopsapps.perrosmvvmmvp.data.MediaProvider
import co.cyclopsapps.perrosmvvmmvp.databinding.ActivityDetailBinding
import co.cyclopsapps.perrosmvvmmvp.getViewModel
import co.cyclopsapps.perrosmvvmmvp.loadUrl
import co.cyclopsapps.perrosmvvmmvp.observe
import co.cyclopsapps.perrosmvvmmvp.setVisible
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

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel {
            observe(item) {
                supportActionBar?.title = it.title
                binding.detailThumb.loadUrl(it.url)
                binding.detailVideoIndicator.setVisible(it.type == MediaItem.Type.VIDEO)
                }
            }
        viewModel.onCreate(intent.getIntExtra(EXTRA_ID, -1))
        }
}