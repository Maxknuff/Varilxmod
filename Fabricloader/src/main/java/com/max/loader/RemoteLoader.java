package com.max.loader;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class RemoteLoader implements ModInitializer {
	public static final String MOD_ID = "remoteloader";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private IRemoteModule loadedScript;

	@Override
	public void onInitialize() {
		LOGGER.info("RemoteLoader startet...");

        try {
            // URL zu deinem Server. 
            // WICHTIG: Zum Testen lokal kannst du "file:///" nutzen.
            // Beispiel Server: new URL("http://dein-server.de/remotescript-1.0.0.jar");
            // Beispiel Lokal (Windows): 
            URL url = new File("C:/Users/Max/Desktop/remotescript-1.0.0.jar").toURI().toURL();

            // Wir erstellen einen ClassLoader, der Zugriff auf Minecraft-Klassen hat (durch 'this.getClass()...')
            URLClassLoader child = new URLClassLoader(
                new URL[] { url }, 
                this.getClass().getClassLoader()
            );

            // Wir laden die Klasse aus dem anderen Projekt
            // Der Name muss EXAKT übereinstimmen mit dem in Fabricscript
            Class<?> classToLoad = Class.forName("com.max.script.RemoteScript", true, child);

            // Wir erstellen eine Instanz
            Object instance = classToLoad.getDeclaredConstructor().newInstance();

            // Wir prüfen, ob es das Interface implementiert
            if (instance instanceof IRemoteModule) {
                loadedScript = (IRemoteModule) instance;
                loadedScript.onInitialize();
                LOGGER.info("Remote-Script erfolgreich ausgeführt!");
            } else {
                LOGGER.error("Die geladene Klasse implementiert nicht IRemoteModule!");
            }

        } catch (Exception e) {
            LOGGER.error("Fehlerbeim Laden des Remote-Scripts:", e);
            e.printStackTrace();
        }
	}
}