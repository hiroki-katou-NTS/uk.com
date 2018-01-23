package nts.uk.ctx.at.request.dom.application;

import nts.arc.enums.EnumAdaptor;

public enum UseAtr {

	/**
	 * 使用しない
	 */
	NOTUSE(0),
	/**
	 * 使用する
	 */
	USE(1);
	
	public int value;
	
	UseAtr(int type){
		this.value = type;
	}
	
	public static UseAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, UseAtr.class);
	}
	
}
