package com.max.script;

import com.max.loader.IRemoteModule; // Importiere das Interface!
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// WICHTIG: Du musst IRemoteModule implementieren
public class RemoteScript implements IRemoteModule, ModInitializer {
    
    // Für normales Fabric Laden (optional, falls du es auch standalone nutzen willst)
    @Override
    public void onInitialize() {
        runMyCode();
    }

    // Für deinen RemoteLoader
    @Override
    public void onInitialize() { // Name muss match Interface matchen
         runMyCode();
    }

    private void runMyCode() {
        System.out.println("------------------------------------------");
        System.out.println("GRÜSSE VOM SERVER! Version 1.0");
        System.out.println("Ich wurde dynamisch geladen.");
        System.out.println("------------------------------------------");
        
        // Hier kannst du Minecraft Code nutzen
        // z.B. Items registrieren (Vorsicht: Registry ist oft schon locked nach Start)
        // Besser: Chat Nachrichten, Events, etc.
    }
}