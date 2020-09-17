package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 適用する時間帯
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum ApplicableTimeZoneCls {
	/** 0 - 全日帯 **/
	ALLDAY(0),
	/** 1 - 夜勤時間帯**/
	NIGHTSHIFT(1);

	public final int value;
	
	public static   ApplicableTimeZoneCls of(int value) {
		
		return EnumAdaptor.valueOf(value,  ApplicableTimeZoneCls.class);
	}
	
}
