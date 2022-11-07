package github.samyycx.miraisetu.sender

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.util.DownloadUtil
import github.samyycx.miraisetu.vo.SetuData
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage

/**
 * 发送色图的工具
 */
object SetuSender {



    suspend fun getSetuMessage(message: String, contact: Contact): MessageChain {
        val split = message.split(" ").drop(1)
        val data: SetuData = if (split.isEmpty()) {
            MiraiSetu.fetcher.fetchData()
        } else {
            MiraiSetu.fetcher.fetchDataWithTags(ArrayList(split))
        }
        val builder = MessageChainBuilder()

        val imageFile =DownloadUtil.downloadImage(data.imageUrl)
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
        val image = imageFile.uploadAsImage(contact)
        builder.add(image)
        println(image)

        return builder.build()


    }

}