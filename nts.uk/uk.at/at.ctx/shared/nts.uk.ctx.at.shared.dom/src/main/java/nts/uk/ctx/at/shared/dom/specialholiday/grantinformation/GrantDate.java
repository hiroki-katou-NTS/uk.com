package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import nts.arc.enums.EnumAdaptor;

/**
 * 付与基準日
 * 
 * @author tanlv
 *
 */
public enum GrantDate {

	/**
	 * 入社日を付与基準日とする
	 */
	EMP_GRANT_DATE(0),
	/**
	 * 年休付与基準日を付与基準日とする
	 */
	GRANT_BASE_HOLIDAY(1),
	/**
	 * 特別休暇付与基準日を付与基準日とする
	 */
	SPECIAL_LEAVE_DATE(2);
	
	public int value;
	
	GrantDate(int type){
		this.value = type;
	}
	
	public static GrantDate toEnum(int value){
		return EnumAdaptor.valueOf(value, GrantDate.class);
	}
	
}
