package co.cyclopsapps.perrosmvvmmvp

/**
 * Created by Carlos Daniel Agudelo on 03/09/2020.
 */
object MediaProvider {
    fun getItems(): List<MediaItem> {
        Thread.sleep(2000)
        return (1..10).map {
            MediaItem(
                it,
                "Title $it",
                "https://placekitten.com/200/200?image=$it",
                if (it % 3 == 0) MediaItem.Type.VIDEO else MediaItem.Type.PHOTO
            )
        }
    }
}