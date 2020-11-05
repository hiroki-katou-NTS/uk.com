package nts.uk.screen.at.app.kdl045.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 日別勤怠の時間休暇使用時間:Dto
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class DailyAttdTimeVacationDto {
	private Integer timeAbbyakLeave; //時間年休使用時間
	private String timeAbbyakLeaveDisplay;
	private Integer timeOff;//時間代休使用時間
	private String timeOffDisplay;
	private Integer excessPaidHoliday;//超過有休使用時間
	private String excessPaidHolidayDisplay;
	private Integer specialHoliday;//特別休暇使用時間
	private String specialHolidayDisplay;
	private Integer frameNO;//特別休暇枠NO
	private String textKDL045_63;
	private Integer childNursingLeave;//子の看護休暇使用時間
	private String childNursingLeaveDisplay;
	private Integer nursingCareLeave;//介護休暇使用時間
	private String nursingCareLeaveDisplay;
	public DailyAttdTimeVacationDto(Integer timeAbbyakLeave, Integer timeOff, Integer excessPaidHoliday,
			Integer specialHoliday, Integer frameNO, Integer childNursingLeave, Integer nursingCareLeave) {
		super();
		this.timeAbbyakLeave = timeAbbyakLeave;
		this.timeOff = timeOff;
		this.excessPaidHoliday = excessPaidHoliday;
		this.specialHoliday = specialHoliday;
		this.frameNO = frameNO;
		this.childNursingLeave = childNursingLeave;
		this.nursingCareLeave = nursingCareLeave;
	}
	public DailyAttdTimeVacationDto(Integer timeAbbyakLeave, String timeAbbyakLeaveDisplay, Integer timeOff,
			String timeOffDisplay, Integer excessPaidHoliday, String excessPaidHolidayDisplay, Integer specialHoliday,
			String specialHolidayDisplay, Integer frameNO, String textKDL045_63, Integer childNursingLeave,
			String childNursingLeaveDisplay, Integer nursingCareLeave, String nursingCareLeaveDisplay) {
		super();
		this.timeAbbyakLeave = timeAbbyakLeave;
		this.timeAbbyakLeaveDisplay = timeAbbyakLeaveDisplay;
		this.timeOff = timeOff;
		this.timeOffDisplay = timeOffDisplay;
		this.excessPaidHoliday = excessPaidHoliday;
		this.excessPaidHolidayDisplay = excessPaidHolidayDisplay;
		this.specialHoliday = specialHoliday;
		this.specialHolidayDisplay = specialHolidayDisplay;
		this.frameNO = frameNO;
		this.textKDL045_63 = textKDL045_63;
		this.childNursingLeave = childNursingLeave;
		this.childNursingLeaveDisplay = childNursingLeaveDisplay;
		this.nursingCareLeave = nursingCareLeave;
		this.nursingCareLeaveDisplay = nursingCareLeaveDisplay;
	}
	
	
}
