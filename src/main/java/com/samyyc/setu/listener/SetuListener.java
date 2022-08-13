package com.samyyc.setu.listener;

import com.samyyc.setu.Setu;
import com.samyyc.setu.util.MessageUtil;
import com.samyyc.setu.util.SetuUtil;
import com.samyyc.setu.vo.SetuData;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetuListener {

    public static void listen() {
        GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, event -> {
            MessageChain chain = event.getMessage();
            List<SingleMessage> messages = chain.stream().filter(singleMessage -> !(singleMessage instanceof MessageSource)).collect(Collectors.toList());
            if (messages.size() > 0) {
                if (messages.get(0) instanceof PlainText) {
                    PlainText plainText = (PlainText) messages.get(0);
                    String message = plainText.contentToString();
                    if (message.startsWith("色图")) {
                        MessageUtil.sendSetu(false, event.getSubject(), message);
                    } else if (message.startsWith("r18色图")) {
                        MessageUtil.sendSetu(true, event.getSubject(), message);
                    }
                }
            }
        });
    }

}
