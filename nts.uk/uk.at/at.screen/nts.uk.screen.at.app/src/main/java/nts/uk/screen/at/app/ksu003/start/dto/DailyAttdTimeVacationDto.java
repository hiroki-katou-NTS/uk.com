package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;

/**
 * 日別勤怠の時間休暇使用時間
 * @author phongtq
 *
 */

@Value
public class DailyAttdTimeVacationDto {
	//時間年休使用時間
	private int timeAbbyakLeave;
	//時間代休使用時間
	private int timeOff;
	//超過有休使用時間
	private int excessPaidHoliday;
	//特別休暇使用時間
	private int specialHoliday;
	//特別休暇枠NO
	private int frameNO;
	//子の看護休暇使用時間
	private int childNursingLeave;
	//介護休暇使用時間
	private int nursingCareLeave;
}
