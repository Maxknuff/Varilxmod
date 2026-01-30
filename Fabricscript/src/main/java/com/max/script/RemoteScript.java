package com.max.script;

import com.max.loader.IRemoteModule;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteScript implements IRemoteModule, ModInitializer {
    
    // Diese EINE Methode erfüllt BEIDE Interfaces gleichzeitig!
    @Override
    public void onInitialize() {
        runMyCode();
    }

    private void runMyCode() {
        System.out.println("------------------------------------------");
        System.out.println("GRÜSSE VOM SERVER! Version 1.0");
        System.out.println("Ich wurde dynamisch geladen.");
        System.out.println("------------------------------------------");
    }
}