package com.speedyg.ras.events;

import java.util.Date;

import com.speedyg.ras.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.citem.Items;

public class Events implements Listener {

    private BasicAnimals main;

    public Events() {
        this.main = BasicAnimals.getInstance();
    }

    public void registerEvents() {
        this.main.getServer().getPluginManager().registerEvents(this, main);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    private void onClickMob(PlayerInteractAtEntityEvent e) {
        if (e.isCancelled())
            return;
        if (e.getRightClicked() != null && e.getHand() != null) {
            if (e.getRightClicked().getType().equals(EntityType.CHICKEN)
                    || e.getRightClicked().getType().equals(EntityType.COW)
                    || e.getRightClicked().getType().equals(EntityType.PIG)
                    || e.getRightClicked().getType().equals(EntityType.SHEEP)
                    || e.getRightClicked().getType().equals(EntityType.LLAMA)
                    || e.getRightClicked().getType().equals(EntityType.PARROT)
                    || e.getRightClicked().getType().equals(EntityType.MUSHROOM_COW)
                    || e.getRightClicked().getType().equals(EntityType.RABBIT)
                    || e.getRightClicked().getType().equals(EntityType.WOLF)
                    || e.getRightClicked().getType().equals(EntityType.POLAR_BEAR)
					|| e.getRightClicked().getType().equals(EntityType.HORSE)) {
                if (e.getHand().equals(EquipmentSlot.HAND)) {
                    if (!BasicAnimals.getInstance().isAdopted(e.getRightClicked())) {
                        if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().hasItemMeta()) {
                            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null
                                    && e.getPlayer().getItemInHand().getItemMeta().getLore() != null) {
                                if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                                        .equals(Items.getRentPaper().getItemMeta().getDisplayName())
                                        || e.getPlayer().getItemInHand().getItemMeta().getLore()
                                        .equals(Items.getRentPaper().getItemMeta().getLore())) {
                                    e.getPlayer().getInventory().removeItem(Items.getRentPaper());
                                    PlayerAdoptEvent event = new PlayerAdoptEvent(e.getPlayer().getWorld(),
                                            e.getPlayer(), e.getRightClicked().getType(),
                                            e.getRightClicked().getLocation());
                                    e.getRightClicked().remove();
                                    Bukkit.getServer().getPluginManager().callEvent(event);
                                    return;

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void adoptEvent(PlayerAdoptEvent e) {
        if (e.isCancelled())
            return;
        Date date = new Date();
        switch (e.getEntityType()) {
            case COW:
                AdoptedAnimal cow = new AdoptedCow(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                cow.createMob();
                break;
            case LLAMA:
                AdoptedAnimal lama = new AdoptedLLama(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                lama.createMob();
                break;
            case PIG:
                AdoptedAnimal pig = new AdoptedPig(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                pig.createMob();
                break;
            case SHEEP:
                AdoptedAnimal sheep = new AdoptedSheep(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                sheep.createMob();
                break;
            case CHICKEN:
                AdoptedAnimal chicken = new AdoptedChicken(e.getCraftWorld(), e.getPlayer(), e.getLocation(),
                        date.getTime());
                chicken.createMob();
                break;
            case WOLF:
                AdoptedAnimal wolf = new AdoptedWolf(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                wolf.createMob();
                break;
            case RABBIT:
                AdoptedAnimal rabbit = new AdoptedRabbit(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                rabbit.createMob();
                break;
            case PARROT:
                AdoptedAnimal parrot = new AdoptedParrot(e.getCraftWorld(), e.getPlayer(), e.getLocation(), date.getTime());
                parrot.createMob();
                break;
            case MUSHROOM_COW:
                AdoptedAnimal mushroom = new AdoptedMooshroom(e.getCraftWorld(), e.getPlayer(), e.getLocation(),
                        date.getTime());
                mushroom.createMob();
                break;
            case POLAR_BEAR:
                AdoptedAnimal bear = new AdoptedPolarBear(e.getCraftWorld(), e.getPlayer(), e.getLocation(),
                        date.getTime());
                bear.createMob();
                break;
            default:
                break;
        }

    }

}
