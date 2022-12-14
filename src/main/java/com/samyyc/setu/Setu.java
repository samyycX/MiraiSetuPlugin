package com.samyyc.setu;

import com.samyyc.setu.cache.SetuCache;
import com.samyyc.setu.listener.SetuListener;
import com.samyyc.setu.util.ConfigUtil;
import com.samyyc.setu.util.MessageUtil;
import com.samyyc.setu.util.SetuUtil;
import com.samyyc.setu.vo.SetuData;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class Setu extends JavaPlugin {
    public static final Setu INSTANCE = new Setu();

    private Setu() {
        super(new JvmPluginDescriptionBuilder("com.samyyc.setu", "1.1.0")
                .name("Setu")
                .info("通过色图API获取色图")
                .author("samyyc")
                .build());
    }

    @Override
    public void onEnable() {

        if (!getConfigFolder().exists()) {
            getConfigFolder().mkdir();
        }
        File file = new File(getConfigFolder(), "config.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
                ConfigUtil.initConfig(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        SetuListener.listen();
        SetuCache.init(file);

        getLogger().info("[MiralSetuPlugin] 色图插件加载成功!");
    }

    public void test() {
        Bot bot = BotFactory.INSTANCE.newBot(2406668080L,"Samyyc0508", new BotConfiguration() {{
            fileBasedDeviceInfo();
        }});
        bot.login();
        System.out.println(SetuCache.getSetuCacheMessage(false, bot.getAsFriend()));
    }
}