package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;

import nts.arc.enums.EnumAdaptor;

/**
 * 利用区分
 * 
 * @author tanlv
 *
 */
public enum UseAtr {

	/**
	 * 利用する
	 */
	USE(0),
	/**
	 * 利用しない
	 */
	NOT_USE(1);
	
	public int value;
	
	UseAtr(int type){
		this.value = type;
	}
	
	public static UseAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, UseAtr.class);
	}
	
}
