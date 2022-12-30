package github.samyycx.miraisetu.setu.impl

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.fetcher.impl.WaifuIMFetcher
import github.samyycx.miraisetu.setu.ISetuManager
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.util.UploadUtil
import github.samyycx.miraisetu.vo.SetuData
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import java.io.File

class WaifuIMManager : ISetuManager {

    val fetcher = WaifuIMFetcher()

    override suspend fun getSetu(contact: Contact): MessageChain {
        val data = fetcher.fetchData()
        return toMessage(data, contact)

    }

    override suspend fun getR18Setu(contact: Contact): MessageChain {
        val data = fetcher.fetchDataWithTags(mutableListOf("r18"))
        return toMessage(data, contact)
    }

    override suspend fun getSetuByTag(tags: MutableList<String>, contact: Contact): MessageChain {
        val data = fetcher.fetchDataWithTags(tags)
        return toMessage(data, contact)
    }

    private suspend fun toMessage(data : SetuData, contact: Contact): MessageChain {
        val builder = MessageChainBuilder()
        data.properties.forEach { k, v -> builder.add("${k}: $v") }
        DownloadUtil.downloadImage(data.imageUrl, File(MiraiSetu.dataFolder,"temp"))?.let {
            UploadUtil.uploadImage(it, contact)?.let {
                    it1 -> builder.add(it1)
            }
        }
        return builder.build()
    }
}