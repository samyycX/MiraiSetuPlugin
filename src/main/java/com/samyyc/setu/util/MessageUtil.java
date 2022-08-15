package com.samyyc.setu.util;

import com.samyyc.setu.cache.SetuCache;
import com.samyyc.setu.config.GlobalConfig;
import com.samyyc.setu.vo.SetuData;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;

import java.util.Arrays;

public class MessageUtil {

    public static Message getSetuMessageFromApi(boolean r18, Contact contact, String message) {
        String[] split = message.split(" ");
        if (split.length > 1) {
            String[] tags = Arrays.stream(split).skip(1).toArray(String[]::new);
            return getSetuMessageFromApi(r18, contact, tags);
        } else {
            if (SetuCache.isEnable()) {
                return SetuCache.getSetuCacheMessage(r18, contact);
            } else {
                return getSetuMessageFromApi(r18, contact);
            }
        }
    }

    public static Message getSetuMessageFromApi(boolean r18, Contact contact, String... tags) {
        if (r18) {
            if (!GlobalConfig.r18EnableMasterControl) {
                return GlobalConfig.r18BannedMessage;
            }
        }
        SetuData data;
        if (tags.length > 0) {
            data = SetuUtil.getSetu(r18, tags);
        } else {
            data = SetuUtil.getSetu(r18);
        }
        return getSetuMessage(contact, data);
    }

    public static Message getSetuMessage(Contact contact, SetuData data) {
        Image image = SetuUtil.getImage(data, contact);
        return MessageUtil.setuData2QQMessage(image, data, contact);
    }
    public static Message setuData2QQMessage(Image image, SetuData data, Contact contact) {

        if (image == null) {
            return new PlainText("获取图片出现错误，可能是指定的tag不存在");
        }
        return MessageUtils.newChain(
                new PlainText("pid: ")
                .plus(String.valueOf(data.getPid()))
                .plus("\n")
            ,
                new PlainText("画师: ")
                .plus(data.getAuthor())
            ,
                image
            );
    }

}
