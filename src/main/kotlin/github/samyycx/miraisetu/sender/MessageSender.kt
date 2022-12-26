package github.samyycx.miraisetu.sender

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.util.UploadUtil
import github.samyycx.miraisetu.vo.SetuData
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.File

/**
 * 发送色图的工具
 */
class MessageSender {



    suspend fun getSetuMessage(message: String, contact: Contact): MessageChain {
        val split = message.split(" ").drop(1)
        val data: SetuData

        if (split.isEmpty()) {
            MiraiSetu.cacheManager.getSetu(false).let {
                if (it == null) {
                    data = MiraiSetu.fetcher.fetchData()
                } else {
                    val builder = MessageChainBuilder()
                    builder.add("pid: ${it.name.split("_").first()}".toPlainText())
                    UploadUtil.uploadImage(it, contact)?.let { it1 -> builder.add(it1) }
                    return builder.build()
                }
            }
        } else if (split.size == 1 && split[0] == "r18") {
            MiraiSetu.cacheManager.getSetu(true).let {
                if (it == null) {
                    data = MiraiSetu.fetcher.fetchDataWithTags(ArrayList(split))
                } else {
                    val builder = MessageChainBuilder()
                    builder.add("pid: ${it.name.split("_").first()}".toPlainText())
                    UploadUtil.uploadImage(it, contact)?.let { it1 -> builder.add(it1) }
                    return builder.build()
                }
            }

        } else {
            data = MiraiSetu.fetcher.fetchDataWithTags(ArrayList(split))
        }
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

        data.properties.forEach {
            builder.add("${it.key}: ${it.value}\n".toPlainText())
        }
        UploadUtil.uploadImage(imageFile, contact)?.let { it1 -> builder.add(it1) }

        return builder.build()


    }

}