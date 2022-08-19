package com.samyyc.setu.config;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

public class GlobalConfig {

    public static Boolean r18EnableMasterControl = false;

    public static Message r18BannedMessage = new PlainText("R18主控制开关已关闭，禁止获取R18涩图");
    public static Message cacheFileNotFound = new PlainText("本地缓存文件出现错误, 请重新获取");

}
