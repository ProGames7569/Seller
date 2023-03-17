package com.clubdev.seller;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import java.util.Arrays;

public class Seller extends PluginBase {
    @Getter String priceList;

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();
        this.priceList = "/pricelist.json";
        getServer().getCommandMap().registerAll("Seller", Arrays.asList(
            new SellCommand()
        ));
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this), this);
    }
}

