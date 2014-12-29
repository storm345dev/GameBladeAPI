package org.stormdev.gbapi.storm.skulls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.stormdev.gbapi.storm.misc.Reflect;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class CustomPlayerHeads {
	private static final Random random = new Random();
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Method getWorldHandle;
    private static Method getWorldTileEntity;
    private static Method setGameProfile;

    public static void onEnable() {
        if (getWorldHandle == null || getWorldTileEntity == null || setGameProfile == null) {
            try {
                getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
                getWorldTileEntity = getMCClass("WorldServer").getMethod("getTileEntity", int.class, int.class, int.class);
                setGameProfile = getMCClass("TileEntitySkull").getMethod("setGameProfile", GameProfile.class);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    // Example
    //@EventHandler(ignoreCancelled=true)
    //public void on(PlayerInteractEvent event) {
    //    if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SKULL)
    //        setSkullWithNonPlayerProfile("http://213.136.94.170/downloads/img/skin.png", true, event.getClickedBlock());
    //}

    // Method
    public static void setSkullWithNonPlayerProfile(String skinURL, boolean randomName, Block skull) {
        if(skull.getType() != Material.SKULL)
            throw new IllegalArgumentException("Block must be a skull.");
        Skull s = (Skull) skull.getState();
        try {
            setSkullProfile(s, getNonPlayerProfile(skinURL, randomName));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
    }
    
    // Method
    public static void setSkullWithNonPlayerProfile(String skinURL, boolean randomName, ItemStack skull) {
        if(skull.getType() != Material.SKULL_ITEM)
            throw new IllegalArgumentException("Block must be a skull.");
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        Class<?> skullClass = Reflect.getCBClass("inventory.CraftMetaSkull");
        if(!skullClass.isInstance(sm)){
        	throw new IllegalArgumentException("SkullItemMeta not an instance of CraftMetaSkull!");
        }
        try {
			Field f = skullClass.getField("profile");
			f.setAccessible(true);
			f.set(sm, getNonPlayerProfile(skinURL, randomName));
			skull.setItemMeta(sm);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // Method
    public static void setSkullWithNonPlayerProfile(String skinURL, boolean randomName, Skull skull) {
        try {
            setSkullProfile(skull, getNonPlayerProfile(skinURL, randomName));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
    }

    // Method
    private static void setSkullProfile(Skull skull, GameProfile someGameprofile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object world = getWorldHandle.invoke(skull.getWorld());
        Object tileSkull = getWorldTileEntity.invoke(world, skull.getX(), skull.getY(), skull.getZ());
        setGameProfile.invoke(tileSkull, someGameprofile);
    }

    // Method
    public static GameProfile getNonPlayerProfile(String skinURL, boolean randomName) {
        GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), randomName ? getRandomString(16) : null);
        newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")));
        return newSkinProfile;
    }

    // Example
    public static String getRandomString(int length) {
        StringBuilder b = new StringBuilder(length);
        for(int j = 0; j < length; j++)
            b.append(chars.charAt(random.nextInt(chars.length())));
        return b.toString();
    }

    // Refletion
    private static Class<?> getMCClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    // Reflection
    private static Class<?> getCraftClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
