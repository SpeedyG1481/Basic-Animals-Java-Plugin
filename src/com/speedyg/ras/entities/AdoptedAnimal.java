package com.speedyg.ras.entities;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.nms.CustomEntities;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAnimals;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.UUID;

public class AdoptedAnimal extends EntityAnimal implements Listener {

    private Player owner;
    private String ownerName;
    private float speed = 0.33F;
    private UUID ouuid = null;
    private Location firstSpawnLoc;
    private int followRange = 10;
    private int tpRange = followRange * 4;
    private AdoptedType type;
    private long firsSpawnTime;
    private boolean follow = true;
    private boolean isSelling = false;
    private float sellPrice = 0.0F;
    private float maxspeed = 0.65F;
    private AdoptedAnimal adoptedAnimal = this;

    public AdoptedAnimal(World world, Player p, Location loc, AdoptedType type, long firstSpawn) {
        super(world);
        this.owner = p;
        this.ouuid = p.getUniqueId();
        this.ownerName = p.getName();
        this.firstSpawnLoc = loc;
        this.firsSpawnTime = firstSpawn;
        this.type = type;
        Bukkit.getServer().getPluginManager().registerEvents(this, BasicAnimals.getInstance());
        this.rideEntity();
    }

    public boolean isSelling() {
        return this.isSelling;
    }

    public void setSelling(boolean xx) {
        this.isSelling = xx;
    }

    public float getPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(float f) {
        this.sellPrice = f;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(double d) {
        if (d < maxspeed && d > 0.15F)
            this.speed = (float) d;
    }

    public Player getOwner() {
        return this.owner;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwner(Player p) {
        this.owner = p;
        this.ouuid = p.getUniqueId();
        this.ownerName = p.getName();
    }

    public AdoptedType getAdoptType() {
        return this.type;
    }

    public UUID getOwnerUUID() {
        return this.ouuid;
    }

    public int getFollowRange() {
        return this.followRange;
    }

    public int getTPRange() {
        return this.tpRange;
    }

    public void setFollowRange(int r) {
        if ((r > 0) && (r < 102)) {
            this.followRange = r;
            this.tpRange = this.followRange * 4;
        }
    }

    public Location getFirstSpawnLoc() {
        return this.firstSpawnLoc;
    }

    public void setFirstSpawnLoc(Location loc) {
        this.firstSpawnLoc = loc;
    }

    public void createMob() {
        this.setHealth(BasicAnimals.getInstance().getConfig().getInt("Options.First-Spawn-Health") > 0
                ? BasicAnimals.getInstance().getConfig().getInt("Options.First-Spawn-Health")
                : 20);
        this.setCustomName(BasicAnimals.getInstance().getConfig().getString("Options.First-Spawn-Displayname")
                .replaceAll("&", "§")
                .replaceAll("%t",
                        this.getAdoptType().getLocaleName(BasicAnimals.getInstance().getConfig().getString("Locale"))
                                .substring(0, 1).toUpperCase()
                                + this.getAdoptType()
                                .getLocaleName(BasicAnimals.getInstance().getConfig().getString("Locale"))
                                .substring(1))
                .replaceAll("%p", this.getOwner().getName()));
        this.setCustomNameVisible(true);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        this.setPosition(this.getFirstSpawnLoc().getX() + 1, this.getFirstSpawnLoc().getY(),
                this.getFirstSpawnLoc().getZ() + 1);
        this.getWorld().addEntity(this, SpawnReason.CUSTOM);
        CustomEntities.liveMobs.add(this);
        BasicAnimals.getInstance().saveEntity(this.getOwner(), this);
    }

    public void followPlayer(Player player, LivingEntity entity) {
        LivingEntity e = entity;
        Player p = player;
        if (this.follow) {
            if ((getBukkitEntity() != null) && (!getBukkitEntity().isDead()) && (p != null)) {
                if ((p.getLocation().distance(getBukkitEntity().getLocation()) > getFollowRange())
                        && (p.getLocation().distance(getBukkitEntity().getLocation()) < getTPRange())) {
                    ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX() + 1.75D,
                            p.getLocation().getY(), p.getLocation().getZ() + 1.75D, this.getSpeed());
                } else if ((p.getLocation().distance(getBukkitEntity().getLocation()) >= getTPRange())
                        && (getOwner().isOnGround())) {
                    getBukkitEntity().teleport(getOwner().getLocation());
                }
            }
        }
    }


    private void rideEntity() {
        new BukkitRunnable() {
            public void run() {
                if (bukkitEntity != null) {
                    if (!bukkitEntity.isDead()) {
                        if (getOwner() != null) {
                            if (getOwner().isOnline()) {
                                if (passengers != null) {
                                    if (getBukkitEntity().getPassengers().contains(getOwner())) {
                                        EntityLiving passenger = ((CraftPlayer) getOwner()).getHandle();
                                        yaw = getOwner().getLocation().getYaw();
                                        lastYaw = yaw;
                                        pitch = (getOwner().getLocation().getPitch() * 0.5F);

                                        float sagsol = passenger.be * getSpeed();
                                        float asayukari = passenger.bg * getSpeed();
                                        Field jump = null;
                                        try {
                                            jump = EntityLiving.class.getDeclaredField("bd");
                                        } catch (NoSuchFieldException e1) {
                                            e1.printStackTrace();
                                        } catch (SecurityException e1) {
                                            e1.printStackTrace();
                                        }
                                        jump.setAccessible(true);
                                        try {
                                            if (!getAdoptType().equals(AdoptedType.PARROT)) {
                                                if (jump.getBoolean(passenger) && getBukkitEntity().isOnGround()) {
                                                    motY = 0.6F;
                                                }
                                            } else {
                                                if (jump.getBoolean(passenger)
                                                        && (getBukkitEntity().getLocation().getBlockY() - getBukkitEntity().getWorld()
                                                        .getHighestBlockAt(getBukkitEntity().getLocation()).getY() <= 15)) {
                                                    motY = 0.43F;
                                                }
                                            }
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }


                                        // --------------------

                                        float f4 = sagsol * sagsol + asayukari * asayukari;
                                        if (f4 >= 1.0E-4F) {
                                            f4 = MathHelper.c(f4);
                                            if (f4 < 1.0F) {
                                                f4 = 1.0F;
                                            }

                                            f4 = getSpeed() / f4;
                                            sagsol *= f4;
                                            asayukari *= f4;
                                            float f5 = MathHelper.sin(yaw * 0.017453292F);
                                            float f6 = MathHelper.cos(yaw * 0.017453292F);


                                            if (getBukkitEntity().isOnGround()) {
                                                motX += (double) (sagsol * f6 - asayukari * f5);
                                                motZ += (double) (asayukari * f6 + sagsol * f5);

                                            } else {
                                                motX += (double) (sagsol * f6 / 2.12 - asayukari * f5 / 2.12) / 2.22;
                                                motZ += (double) (asayukari * f6 / 2.12 + sagsol * f5 / 2.12) / 2.22;
                                            }
                                        }
                                        //------------------
                                    } else {
                                        followPlayer(getOwner(), (LivingEntity) getBukkitEntity());
                                    }
                                } else {
                                    followPlayer(getOwner(), (LivingEntity) getBukkitEntity());
                                }

                                if (bukkitEntity.getLocation().getY() <= 0) {
                                    getBukkitEntity().teleport(getOwner().getLocation());
                                }
                            } else {
                                CustomEntities.sendHome(adoptedAnimal);
                                cancel();
                            }
                        } else {
                            CustomEntities.sendHome(adoptedAnimal);
                            cancel();
                        }
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(BasicAnimals.getInstance(), 0L, 1L);

    }

	/*@Override
	protected float g(float sagsol, float asayukari) {
		if (this.passengers != null) {
			if (getBukkitEntity().getPassengers().contains(getOwner())) {
				EntityLiving passenger = ((CraftPlayer) getOwner()).getHandle();
				this.yaw = this.getOwner().getLocation().getYaw();
				this.lastYaw = this.yaw;
				this.pitch = (this.getOwner().getLocation().getPitch() * 0.5F);

				sagsol = passenger.be * getSpeed();
				asayukari = passenger.bg * getSpeed();
				Field jump = null;
				try {
					jump = EntityLiving.class.getDeclaredField("bd");
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
				jump.setAccessible(true);
				try {
					if (!this.getAdoptType().equals(AdoptedType.PARROT)) {
						if (jump.getBoolean(passenger) && this.getBukkitEntity().isOnGround()) {
							this.motY = 0.6F;
						}
					} else {
						if (jump.getBoolean(passenger)
								&& (this.getBukkitEntity().getLocation().getBlockY() - this.getBukkitEntity().getWorld()
										.getHighestBlockAt(this.getBukkitEntity().getLocation()).getY() <= 15)) {
							this.motY = 0.43F;
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (this.getBukkitEntity().isOnGround()) {
					super.b(sagsol, 0.0F, asayukari, getSpeed());
				} else {
					super.b(sagsol / 2, 0.0F, asayukari / 2, getSpeed() / 2);
				}
			} else {
				followPlayer(getOwner(), (LivingEntity) getBukkitEntity());
			}
		} else {
			followPlayer(getOwner(), (LivingEntity) getBukkitEntity());
		}

		if (this.bukkitEntity.getLocation().getY() <= 0) {
			getBukkitEntity().teleport(getOwner().getLocation());
		}
		return 0.0F;
	}*/

    public static boolean isAdopted(Entity e) {
        if ((e instanceof CraftAnimals)) {
            EntityAnimal c = ((CraftAnimals) e).getHandle();
            if (c.getClass().getName().contains("Adopted")) {
                return true;
            }
        }
        return false;
    }

    public long getFirstSpawnDate() {
        return this.firsSpawnTime;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean getFollow() {
        return this.follow;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable arg0) {
        return null;
    }

}
