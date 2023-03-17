package com.clubdev.seller;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InventoryCloseListener implements Listener {
    private Seller main;

    public InventoryCloseListener(Seller main) {
        this.main = main;
    }
    @EventHandler
    protected void InventoryCloseListener(InventoryCloseEvent e) throws IOException {
        if(e.getInventory().getTitle().equals("Скупщик")) {
            int default_price = 1;
            int money = 0;
            Player player = e.getPlayer();
            InputStream is = InventoryCloseListener.class.getResourceAsStream(main.getPriceList());
            if (is == null) {
                throw new NullPointerException("Cannot find resource file ");
            }
            JSONTokener tokener = new JSONTokener(is);
            JSONObject pricelist = new JSONObject(tokener);
            for(Item item : e.getInventory().getContents().values()) {
                try {
                    if(item.isTool()) {
                        money = money + Math.round(((pricelist.getInt(item.getNamespaceId())) * ((float) item.getDamage() / (float) item.getMaxDurability())));
                    } else {
                        money = money + pricelist.getInt(item.getNamespaceId()) * item.getCount();
                    }
                } catch (JSONException ee) {
                    money = money + default_price * item.getCount();
                }
            }
            player.sendMessage("У вас: " + money + " деняк!");
        }
    }
}
