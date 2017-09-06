package org.systic.citadel.util;

import net.minecraft.server.v1_7_R4.MinecraftServer;

public class Threads {

    public static boolean isAsync() {
        return Thread.currentThread() != MinecraftServer.getServer().primaryThread;
    }

    public static boolean isSync(){
        return Thread.currentThread() == MinecraftServer.getServer().primaryThread;
    }

}
