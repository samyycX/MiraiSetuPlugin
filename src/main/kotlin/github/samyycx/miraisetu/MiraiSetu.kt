package github.samyycx.miraisetu

import github.samyycx.miraisetu.cache.CacheManager
import github.samyycx.miraisetu.fetcher.Fetcher
import github.samyycx.miraisetu.fetcher.impl.LoliconAppFetcher
import github.samyycx.miraisetu.sender.MessageSender
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.PlainText

object MiraiSetu : KotlinPlugin(
        JvmPluginDescription("github.samyycx.miraisetu","2.0")
)  {
        val fetcher: Fetcher = LoliconAppFetcher()
        val messageSender: MessageSender = MessageSender()
        val cacheManager: CacheManager = CacheManager()

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
                    } else {
                        println(text)
                        println(event.message.size)
                    }
                }
            }


        }

    override fun onDisable() {
        super.onDisable()
    }

}