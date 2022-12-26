package github.samyycx.miraisetu.util

import github.samyycx.miraisetu.MiraiSetu
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.net.URL

object DownloadUtil {

    fun downloadImage(url: String, folder: File): File? {

        val localFile = File(url)
        if (localFile.exists()) {
            return localFile
        }

        val fileName = url.split("/")
        val file = File(folder, fileName.last())
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