package com.max.script;

import com.max.loader.IRemoteModule;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class RemoteScript implements IRemoteModule, ModInitializer {

    // Status-Variablen
    private boolean farmingActive = false;
    private boolean wasToggleKeyPressed = false;
    private float lockedYaw = 0f;
    private float lockedPitch = 0f;

    // Nur EINE Initialize Methode!
    @Override
    public void onInitialize() {
        System.out.println("RemoteScript: Farming Modul geladen!");
        registerFarmingLogic();
    }

    private void registerFarmingLogic() {
        // Dieser Code läuft jeden einzelnen "Tick" (20x pro Sekunde oder öfter im Client)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // --- TASTENDRUCK CHECK (Taste "J" zum Umschalten) ---
            // Wir nutzen GLFW direkt, weil wir keine Keybinds registrieren können (Registry ist frozen)
            boolean isKeyPressed = InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_J);
            
            if (isKeyPressed && !wasToggleKeyPressed) {
                // Taste wurde gerade gedrückt -> Umschalten
                toggleFarming(client);
            }
            wasToggleKeyPressed = isKeyPressed; // Merken für den nächsten Tick

            // --- AKTIVE FARMING LOGIK ---
            if (farmingActive) {
                // 1. Blickrichtung fixieren (Yaw Lock)
                client.player.setYaw(lockedYaw);
                client.player.setPitch(lockedPitch);

                // 2. Automatisch schlagen/abbauen (Linke Maustaste halten)
                client.options.attackKey.setPressed(true);
            }
        });
    }

    private void toggleFarming(MinecraftClient client) {
        farmingActive = !farmingActive; // An/Aus wechseln

        if (farmingActive) {
            // Beim Anschalten: Aktuelle Blickrichtung auf die nächsten 90 Grad runden
            float currentYaw = client.player.getYaw();
            lockedYaw = Math.round(currentYaw / 90.0f) * 90.0f;
            lockedPitch = client.player.getPitch(); // Aktuelle Höhe beibehalten
            
            client.player.sendMessage(Text.of("§a[Veylor-Farming] AKTIV (Yaw: " + lockedYaw + ")"), true);
        } else {
            // Beim Ausschalten: Aufhören zu schlagen
            client.options.attackKey.setPressed(false);
            client.player.sendMessage(Text.of("§c[Veylor-Farming] DEAKTIVIERT"), true);
        }
    }
}