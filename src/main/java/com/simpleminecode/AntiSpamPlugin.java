package com.simpleminecode;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class AntiSpamPlugin extends JavaPlugin implements Listener {
    private static final Map<UUID, Long> timeout = new HashMap<>();

    @Override
    public void onEnable() {
          getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();
        final long timeoutUntil = timeout.getOrDefault(player.getUniqueId(), 0L);

        if (timeoutUntil > System.currentTimeMillis() - 1000 * 5) {
            player.sendMessage("§cYou are typing too fast!");
            event.setCancelled(true);
            return;
        }

        System.out.println("Added");
        timeout.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        timeout.remove(event.getPlayer().getUniqueId());
    }
}
