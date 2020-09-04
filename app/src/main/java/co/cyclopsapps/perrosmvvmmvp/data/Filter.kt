package co.cyclopsapps.perrosmvvmmvp.data

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */

sealed class Filter {
    object  None : Filter()
    class ByType(val type: MediaItem.Type) : Filter()
}