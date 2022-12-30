package github.samyycx.miraisetu.setu.impl

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.fetcher.impl.PurrbotFetcher
import github.samyycx.miraisetu.setu.ISetuManager
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.util.UploadUtil
import github.samyycx.miraisetu.vo.SetuData
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import java.io.File

// purrbot没几个tag 这里直接封装
class PurrbotManager : ISetuManager {

    private val fetcher = PurrbotFetcher()
    private val tagsList = mutableListOf("neko","okami","kitsune")


    override suspend fun getSetu(contact: Contact): MessageChain {
        return toMessage(fetcher.fetchDataWithTags(mutableListOf("sfw", tagsList.random())), contact)
    }

    override suspend fun getR18Setu(contact: Contact): MessageChain {
        return toMessage(fetcher.fetchDataWithTags(mutableListOf("nsfw", "neko")), contact)
    }

    override suspend fun getSetuByTag(tags: MutableList<String>, contact: Contact): MessageChain {
        if (tags.size == 2) {
            if (mutableListOf("sfw","nsfw").contains(tags[0])) {
                if (mutableListOf("neko","okami","kitsune").contains(tags[1])) {
                    val data = fetcher.fetchDataWithTags(tags);
                    return toMessage(data, contact)
                }
            }
        }

        return MessageChainBuilder().apply {
            add("此接口暂不支持此格式的tag，请删除tag使用")
        }.build()

    }

    private suspend fun toMessage(data: SetuData, contact: Contact) : MessageChain {
        val builder = MessageChainBuilder()

        if (!data.success) {
            return builder.apply {
                add(data.err)
            }.build()
        }

        DownloadUtil.downloadImage(data.imageUrl, File(MiraiSetu.dataFolder, "temp"))?.let {
            UploadUtil.uploadImage(it, contact)?.let {
                    it1 -> builder.add(it1)
            }
        }

        return builder.build()

    }
}