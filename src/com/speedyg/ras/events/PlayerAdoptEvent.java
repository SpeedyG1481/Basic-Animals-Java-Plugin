package com.speedyg.ras.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAdoptEvent extends Event implements Cancellable {

	private boolean isCancelled;
	private Player p;
	private EntityType e;
	private net.minecraft.server.v1_12_R1.World world;
	private World w;
	private Location loc;
	private static final HandlerList handlers = new HandlerList();

	public PlayerAdoptEvent(World world, Player p, EntityType e, Location loc) {
		this.p = p;
		this.e = e;
		this.loc = loc;
		this.w = world;
		this.world = ((CraftWorld) world).getHandle();
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;

	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public EntityType getEntityType() {
		return e;
	}

	public Location getLocation() {
		return this.loc;
	}

	public Player getPlayer() {
		return p;
	}

	public net.minecraft.server.v1_12_R1.World getCraftWorld() {
		return this.world;
	}

	public World getWorld() {
		return this.w;
	}

}
