package org.stormdev.gbapi.UUIDAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.stormdev.gbapi.core.APIProvider;
import org.stormdev.gbapi.storm.UUIDAPI.OfflineUUIDCallable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Use to retrive UUIDs
 *
 */
public class PlayerIDFinder {
	
	public static void main(String[] args){
		//test it
		
		System.out.println(retMojangID("storm345").id);
		System.out.println(retMojangID("storm345dev").id);
	}
	
	/**
	 * Get the Mojang UUID of a player, DO NOT use in the main bukkit thread
	 * 
	 * @param player The player
	 * @return The Mojang UUID of the player
	 */
	public static MojangID getMojangID(Player player){
		if(APIProvider.getAPI().isEntityUUIDsCorrect().isTrue()){
			UUID uid = player.getUniqueId();
			if(uid != null){
				String uuid = toUUIDString(uid);
				return new MojangID(player.getName(), uuid);
			}
		}
		if(Bukkit.isPrimaryThread()){
			throw new RuntimeException("Please DO NOT look up mojang IDs in the primary thread!");
		}
		if(player.hasMetadata("uuid")){
			Object o;
			try {
				o = player.getMetadata("uuid").get(0).value();
			} catch (Exception e) {
				o = "";
			}
			if(o instanceof MojangID){
				return (MojangID) o;
			}
			player.removeMetadata("uuid", Bukkit.getPluginManager().getPlugins()[0]);
		}
		MojangID mid = retMojangID(player.getName());
		player.setMetadata("uuid", new SimpleMeta(mid, Bukkit.getPluginManager().getPlugins()[0])); //Replace plugin with yours to use CORRECTLY, but it doesn't matter much
		
		try {
			org.stormdev.gbapi.UUIDAPI.UUIDLoadEvent evt1 = new org.stormdev.gbapi.UUIDAPI.UUIDLoadEvent(player, new org.stormdev.gbapi.UUIDAPI.PlayerIDFinder.MojangID(mid.getName(), mid.getID()), getAsUUID(mid.getID()));
			org.stormdev.gbapi.storm.UUIDAPI.UUIDLoadEvent evt2 = new org.stormdev.gbapi.storm.UUIDAPI.UUIDLoadEvent(player, new org.stormdev.gbapi.storm.UUIDAPI.PlayerIDFinder.MojangID(mid.getName(), mid.getID()), getAsUUID(mid.getID()));
			Bukkit.getPluginManager().callEvent(evt1);
			Bukkit.getPluginManager().callEvent(evt2);
		} catch (Exception e){
			e.printStackTrace();
		}
	/*	try {
			UUID id = getAsUUID(mid.getID());
			PlayerReflect.setPlayerUUID(player, id);
			if(!player.getUniqueId().toString().equals(id.toString())) {
				APIProvider.getAPI().getGBPlugin().getLogger().info("FAILED to set correct UUID for "+player.getName()+"! They're using UUID: "+player.getUniqueId());
			}
		} catch (Exception e) {
			APIProvider.getAPI().getGBPlugin().getLogger().info("FAILED to set correct UUID for "+player.getName()+"! They're using UUID: "+player.getUniqueId());
			e.printStackTrace();
			//Oh well
		}*/
		return mid;
	}
	
	public static void setMeta(Player player, MojangID mid){
		player.removeMetadata("uuid", APIProvider.getAPI().getGBPlugin());
		player.setMetadata("uuid", new SimpleMeta(mid, APIProvider.getAPI().getGBPlugin()));
	}
	
	/**
	 * Get the Mojang UUID of a player, DO NOT use in the main bukkit thread
	 * 
	 * @param player The player
	 * @return The Mojang UUID of the player
	 */
	public static MojangID getMojangID(String playername){
		Player pl = Bukkit.getPlayerExact(playername);
		if(pl != null){
			return getMojangID(pl);
		}
		return retMojangID(playername);
	}
	
	private static MojangID retMojangID(String playername){
		if(APIProvider.getAPI().isEntityUUIDsCorrect().isTrue()){
			OfflinePlayer op;
			try {
				op = Bukkit.getScheduler().callSyncMethod(APIProvider.getAPI().getGBPlugin(), new OfflineUUIDCallable(playername)).get();
			} catch (Exception e) {
				e.printStackTrace();
				op = null;
			}
			if(op != null){
				UUID uid = op.getUniqueId();
				if(uid != null){
					String uuid = toUUIDString(uid);
					return new MojangID(playername, uuid);
				}
			}
		}
		
		try {
			if(Bukkit.isPrimaryThread()){
				throw new RuntimeException("Please DO NOT look up mojang IDs in the primary thread!");
			}
		} catch (NullPointerException e1) {
			//Ran outside of bukkit
		}
		
		String id;
		
		try {
			id = FishBansMojangUUIDGet.getMojangAccountID(playername); //Null if not found, or unavailable
		} catch (Exception e) {
			id = null;
		}
		if(id == null || id.equalsIgnoreCase("null")){
			//System.out.println("FishBans was unable to provide mojang UUID for "+playername+"!");
			try {
				id = toUUIDString(UUIDFetcher.getUUIDOf(playername));
				
			} catch (Exception e) {
				id = null;
			}
			if(id != null){
				//System.out.println("Minecraft resolved UUID for "+playername);
			}
			if(id == null){ //Use Minecraft one
//				System.out.println("Minecraft was unable to provide mojang UUID for "+playername+"!");
				try {
					id = SwordPVPUUIDGet.getMojangAccountID(playername);
				} catch (Exception e) {
					id = null;
				}
				if(id != null){
//					System.out.println("SwordPVP resolved UUID for "+playername);
				}
				if(id == null){
//					System.out.println("SwordPVP was unable to provide mojang UUID for "+playername+"!");
					System.out.println("Nobody was unable to provide UUID for "+playername+"!");
					System.out.println("Assigning player random UUID for session...");
					id = toUUIDString(UUID.randomUUID());
				}
			}
		}
		return new MojangID(playername, id);
	}
	
	/**
	 * Get an MojangID.getId() as a UUID
	 * 
	 * @param id
	 * @return
	 */
	public static UUID getAsUUID(String id) {
	    return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
	}
	
	public static String toUUIDString(UUID id){
		return id.toString().replaceAll(Pattern.quote("-"), "");
	}
	
	/**
	 * Represents a Mojang ID
	 *
	 */
	public static class MojangID {
		private String id;
		private String name;
		public MojangID(String name, String id){
			this.name = name;
			this.id = id.replaceAll("-", "");
		}
		/**
		 * Get the UUID of the player
		 * 
		 * @return Returns the UUID
		 */
		public String getID(){
			return id;
		}
		/**
		 * Get the name of the player associated with the id
		 * 
		 * @return The name of the player
		 */
		public String getName(){
			return name;
		}
	}
	
	public static class PlayerReflect {
		public static void setPlayerUUID(Player player, UUID id){
			String NMSversion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage()
					.getName().replace(".", ",").split(",")[3];
			String CBversion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage()
					.getName().replace(".", ",").split(",")[3];
			
			// org.bukkit.craftbukkit.xx.entity.Entity
			// net.minecraft.server.Entity
			
			Class<?> nms = null;
			Class<?> cb = null;
			try {
				nms = Class.forName(NMSversion + ".Entity");
				cb = Class.forName(CBversion + ".entity.CraftEntity");
				Method getNMSFromCB = cb.getMethod("getHandle");
				Field uuid = nms.getField("uniqueID");
				getNMSFromCB.setAccessible(true);
				uuid.setAccessible(true);
				Object ce = cb.cast(player);
				Object nmsE = nms.cast(getNMSFromCB.invoke(ce));
				uuid.set(nmsE, id);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error: Failed to fake player UUID");
			}
		}
	}
}

class UUIDFetcher implements Callable<Map<String, UUID>> {
    private static final double PROFILES_PER_REQUEST = 100;
    private static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
    private final JsonParser jsonParser = new JsonParser();
    private final List<String> names;
    private final boolean rateLimiting;

    public UUIDFetcher(List<String> names, boolean rateLimiting) {
        this.names = ImmutableList.copyOf(names);
        this.rateLimiting = rateLimiting;
    }

    public UUIDFetcher(List<String> names) {
        this(names, true);
    }

    public Map<String, UUID> call() throws Exception {
        Map<String, UUID> uuidMap = new HashMap<String, UUID>();
        int requests = (int) Math.ceil(names.size() / PROFILES_PER_REQUEST);
        for (int i = 0; i < requests; i++) {
            HttpURLConnection connection = createConnection();
            List<String> s = names.subList(i * 100, Math.min((i + 1) * 100, names.size()));
            
            String body = new Gson().toJson(s);
            writeBody(connection, body);
            JsonArray array = (JsonArray) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
            for (Object profile : array) {
                JsonObject jsonProfile = (JsonObject) profile;
                String id = (String) jsonProfile.get("id").getAsString();
                String name = (String) jsonProfile.get("name").getAsString();
                UUID uuid = UUIDFetcher.getUUID(id);
                uuidMap.put(name, uuid);
            }
            if (rateLimiting && i != requests - 1) {
                Thread.sleep(100L);
            }
        }
        return uuidMap;
    }

    private static void writeBody(HttpURLConnection connection, String body) throws Exception {
        OutputStream stream = connection.getOutputStream();
        stream.write(body.getBytes());
        stream.flush();
        stream.close();
    }

    private static HttpURLConnection createConnection() throws Exception {
        URL url = new URL(PROFILE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private static UUID getUUID(String id) {
        return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public static UUID fromBytes(byte[] array) {
        if (array.length != 16) {
            throw new IllegalArgumentException("Illegal byte array length: " + array.length);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        long mostSignificant = byteBuffer.getLong();
        long leastSignificant = byteBuffer.getLong();
        return new UUID(mostSignificant, leastSignificant);
    }

    public static UUID getUUIDOf(String name) throws Exception {
        return new UUIDFetcher(Arrays.asList(name)).call().get(name);
    }
}

class SwordPVPUUIDGet {
	private static final String URL_BASE = "https://uuid.swordpvp.com/uuid/";
	
	protected static String getMojangAccountID(String name){
		int queries = 3;
		boolean success = false;
		String reply = null;
		
		while(!success && queries > 0){
			try {
				String o = query(name);
				if(o == null || o == "null"){
					queries--;
				}
				reply = o;
				success = true;
			}
			catch(Exception e) {
				//Oh well failed...
			}
			finally {
				queries--;
			}
		}
		
		if(reply == null || reply == "null" || !success){
			return null;
		}
		
		return reply;
	}
	
	private static String query(String username) throws IOException{
		URL url = new URL(URL_BASE + username);
        /*HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setRequestMethod("GET");
        uc.setUseCaches(false);
        uc.setDefaultUseCaches(false);
        uc.addRequestProperty("User-Agent", "Mozilla/5.0");
        uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
        uc.addRequestProperty("Pragma", "no-cache");
        uc.setReadTimeout(1500); //1.5 seconds

        // Parse it
        String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(json);*/
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(3000); //3s timeout
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        reader.close();
        String reply = response.toString();
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(reply);
        return ((JsonObject) ((JsonArray) ((JsonObject) obj).get("profiles")).get(0)).get("id").getAsString();
	}
}

class FishBansMojangUUIDGet {
	private static final String URL_BASE = "http://api.fishbans.com/uuid/";
	
	protected static String getMojangAccountID(String name){
		int queries = 3;
		boolean success = false;
		JsonObject reply = null;
		
		while(!success && queries > 0){
			try {
				JsonObject o = query(name);
				reply = o;
				success = true;
			}
			catch(Exception e) {
				//Oh well failed...
			}
			finally {
				queries--;
			}
		}
		
		if(reply == null || !success || !reply.get("success").getAsBoolean()){
			return null;
		}
		
		return reply.get("uuid").getAsString();
	}
	
	private static JsonObject query(String playername) throws Exception{
		URL url = new URL(constructUrl(playername));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(3000); //3s timeout
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        reader.close();
        String reply = response.toString();
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(reply);
	}
	
	private static String constructUrl(String name){
		return URL_BASE + name;
	}
}

class SimpleMeta implements MetadataValue {
	public Object value = null;
	public Plugin plugin = null;

	public SimpleMeta(Object value, Plugin plugin) {
		this.value = value;
		this.plugin = plugin;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
		return;
	}

	public boolean asBoolean() {
		return false;
	}

	public byte asByte() {
		return 0;
	}

	public double asDouble() {
		return 0;
	}

	public float asFloat() {
		return 0;
	}

	public int asInt() {
		return 0;
	}

	public long asLong() {
		return 0;
	}

	public short asShort() {
		return 0;
	}

	public String asString() {
		return null;
	}

	public Plugin getOwningPlugin() {
		return plugin;
	}

	public void invalidate() {
		return;
	}

	public Object value() {
		return value;
	}
}
	
	/*
	public static void putBlockInCar(Minecart car, int id, int data){
		Boolean useFallingBlock = false;
		// net.minecraft.server.v1_7_R1.EntityMinecartAbstract;
		// org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
		String NMSversion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage()
				.getName().replace(".", ",").split(",")[3];
		String CBversion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage()
				.getName().replace(".", ",").split(",")[3];
		Class nms = null;
		Class cb = null;
		Class nmsEntity = null;
		try {
			nms = Class.forName(NMSversion + ".EntityMinecartAbstract");
			nmsEntity = Class.forName(NMSversion + ".Entity");
			cb = Class.forName(CBversion + ".entity.CraftEntity");
			Method carId = nms.getMethod("k", int.class);
			Method carData = nms.getMethod("l", int.class); //Method 'm' is for height/offset
			Method getNMSEntity = cb.getMethod("getHandle");
			carId.setAccessible(true);
			carData.setAccessible(true);
			getNMSEntity.setAccessible(true);
			Object ce = cb.cast(car);
			Object nmsE = nmsEntity.cast(getNMSEntity.invoke(ce));
			carId.invoke(nmsE, id);
			carData.invoke(nmsE, data);
		} catch (Exception e) {
			useFallingBlock = true;
		}
		if(useFallingBlock){
			//Don't use falling blocks as they're derpy
			main.logger.info("[ALERT] uCarsTrade was unable to place a wool block in a car,"
					+ " please check for an update.");
		}
		return;
	}
	*/

