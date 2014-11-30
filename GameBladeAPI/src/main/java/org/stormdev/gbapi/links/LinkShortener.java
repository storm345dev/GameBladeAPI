package org.stormdev.gbapi.links;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
import org.stormdev.gbapi.storm.misc.Sch;

import com.google.common.base.Charsets;

public class LinkShortener {
	public static class ShorteningError extends Exception {
		private static final long serialVersionUID = 4132381302244358319L;
		
		private String errorMsg;
		public ShorteningError(String errorMsg){
			this.setErrorMsg(errorMsg);
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}
	
	public static String shorten(String location, String customAlias) throws ShorteningError{
		Sch.notSync();
		try {
			String urlEncoded = URLEncoder.encode(location, Charsets.UTF_8.name());
			String aliasEncoded = URLEncoder.encode(customAlias, Charsets.UTF_8.name());
			URL url = new URL("http://gamebla.de/api?api=SFrNtvUxFojf&url="+urlEncoded+"&custom="+aliasEncoded);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setReadTimeout(15000); //3s timeout
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();

			while ((line = reader.readLine()) != null) {
			    response.append(line);
			    response.append('\r');
			}

			reader.close();
			String reply = response.toString();
//			System.out.println(reply);
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse(reply);
			if(obj.has("error")){
				if(obj.get("error").getAsInt() == 1){
					if(obj.get("msg").getAsString().equalsIgnoreCase("This custom alias is already taken.")){
						return "http://gamebla.de/"+customAlias;
					}
					throw new ShorteningError(obj.get("msg").getAsString());
				}
				else {
					if(obj.has("short")){
						String out = obj.get("short").getAsString();
						return out;
					}
				}
			}
		} catch (JsonSyntaxException e) {
			throw new ShorteningError("Service unavailable");
		} catch (UnsupportedEncodingException e) {
			throw new ShorteningError("Unsupported UTF-8 encoding!");
		} catch (MalformedURLException e) {
			throw new ShorteningError("URL was malformed!");
		} catch (Exception e) {
			throw new ShorteningError(e.getMessage()); //TODO ERROR
		}
		throw new ShorteningError("No URL response!");
	}
}
