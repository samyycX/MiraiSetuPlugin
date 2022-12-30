package github.samyycx.miraisetu.sender

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.setu.ISetuManager
import github.samyycx.miraisetu.setu.impl.LoliconAppManager
import github.samyycx.miraisetu.setu.impl.PurrbotManager
import github.samyycx.miraisetu.setu.impl.WaifuIMManager
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.*

/**
 * 发送色图的工具
 */
class MessageSender {

    val setuManagers = mutableMapOf<String, ISetuManager>().apply {
        put("loliconapp", LoliconAppManager())
        put("waifuim", WaifuIMManager())
        put("purrbot", PurrbotManager())
    }

    suspend fun getSetuMessage(message: String, contact: Contact): MessageChain {
        val split = message.split(" ").drop(1)
        val manager = setuManagers[MiraiSetu.engine]
            ?: return MessageChainBuilder().apply {
                add("未知的api,请使用/色图 apis指令来获取api列表")
            }.build()

        return if (split.isEmpty()) {
            manager.getSetu(contact)
        } else if (split.size == 1 && split[0] == "r18") {
            manager.getR18Setu(contact)

        } else {
            manager.getSetuByTag(split.toMutableList(), contact)
        }


    }

}