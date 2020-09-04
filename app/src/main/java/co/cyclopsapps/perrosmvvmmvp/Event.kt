package co.cyclopsapps.perrosmvvmmvp

/**
 * Created by Carlos Daniel Agudelo on 04/09/2020.
 */
data class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}