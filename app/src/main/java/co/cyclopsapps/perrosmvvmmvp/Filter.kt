package co.cyclopsapps.perrosmvvmmvp

import android.media.browse.MediaBrowser

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */

sealed class Filter {
    object  None : Filter()
    class ByType(val type: MediaItem.Type) : Filter()
}