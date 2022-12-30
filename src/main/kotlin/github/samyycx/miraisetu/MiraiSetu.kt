package github.samyycx.miraisetu

import github.samyycx.miraisetu.cache.CacheManager
import github.samyycx.miraisetu.fetcher.Fetcher
import github.samyycx.miraisetu.fetcher.impl.LoliconAppFetcher
import github.samyycx.miraisetu.sender.MessageSender
import github.samyycx.miraisetu.setu.ISetuManager
import github.samyycx.miraisetu.setu.impl.LoliconAppManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.PlainText

object MiraiSetu : KotlinPlugin(
        JvmPluginDescription("github.samyycx.miraisetu","2.0")
)  {
        val messageSender: MessageSender = MessageSender()
        val cacheManager: CacheManager = CacheManager()
        var engine = "loliconapp"

        override fun onEnable() {
            super.onEnable()
            cacheManager.startCaching()

            GlobalEventChannel.subscribeAlways<MessageEvent> { event ->
                if (event.message[1] is PlainText) {
                    val plainText = event.message[1] as PlainText
                    val text = plainText.contentToString()
                    if (text.startsWith("色图")) {
                        val reply = messageSender.getSetuMessage(text, event.subject)
                        event.subject.sendMessage(reply)
                    } else if (text.startsWith("!色图")) {
                        val split = text.split(" ")
                        when(split[1]) {
                            "api" -> event.subject.sendMessage("当前使用的API: ${engine}")
                            "apis" -> event.subject.sendMessage("支持的API: loliconapp, waifuim")
                            "apiuse" -> {
                                engine = split[2]
                                event.subject.sendMessage("成功设置为: ${engine}")
                            }
                        }
                    }
                }
            }


        }

    override fun onDisable() {
        super.onDisable()
    }

}