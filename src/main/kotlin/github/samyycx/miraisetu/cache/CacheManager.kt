package github.samyycx.miraisetu.cache

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.vo.SetuData
import java.io.File
import java.util.concurrent.ConcurrentLinkedQueue

class CacheManager {

    val limit: Int = 100

    fun startCaching() {
        object : Thread() {
            override fun run() {
                while (true) {
                    val folder = File(MiraiSetu.dataFolder, "cache/normal")
                    val fileAmount = folder.listFiles()?.size ?: 0
                    if (fileAmount < limit) {
                        val datas = MiraiSetu.fetcher.fetchMultipleData(limit - fileAmount)
                        for (data in datas) {
                            Thread(CacheThread(data,"cache/normal")).start()
                        }
                    }

                    val r18Folder = File(MiraiSetu.dataFolder, "cache/r18")
                    val r18FileAmount = r18Folder.listFiles()?.size ?: 0
                    if (r18FileAmount < limit) {
                        val datas = MiraiSetu.fetcher.fetchMultipleDataWithTags(limit - fileAmount, mutableListOf("r18"))
                        for (data in datas) {
                            Thread(CacheThread(data,"cache/r18")).start()
                        }
                    }

                    sleep(5000)
                }
            }
        }.start()
    }

    fun getSetu(r18: Boolean) : File? {
        val folder = File(MiraiSetu.dataFolder, if (r18) "cache/r18" else "cache/normal")
        folder.listFiles().let {
            if (it == null) {
                return null
            }

            it.shuffle()
            return it[0]
        }
    }

}