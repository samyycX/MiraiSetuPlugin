package github.samyycx.miraisetu.util

import github.samyycx.miraisetu.MiraiSetu
import org.apache.commons.io.FileUtils
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

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
        val image: BufferedImage = ImageIO.read(file)
        val random = Random()
        image.setRGB(0, 0, getRandomColor(random))
        image.setRGB(0,image.height-1, getRandomColor(random))
        image.setRGB(image.width-1, 0, getRandomColor(random))
        image.setRGB(image.width-1, image.height -1, getRandomColor(random))
        ImageIO.write(image, "jpg", file)
        return file
    }


    private fun getRandomColor(random: Random) : Int {
        return Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)).rgb
    }

}