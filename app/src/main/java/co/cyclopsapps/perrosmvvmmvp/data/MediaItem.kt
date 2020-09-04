package co.cyclopsapps.perrosmvvmmvp.data

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */

data class MediaItem(val id: Int, val title: String, val url: String, val type: Type) {
    enum class Type { PHOTO, VIDEO }
}