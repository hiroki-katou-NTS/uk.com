package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.StayingTimeOfDaily;

@Builder
@Data
public class AttendanceTimeOfDailyAttendanceDto {
	//勤務予定時間 - 日別実績の勤務予定時間
	private WorkScheduleTimeOfDailyDto workScheduleTimeOfDaily;
	
	//実働時間/実績時間  - 日別実績の勤務実績時間 - 勤務時間
	private ActualWorkingTimeOfDailyDto actualWorkingTimeOfDaily;
	
//	//滞在時間 - 日別実績の滞在時間 change tyle
//	private StayingTimeOfDailyDto stayingTime;
//	
//	//不就労時間 - 勤怠時間
//	private AttendanceTimeOfExistMinusDto unEmployedTime;
//	
//	//予実差異時間 - 勤怠時間
//	private AttendanceTimeOfExistMinusDto budgetTimeVariance;
//	
//	//医療時間 - 日別実績の医療時間
//	private MedicalCareTimeOfDailyDto medicalCareTime;
}
