package nts.uk.ctx.at.schedule.dom.shift.businesscalendar;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * 営業日カレンダー種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.営業日カレンダー種類
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
