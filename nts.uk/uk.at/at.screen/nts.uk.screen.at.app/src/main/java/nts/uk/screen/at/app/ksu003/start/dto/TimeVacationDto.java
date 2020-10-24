package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
/**
 * 時間休暇
 * @author phongtq
 *
 */
@Value
public class TimeVacationDto {
	//時間帯リスト
	private TimeZoneDto timeZone;
	//使用時間
	private DailyAttdTimeVacationDto usageTime;
}
