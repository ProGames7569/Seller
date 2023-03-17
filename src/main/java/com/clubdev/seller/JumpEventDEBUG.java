package com.clubdev.seller;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJumpEvent;
import cn.nukkit.item.Item;

public class JumpEventDEBUG implements Listener {
    @EventHandler
    public void JumpEventDEBUG(PlayerJumpEvent e) {
        Item item = Item.get(Item.DIAMOND_BOOTS);
        item.setDamage(215);
        e.getPlayer().giveItem(item);
    }
}
