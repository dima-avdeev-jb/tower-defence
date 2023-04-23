import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

private val images: MutableMap<String, ImageBitmap> = mutableMapOf()

@OptIn(ExperimentalResourceApi::class)
fun getImage(name: String): ImageBitmap {
    return images.getOrPut(name) {
        GlobalScope.launch {
            images.put(name, resource(name).readBytes().toImageBitmap())
        }
        ImageBitmap(1, 1)
    }
}

expect fun ByteArray.toImageBitmap(): ImageBitmap
