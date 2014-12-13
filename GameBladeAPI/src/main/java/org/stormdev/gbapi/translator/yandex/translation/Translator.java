package org.stormdev.gbapi.translator.yandex.translation;

import java.io.IOException;
import java.net.MalformedURLException;

import org.stormdev.gbapi.translator.yandex.errors.YandexException;
import org.stormdev.gbapi.translator.yandex.errors.YandexUnsupportedLanguageException;
import org.stormdev.gbapi.translator.yandex.languages.Lang;

public class Translator {
	private static YandexConnection con;
	static {
		con = new YandexConnection("trnsl.1.1.20141121T183745Z.640aedb93bcd04b7.1ac5bb0cabfa32bb8ffffb3fdb9eacd147685efb");
	}
	
	public Lang detectLanguage(String text) throws YandexUnsupportedLanguageException, YandexException, MalformedURLException, IOException{
		return con.getLang(text);
	}
	
	public String translate(Lang from, Lang to, String text) throws YandexException, MalformedURLException, IOException{
		return con.translate(from, to, text);
	}
}
