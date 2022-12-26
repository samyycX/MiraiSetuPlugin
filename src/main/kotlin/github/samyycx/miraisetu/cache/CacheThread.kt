package github.samyycx.miraisetu.cache

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.vo.SetuData
import java.io.File

class CacheThread(private val data: SetuData, private val folder: String) : Runnable {

    override fun run() {
        val url = data.imageUrl
        val file = DownloadUtil.downloadImage(url, File(MiraiSetu.dataFolder, "uncompleted"))
        file?.copyTo(File(MiraiSetu.dataFolder, "${folder}/${file.name}"))
    }

}