package github.samyycx.miraisetu

import github.samyycx.miraisetu.fetcher.SetuFetcher
import github.samyycx.miraisetu.fetcher.impl.LoliconAppFetcher
import github.samyycx.miraisetu.listener.SetuListener
import github.samyycx.miraisetu.sender.SetuSender
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.utils.BotConfiguration
import java.io.File

object MiraiSetu : KotlinPlugin(
        JvmPluginDescription("github.samyycx.miraisetu","2.0")
)  {
        val fetcher: SetuFetcher = LoliconAppFetcher()

        override fun onEnable() {
            super.onEnable()
            SetuListener.subscribe<MessageEvent>()

            //println(File(".").absolutePath)




            /*
           async {
                val bot = BotFactory.newBot(2406668080L, "Samyyc0508") {
                    fileBasedDeviceInfo("device.json")
                    inheritCoroutineContext()
                }
               bot.login()
            }

             */


        }

}