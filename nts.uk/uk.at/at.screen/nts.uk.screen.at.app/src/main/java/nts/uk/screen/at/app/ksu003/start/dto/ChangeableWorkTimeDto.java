package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 勤務NOごとの変更可能な勤務時間帯
 * @author phongtq
 *
 */
@Value
@AllArgsConstructor
public class ChangeableWorkTimeDto {
	//勤務NO
	public int workNo;
	public int startTime;
	public int endTime;
}
