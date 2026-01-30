package com.max.loader;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;

public class RemoteLoader implements ModInitializer {
    public static final String MOD_ID = "remoteloader";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private IRemoteModule loadedScript;

    @Override
    public void onInitialize() {
        LOGGER.info("RemoteLoader sucht nach Updates auf GitHub...");

        try {
            // HIER DEN LINK ÄNDERN!
            // Es muss der "Raw" Link zu deiner Jar-Datei sein.
            // Beispiel: https://github.com/DeinName/DeinRepo/raw/main/remotescript-1.0.0.jar
            // Oder via GitHub Pages / Releases.
            
            // Platzhalter - Du musst deinen echten Link hier einfügen:
            String githubUrl = "https://github.com/DeinUser/DeinRepo/raw/main/remotescript-1.0.0.jar";
            URL url = new URL(githubUrl);

            LOGGER.info("Lade Script von: " + url.toString());

            // ClassLoader erstellen
            URLClassLoader child = new URLClassLoader(
                new URL[] { url }, 
                this.getClass().getClassLoader()
            );

            // Klasse laden und ausführen (Rest bleibt gleich)
            Class<?> classToLoad = Class.forName("com.max.script.RemoteScript", true, child);
            Object instance = classToLoad.getDeclaredConstructor().newInstance();

            if (instance instanceof IRemoteModule) {
                loadedScript = (IRemoteModule) instance;
                loadedScript.onInitialize();
                LOGGER.info("Remote-Script erfolgreich von GitHub geladen!");
            } else {
                LOGGER.error("Fehler: Interface nicht implementiert.");
            }

        } catch (Exception e) {
            LOGGER.error("Kritischer Fehler - Konnte Script nicht laden (Internet weg? Falscher Link?):", e);
        }
    }
}