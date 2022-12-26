package github.samyycx.miraisetu.util

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.File

object UploadUtil {

    suspend fun uploadImage(file: File, contact: Contact): Image? {
        try {
            val image = file.uploadAsImage(contact)
            file.delete()
            return image
        } catch (exception: Exception) {
            contact.sendMessage("上传图片失败，可能是Mirai框架问题: \nException Message: ${exception.message}\n上传的文件: ${file.name}")
        }
        return null
    }

}