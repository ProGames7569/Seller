package com.clubdev.seller;

import cn.nukkit.Player;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.MinecraftItemID;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import me.iwareq.fakeinventories.CustomInventory;
import ru.contentforge.formconstructor.form.SimpleForm;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.json.JSONObject;

import com.clubdev.seller.commands.SellCommand;
import com.clubdev.seller.listeners.EventListener;

public class Seller extends PluginBase {

    @Getter String priceList;

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();
        this.priceList = "/pricelist.json";
        this.getServer().getCommandMap().registerAll("Seller", Arrays.asList(
            new SellCommand(this)
        ));
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public void sendMainForm(Player player) {
        new SimpleForm("Скупщик")
            .setContent("Вах брат, покупаю любой товар!")
            .addButton("Продать предметы", (pl, b) -> {
                try {
                    this.sendBuyingUI(pl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .addButton("Расценки", (pl, b) -> {
                this.sendPriceListForm(pl);
            })
            .send(player);
    }

    public void sendPriceListForm(Player player) {
        SimpleForm form = new SimpleForm("Скупщик");
        form.addContent("Все расценки на товары:\n");
        try {
            FileReader reader = new FileReader(new File(priceList));
            JSONObject jsonObject = new JSONObject(reader);

            String[] keys = JSONObject.getNames(jsonObject);
            
            for (String key : keys) {
                String value = jsonObject.getString(key);
                form.addContent(MinecraftItemID.getByNamespaceId(key).get(0).getName() + " §7-§e " + value + "\n");
            }
        } catch (IOException e) {
            form.addContent("§cНе удалось загрузить список.");
        }
        

        form.addButton("Продать предметы", (p, b) -> {
                try {
                    sendBuyingUI(p);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .send(player);
    }

    public void sendBuyingUI(Player player) throws IOException {
        CustomInventory inventory = new CustomInventory(InventoryType.CHEST, "Скупщик");
        player.addWindow(inventory);
    }

    public static int calculatePrice(int durability, int maxDurability, int price) {
        double durabilityRatio = (double) durability / maxDurability;
        int calculatedPrice = (int) (durabilityRatio * price);
        return calculatedPrice;
    }
}

