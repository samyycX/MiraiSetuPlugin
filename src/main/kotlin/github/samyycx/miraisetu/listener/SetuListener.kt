package github.samyycx.miraisetu.listener

import github.samyycx.miraisetu.MiraiSetu
import github.samyycx.miraisetu.sender.SetuSender
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.PlainText

object SetuListener {

    inline fun <reified T : MessageEvent> subscribe() {
        GlobalEventChannel.subscribeAlways<T> { event ->
            if (event.message[1] is PlainText) {
                val plainText = event.message[1] as PlainText
                val text = plainText.contentToString()
                if (text.startsWith("色图")) {
                    val reply = SetuSender.getSetuMessage(text, event.subject)
                    println(3)
                    event.subject.sendMessage(reply)
                } else {
                    println(text)
                    println(event.message.size)
                }
            }
        }
    }

}