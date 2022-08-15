package com.samyyc.setu.config;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

public class GlobalConfig {

    public static Boolean r18EnableMasterControl = false;

    public static Message r18BannedMessage = new PlainText("R18主控制开关已关闭，禁止获取R18涩图");

}
