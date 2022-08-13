package com.samyyc.setu.util;

import com.samyyc.setu.vo.SetuData;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;

import java.util.Arrays;

public class MessageUtil {

    public static void sendSetu(boolean r18, Contact contact, String message) {
        String[] split = message.split(" ");
        SetuData data = null;
        if (split.length > 1) {
            String[] tags = Arrays.stream(split).skip(1).toArray(String[]::new);
            data = SetuUtil.getSetu(r18, tags);
        } else {
            data = SetuUtil.getSetu(r18);
        }
        contact.sendMessage(MessageUtil.setuData2QQMessage(data, contact));
    }
    public static Message setuData2QQMessage(SetuData data, Contact contact) {
        Image image = SetuUtil.getImage(data, contact);
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
