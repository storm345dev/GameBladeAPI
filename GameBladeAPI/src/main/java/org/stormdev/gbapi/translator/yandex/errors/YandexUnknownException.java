package org.stormdev.gbapi.translator.yandex.errors;

public class YandexUnknownException extends YandexException {
	private static final long serialVersionUID = 1L;

	private int code = -1;
	
	public YandexUnknownException(){
		
	}
	
	public YandexUnknownException(int code){
		this.code = code;
	}
	
	@Override
	public int getErrorCode() {
		return code;
	}

	@Override
	public String getErrorMsg() {
		return "Unspecified error";
	}
	
}
