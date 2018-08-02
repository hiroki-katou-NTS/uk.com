package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import nts.arc.enums.EnumAdaptor;

/**
 * 付与するタイミングの種類
 * 
 * @author tanlv
 *
 */
public enum TypeTime {

	/**
	 * 周期、日数を指定して付与する
	 */
	GRANT_START_DATE_SPECIFY(0),
	/**
	 * 付与テーブルを参照して付与する
	 */
	REFER_GRANT_DATE_TBL(1);
	
	public int value;
	
	TypeTime(int type){
		this.value = type;
	}
	
	public static TypeTime toEnum(int value){
		return EnumAdaptor.valueOf(value, TypeTime.class);
	}
	
}
