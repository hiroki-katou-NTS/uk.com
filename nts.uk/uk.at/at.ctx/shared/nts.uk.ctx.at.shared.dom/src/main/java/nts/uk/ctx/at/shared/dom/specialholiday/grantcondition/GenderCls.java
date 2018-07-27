package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;

import nts.arc.enums.EnumAdaptor;

/**
 * 性別区分
 * 
 * @author tanlv
 *
 */
public enum GenderCls {

	/**
	 * 男性
	 */
	MALE(0),
	/**
	 * 女性
	 */
	FEMALE(1);
	
	public int value;
	
	GenderCls(int type){
		this.value = type;
	}
	
	public static GenderCls toEnum(int value){
		return EnumAdaptor.valueOf(value, GenderCls.class);
	}
	
}
