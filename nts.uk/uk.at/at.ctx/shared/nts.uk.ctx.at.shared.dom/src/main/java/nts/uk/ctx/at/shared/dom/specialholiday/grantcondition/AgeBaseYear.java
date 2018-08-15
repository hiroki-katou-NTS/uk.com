package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;

import nts.arc.enums.EnumAdaptor;

/**
 * 年齢基準年区分
 * 
 * @author tanlv
 *
 */
public enum AgeBaseYear {

	/**
	 * 当年
	 */
	THIS_YEAR(0),
	/**
	 * 翌年
	 */
	NEXT_YEAR(1);
	
	public int value;
	
	AgeBaseYear(int type){
		this.value = type;
	}
	
	public static AgeBaseYear toEnum(int value){
		return EnumAdaptor.valueOf(value, AgeBaseYear.class);
	}
	
}
