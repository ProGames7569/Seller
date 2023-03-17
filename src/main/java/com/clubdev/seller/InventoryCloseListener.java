package com.clubdev.seller;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.item.Item;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InventoryCloseListener implements Listener {
    private Seller main;

    public InventoryCloseListener(Seller main) {
        this.main = main;
    }
    @EventHandler
    protected void InventoryCloseListener(InventoryCloseEvent e) throws IOException {
        int default_price = 1;
        int money = 0;
        Player player = e.getPlayer();
            JSONObject pricelist = new JSONObject(Files.readAllBytes(Paths.get(main.getPriceList())));
            for(Item item : e.getInventory().getContents().values()) {
                if(pricelist.isNull(item.getNamespaceId())) {money = money + default_price;}
                money = money + pricelist.getInt(item.getNamespaceId());
            }
            player.sendMessage("У вас: " + money + " деняк!");
    }
}
