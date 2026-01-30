package com.max.loader; // In Fabricscript musst du das package evtl. anpassen oder gleich lassen

public interface IRemoteModule {
    void onInitialize();
    // Hier kannst du später mehr Methoden hinzufügen, z.B.:
    // void onServerTick(MinecraftServer server);
}