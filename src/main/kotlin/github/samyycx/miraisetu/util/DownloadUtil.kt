package github.samyycx.miraisetu.util

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.net.URL

object DownloadUtil {

    fun downloadImage(url: String): File? {
        val ext = url.split(".").last()
        val file = File("./cache/temp.${ext}")
        println("2")
        try {
            FileUtils.copyURLToFile(
                URL(url),
                file,
                10000,
                10000
            )
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

}