package me.destroy.main;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public final class Main extends JavaPlugin {

    public List<UUID> troopBombers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        for(UUID uuid:troopBombers){
            Entity entity = Bukkit.getEntity(uuid);
            if(entity == null)return;
            entity.remove();
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e){
        Entity mob = e.getEntity();
        if(!(mob instanceof LivingEntity))return;
        Random random = new Random();
        int chance = random.nextInt(1,100);

        Location loc = mob.getLocation();
        if(mob instanceof Phantom){
            if(chance > 2)return;
            troopsBomber(loc,30);
            return;
        }
        if(mob instanceof PigZombie){
            if(chance > 2)return;
            //troopsBomber()
            return;
        }
        if(mob instanceof Zombie){
            if(chance > 2)return;
            //troopsBomber()
            return;
        }
        if(mob instanceof Skeleton){
            if(chance > 2)return;
            //troopsBomber()
            return;
        }
        if(mob instanceof Piglin){
            if(chance > 2)return;
            //troopsBomber()
            return;
        }
    }

    @EventHandler
    public void onMobTargetMob(EntityTargetLivingEntityEvent e){
        LivingEntity target = e.getTarget();
        if(target instanceof Player)return;
        Entity mob = e.getEntity();
        if(!(mob instanceof LivingEntity))return;
        if(target == null)return;
        if(mob.isCustomNameVisible() && target.isCustomNameVisible()){
            e.setCancelled(true);
        }
    }


    public void troopsBomberLoop(Phantom mob){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Location loc = mob.getLocation();
                Random random = new Random();

                String[] name = mob.customName().toString().split(":");
                int count = Integer.getInteger(name[1]);
                count--;
                mob.customName(Component.text("Troops Bomber: ",NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false).append(Component.text("[" + count + "]",NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)));
                mob.setSize(count);
                if(count == 0)mob.remove();

                switch (random.nextInt(0, 1)) {
                    case 0 -> summonTroopsOption1(random.nextInt(1, 3), loc);
                    case 1 -> summonTroopsOption2(random.nextInt(1, 3), loc);
                }
            }
        },15*1000);
    }

    public void summonTroopsOption1(int troopCount,Location loc){
        for(int i = 0;i<troopCount;i++){
            zombieMaster(loc);
            for(int j = 0;j<2;j++){
                skeletonWarrior(loc);
            }
        }
    }
    public void summonTroopsOption2(int troopCount,Location loc){
        for(int i = 0;i<troopCount;i++){
            skeletonMaster(loc);
            for(int j = 0;j<2;j++){
                zombieWarrior(loc);
            }
        }
    }
    public void summonArmy(int troopCount,Location loc){
        Random random = new Random();
        for(int i = 0;i<troopCount;i++){
            Location newLoc = loc;
            newLoc.setX(newLoc.getX() + random.nextInt(1,10));
            newLoc.setZ(newLoc.getZ() + random.nextInt(1,10));
            zombieMaster(newLoc);
        }
        for(int i = 0;i<troopCount;i++){
            Location newLoc = loc;
            newLoc.setX(newLoc.getX() + random.nextInt(1,10));
            newLoc.setZ(newLoc.getZ() + random.nextInt(1,10));
            skeletonMaster(newLoc);
        }
    }

    public static int Max = Integer.MAX_VALUE/20;

    public static PotionEffect Strength(int seconds, int modifier){
        return new PotionEffect(PotionEffectType.INCREASE_DAMAGE,seconds*20,modifier-1);
    }
    public static PotionEffect Speed(int seconds, int modifier){
        return new PotionEffect(PotionEffectType.SPEED,seconds*20,modifier-1);
    }
    public static PotionEffect InstantDamage(int seconds, int modifier){
        return new PotionEffect(PotionEffectType.HARM,seconds*20,modifier-1);
    }
    public static PotionEffect Regen(int seconds, int modifier){
        return new PotionEffect(PotionEffectType.REGENERATION,seconds*20,modifier-1);
    }

    public static void zombieWarrior(Location loc){
        Zombie mob = loc.getWorld().spawn(loc,Zombie.class);
        mob.customName(Component.text("Zombie Warrior", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false));
        mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        mob.setCustomNameVisible(true);

        mob.addPotionEffect(Strength(Max,2));
        mob.addPotionEffect(Speed(Max,2));
        mob.addPotionEffect(InstantDamage(3,10));
    }
    public static void skeletonWarrior(Location loc){
        Skeleton mob = loc.getWorld().spawn(loc,Skeleton.class);
        mob.customName(Component.text("Skeleton Warrior", NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false));
        mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        mob.setCustomNameVisible(true);

        mob.addPotionEffect(Strength(Max,2));
        mob.addPotionEffect(Speed(Max,2));
        mob.addPotionEffect(InstantDamage(3,10));
    }
    public static void zombieMaster(Location loc){
        Zombie mob = loc.getWorld().spawn(loc,Zombie.class);
        mob.customName(Component.text("Zombie Master", NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC,false));
        mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        mob.setCustomNameVisible(true);

        mob.addPotionEffect(Strength(Max,2));
        mob.addPotionEffect(Speed(Max,2));
        mob.addPotionEffect(InstantDamage(3,10));
    }
    public static void skeletonMaster(Location loc){
        Skeleton mob = loc.getWorld().spawn(loc,Skeleton.class);
        mob.customName(Component.text("Skeleton Master", NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC,false));
        mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        mob.setCustomNameVisible(true);

        mob.addPotionEffect(Strength(Max,2));
        mob.addPotionEffect(Speed(Max,2));
        mob.addPotionEffect(InstantDamage(3,10));
    }
    public void troopsBomber(Location loc,int count){
        Phantom mob = loc.getWorld().spawn(loc,Phantom.class);
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
        mob.customName(Component.text("Troops Bomber: ",NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false).append(Component.text("[" + count + "]",NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)));
        mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        mob.setShouldBurnInDay(false);
        mob.setSize(count);
        troopsBomberLoop(mob);
        troopBombers.add(mob.getUniqueId());
    }

}
