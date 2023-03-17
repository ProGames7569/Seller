package com.clubdev.seller.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import com.clubdev.seller.Seller;

public class SellCommand extends Command {

    private Seller main;

    public SellCommand(Seller main) {
        super("sell", "Открыть меню скупщика");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(sender instanceof Player player) {
            main.sendMainForm(player);
            return true;
        }

        return false;
    }

    
}
