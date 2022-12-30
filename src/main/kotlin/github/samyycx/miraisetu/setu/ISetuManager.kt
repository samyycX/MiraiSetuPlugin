package github.samyycx.miraisetu.setu

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.MessageChain

interface ISetuManager {

    suspend fun getSetu(contact: Contact) : MessageChain

    suspend fun getR18Setu(contact: Contact) : MessageChain

    suspend fun getSetuByTag(tags: MutableList<String>, contact: Contact): MessageChain
}