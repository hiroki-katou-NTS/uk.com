package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
/**
 * 時間帯(実装コードなし/使用不可)
 * @author phongtq
 *
 */
@Value
public class TimeZoneDto {
	//開始時刻 
	private TimeOfDayDto startTime;
	//終了時刻
	private TimeOfDayDto endTime;
}
