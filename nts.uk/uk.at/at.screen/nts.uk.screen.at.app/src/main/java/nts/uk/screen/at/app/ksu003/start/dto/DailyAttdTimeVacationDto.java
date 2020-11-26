package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.Optional;

import lombok.Value;

/**
 * 日別勤怠の時間休暇使用時間
 * @author phongtq
 *
 */

@Value
public class DailyAttdTimeVacationDto {
	//時間年休使用時間
	private Integer timeAnnualLeaveUseTime;
	//時間代休使用時間
	private Integer timeCompensatoryLeaveUseTime;
	//超過有休使用時間
	private Integer sixtyHourExcessHolidayUseTime;
	//特別休暇使用時間
	private Integer timeSpecialHolidayUseTime;
	//特別休暇枠NO - Optional
	private Integer specialHolidayFrameNo;
	//子の看護休暇使用時間
	private Integer timeChildCareHolidayUseTime;
	//介護休暇使用時間
	private Integer timeCareHolidayUseTime;
}
