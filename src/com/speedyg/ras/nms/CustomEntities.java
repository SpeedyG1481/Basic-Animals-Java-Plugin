package com.speedyg.ras.nms;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.speedyg.ras.entities.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.speedyg.ras.BasicAnimals;
import com.speedyg.ras.appends.Files;
import com.speedyg.ras.messages.Messages;

public enum CustomEntities {

    COW("adopted_cow", 92, EntityType.COW, EntityCow.class, AdoptedCow.class),
    SHEEP("adopted_sheep", 91, EntityType.SHEEP, EntitySheep.class, AdoptedSheep.class),
    PIG("adopted_pig", 90, EntityType.PIG, EntityPig.class, AdoptedPig.class),
    CHICKEN("adopted_chicken", 93, EntityType.CHICKEN, EntityChicken.class, AdoptedChicken.class),
    LLAMA("adopted_llama", 103, EntityType.LLAMA, EntityLlama.class, AdoptedLLama.class),
    MOOSHROOM("adopted_mushroomcow", 96, EntityType.MUSHROOM_COW, EntityMushroomCow.class, AdoptedMooshroom.class),
    RABBIT("adopted_rabbit", 101, EntityType.RABBIT, EntityRabbit.class, AdoptedRabbit.class),
    WOLF("adopted_wolf", 95, EntityType.WOLF, EntityWolf.class, AdoptedWolf.class),
    PARROT("adopted_parrot", 105, EntityType.PARROT, EntityParrot.class, AdoptedParrot.class),
    POLAR_BEAR("adopted_pbear", 102, EntityType.POLAR_BEAR, EntityPolarBear.class, AdoptedPolarBear.class);

    public static List<AdoptedAnimal> liveMobs = new ArrayList<AdoptedAnimal>();
    private String name;
    private int id;

    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends Entity> customClass;
    private MinecraftKey key;
    private MinecraftKey oldKey;

    private CustomEntities(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass,
                           Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
        this.oldKey = EntityTypes.b.b(nmsClass);
    }

    public static void registerEntities() {
        for (CustomEntities ce : CustomEntities.values())
            ce.register();
    }

    public static void unregisterEntities() {
        for (CustomEntities ce : CustomEntities.values())
            ce.unregister();
    }

    public static void removeAllEntities() {
        for (AdoptedAnimal a : liveMobs) {
            if (a.getBukkitEntity() != null) {
                a.getBukkitEntity().remove();
            }
        }
        liveMobs.clear();
    }

    private void register() {
        EntityTypes.d.add(key);
        EntityTypes.b.a(id, key, customClass);
    }

    private void unregister() {
        EntityTypes.d.remove(key);
        EntityTypes.b.a(id, oldKey, nmsClass);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }

    public static boolean isHired(File f) {
        for (AdoptedAnimal a : liveMobs) {
            if (Files.findMobFile(a) != null) {
                if (Files.findMobFile(a).equals(f)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static AdoptedAnimal getHired(File f) {
        for (AdoptedAnimal a : liveMobs) {
            if (Files.findMobFile(a) != null)
                if (Files.findMobFile(a).equals(f))
                    return a;
        }
        return null;
    }

    public static void sendHome(AdoptedAnimal e) {
        e.getBukkitEntity().remove();
        liveMobs.remove(e);
    }

    public static void hire(Player p, File f) {
        AdoptedType t = null;
        AdoptedAnimal mob = null;
        JSONObject jo = null;
        try {
            FileReader reader = new FileReader(f);
            JSONParser oop = new JSONParser();
            jo = (JSONObject) oop.parse(reader);
            reader.close();
        } catch (Exception e) {

        }

        String name = (String) jo.get("mob-Name");
        Player owner = Bukkit.getPlayer(UUID.fromString((String) jo.get("owner-UUID")));
        float price = (float) (double) jo.get("sell-Price");
        float speed = (float) (double) jo.get("last-Speed");
        t = AdoptedType.valueOf((String) jo.get("mob-Type"));
        long firstOwningDate = (long) jo.get("first-OwningDate");
        boolean isSelling = (boolean) jo.get("is-Selling");
        int followRange = (int) (long) jo.get("last-FollowRange");

        switch (t) {
            case COW:
                mob = new AdoptedCow(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case LLAMA:
                mob = new AdoptedLLama(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case PIG:
                mob = new AdoptedPig(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case SHEEP:
                mob = new AdoptedSheep(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case CHICKEN:
                mob = new AdoptedChicken(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case WOLF:
                mob = new AdoptedWolf(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case RABBIT:
                mob = new AdoptedRabbit(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case PARROT:
                mob = new AdoptedParrot(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(), firstOwningDate);
                mob.createMob();
                break;
            case MOOSHROOM:
                mob = new AdoptedMooshroom(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(),
                        firstOwningDate);
                mob.createMob();
                break;
            case POLAR_BEAR:
                mob = new AdoptedPolarBear(((CraftWorld) p.getWorld()).getHandle(), owner, p.getLocation(),
                        firstOwningDate);
                mob.createMob();
                break;
            default:
                break;
        }

        mob.setCustomName(name.replaceAll("&", "§"));
        mob.setFollowRange(followRange);
        mob.setSelling(isSelling);
        mob.setSpeed(speed);
        mob.setSellPrice(price);
        BasicAnimals.getInstance().saveEntity(mob.getOwner(), mob);
        p.sendMessage(Messages.hire_success);
    }
}
