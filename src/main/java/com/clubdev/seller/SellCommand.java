package com.clubdev.seller;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.InventoryType;
import me.iwareq.fakeinventories.CustomInventory;
import ru.contentforge.formconstructor.form.SimpleForm;
import java.io.IOException;

public class SellCommand extends Command {
    public SellCommand() {
        super("sell", "Открыть меню скупщика");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(sender instanceof Player player) {
            sendMainForm(player);
            return true;
        }

        return false;
    }

    private void sendMainForm(Player player) {
        new SimpleForm().setTitle("Скупщик")
                .setContent("Вах брат, покупаю любой товар, цена потом")
                .addButton("Продать предметы", (p, b) -> {
                    try {
                        sendBuyingUI(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .send(player);
    }

    private void sendBuyingUI(Player player) throws IOException {
        CustomInventory inventory = new CustomInventory(InventoryType.CHEST, "Скупщик");
        player.addWindow(inventory);
    }
}
