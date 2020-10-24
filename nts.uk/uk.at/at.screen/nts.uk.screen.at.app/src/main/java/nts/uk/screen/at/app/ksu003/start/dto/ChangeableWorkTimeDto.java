package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
/**
 * 勤務NOごとの変更可能な勤務時間帯
 * @author phongtq
 *
 */
@Value
public class ChangeableWorkTimeDto {
	//勤務NO
	private int workNo;
	//開始時刻の変更可能な時間帯
	private TimeZoneDto changeableStartTime;
	//終了時刻の変更可能な時間帯
	private TimeZoneDto changeableEndTime;
}
