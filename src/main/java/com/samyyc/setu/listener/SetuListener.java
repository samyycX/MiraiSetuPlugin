package com.samyyc.setu.listener;

import com.samyyc.setu.util.MessageUtil;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;

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
                        event.getSubject().sendMessage(MessageUtil.getSetuMessageFromApi(false, event.getSubject(), message));
                    } else if (message.startsWith("r18色图")) {
                        event.getSubject().sendMessage(MessageUtil.getSetuMessageFromApi(true, event.getSubject(), message));
                    }
                }
            }
        });
    }

}
