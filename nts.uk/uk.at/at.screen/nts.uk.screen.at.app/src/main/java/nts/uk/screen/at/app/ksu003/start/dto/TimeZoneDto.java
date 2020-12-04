package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 時間帯(実装コードなし/使用不可)
 * @author phongtq
 *
 */
@Value
@AllArgsConstructor
public class TimeZoneDto {
	//開始時刻 
	public TimeOfDayDto startTime;
	//終了時刻
	public TimeOfDayDto endTime;
}
