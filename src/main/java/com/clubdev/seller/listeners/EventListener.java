package com.clubdev.seller.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.item.Item;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.clubdev.seller.Seller;

import java.io.IOException;
import java.io.InputStream;

public class EventListener implements Listener {

    private Seller main;

    public EventListener(Seller main) {
        this.main = main;
    }

    @EventHandler
    protected void onCloseListener(InventoryCloseEvent event) throws IOException {
        if(event.getInventory().getTitle().equals("Скупщик")) {
            int default_price = 1;
            int money = 0;
            Player player = event.getPlayer();
            InputStream is = EventListener.class.getResourceAsStream(main.getPriceList());
            if (is == null) {
                throw new NullPointerException("Cannot find resource file ");
            }
            JSONTokener tokener = new JSONTokener(is);
            JSONObject pricelist = new JSONObject(tokener);
            for(Item item : event.getInventory().getContents().values()) {
                try {
                    if(item.isTool() || item.isArmor()) {
                        money = money + Seller.calculatePrice(item.getMaxDurability() - item.getDamage(), item.getMaxDurability(), pricelist.getInt(item.getNamespaceId()));
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
