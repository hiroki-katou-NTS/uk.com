package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * 営業日カレンダー種類
 * pathEA
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum BusinessDaysCalendarType {
	/** 0 - 会社 **/
	COMPANY(0),
	/** 1 - 職場 **/
	WORKPLACE(1),
	/** 2 - 分類**/
	CLASSSICATION(2);
	
	public final int value;

	public static  BusinessDaysCalendarType of(int value) {
		
		return EnumAdaptor.valueOf(value, BusinessDaysCalendarType.class);
	}
	

}
