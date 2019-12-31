package com.speedyg.ras.entities;

import com.speedyg.ras.nms.CustomEntities;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAnimals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.menu.MobEditMenu;
import com.speedyg.ras.messages.Messages;

import net.minecraft.server.v1_12_R1.World;

public class AdoptedSheep extends AdoptedAnimal {

	public AdoptedSheep(World world, Player owner, Location loc, long firstSpawnTime) {
		super(world, owner, loc, AdoptedType.SHEEP, firstSpawnTime);
	}

	@EventHandler
	private void entityRightClickEvent(PlayerInteractAtEntityEvent e) {
		if (this.getBukkitEntity() != null) {
			if (e.getRightClicked() != null && e.getHand() != null) {
				if (e.getHand().equals(EquipmentSlot.HAND)) {
					if (BasicAnimals.getInstance().isAdopted(e.getRightClicked())) {
						if (e.getRightClicked().equals(this.getBukkitEntity())) {
							e.setCancelled(true);
							if (e.getPlayer().equals(this.getOwner())) {
								if (e.getPlayer().isSneaking()) {
									MobEditMenu menu = new MobEditMenu(BasicAnimals.getInstance(), this.getOwner(),
											this);
									menu.openMenu();
								} else {
									this.getBukkitEntity().addPassenger(e.getPlayer());
								}
							} else {
								e.getPlayer().sendMessage(Messages.not_your_mob);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	private void entityDamageEvent(EntityDamageByEntityEvent e) {
		if (this.getBukkitEntity() != null)
			if (e.getEntity() != null) {
				if (e.getDamager() instanceof Player) {
					Player damager = (Player) e.getDamager();
					if (e.getEntity().equals(this.getBukkitEntity())) {
						if (!damager.equals(this.getOwner()) && !damager.isOp()) {
							e.setCancelled(true);
							damager.sendMessage(Messages.not_your_mob);
						}
					}
				}
			}
	}

	@EventHandler
	private void entityDamagedEvent(EntityDamageEvent e) {
		if (this.getBukkitEntity() != null)
			if (e.getEntity().equals(this.getBukkitEntity())) {
				if (!e.getCause().equals(DamageCause.ENTITY_ATTACK)) {
					e.setCancelled(true);
				}
			}
	}

	@EventHandler
	private void deathEvent(EntityDeathEvent e) {
		if (e.getEntity() != null) {
			if (BasicAnimals.getInstance().isAdopted(e.getEntity()))
				if (e.getEntity().getUniqueId().equals(this.bukkitEntity.getUniqueId())) {
					CustomEntities.sendHome((AdoptedAnimal) ((CraftAnimals) e.getEntity()).getHandle());
				}
		}
	}

}