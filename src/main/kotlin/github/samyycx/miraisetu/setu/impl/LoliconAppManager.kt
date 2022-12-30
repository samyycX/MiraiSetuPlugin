package github.samyycx.miraisetu.setu.impl

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.fetcher.impl.LoliconAppFetcher
import github.samyycx.miraisetu.setu.ISetuManager
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.util.UploadUtil
import github.samyycx.miraisetu.vo.SetuData
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.toPlainText
import java.io.File

class LoliconAppManager : ISetuManager {

    private val fetcher = LoliconAppFetcher()

    override suspend fun getSetu(contact: Contact): MessageChain {
        val file : File
        MiraiSetu.cacheManager.getSetu(false).let { it ->
            return cacheOrApi(it, contact)
        }
    }

    override suspend fun getR18Setu(contact: Contact): MessageChain {
        MiraiSetu.cacheManager.getSetu(true).let { it ->
            return cacheOrApi(it, contact)
        }
    }

    override suspend fun getSetuByTag(tags: MutableList<String>, contact: Contact): MessageChain {
        val data = fetcher.fetchDataWithTags(tags)
        return api(data, contact)
    }



    private suspend fun cacheOrApi(it : File?, contact: Contact): MessageChain {
        return if (it == null) {
            val data = fetcher.fetchData()
            api(data, contact)

        } else {
            val builder = MessageChainBuilder()
            builder.add("pid: ${it.name.split("_").first()}".toPlainText())
            UploadUtil.uploadImage(it, contact)?.let { it1 -> builder.add(it1) }
            builder.build()
        }
    }

    private suspend fun api(data: SetuData, contact: Contact): MessageChain {
        val builder = MessageChainBuilder()

        val imageFile =DownloadUtil.downloadImage(data.imageUrl, File(MiraiSetu.dataFolder,"temp"))
        if (!data.success) {
            builder.add(data.err.toPlainText())
            return builder.build()
        }
        if (imageFile == null) {
            builder.add("请求到错误数据，请重试".toPlainText())
            return builder.build()
        }

        data.properties.forEach { it1 ->
            builder.add("${it1.key}: ${it1.value}\n".toPlainText())
        }
        UploadUtil.uploadImage(imageFile, contact)?.let { it1 -> builder.add(it1) }
        return builder.build()
    }

}