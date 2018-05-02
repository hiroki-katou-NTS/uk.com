package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の勤怠時間 */
@Getter
@Setter
@AttendanceItemRoot(rootName = "日別実績の勤怠時間")
public class AttendanceTimeDailyPerformDto extends AttendanceItemCommon {

	/** 年月日: 年月日 */
	private GeneralDate date;

	/** 社員ID: 社員ID */
	private String employeeID;

	/** 実績時間: 日別実績の勤務実績時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "実績時間")
	private ActualWorkTimeDailyPerformDto actualWorkTime;

	/** 勤務予定時間: 日別実績の勤務予定時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務予定時間")
	private WorkScheduleTimeDailyPerformDto scheduleTime;

	/** 滞在時間: 日別実績の滞在時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "滞在時間")
	private StayingTimeDto stayingTime;

	/** 医療時間: 日別実績の医療時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "医療時間", enumField = "dayNightAtr")
	private MedicalTimeDailyPerformDto medicalTime;

	/** 予実差異時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "予実差異時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer budgetTimeVariance;

	/** 不就労時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "不就労時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer unemployedTime;
	
	public static AttendanceTimeDailyPerformDto getDto(AttendanceTimeOfDailyPerformance domain) {
		AttendanceTimeDailyPerformDto items = new AttendanceTimeDailyPerformDto();
		if(domain != null){
			items.setEmployeeID(domain.getEmployeeId());
			items.setDate(domain.getYmd());
			items.setActualWorkTime(ActualWorkTimeDailyPerformDto.toActualWorkTime(domain.getActualWorkingTimeOfDaily()));
			//items.setBudgetTimeVariance(domain.getBudgetTimeVariance().valueAsMinutes());
			items.setBudgetTimeVariance(getAttendanceTime(domain.getBudgetTimeVariance()));
			items.setDate(domain.getYmd());
			items.setEmployeeID(domain.getEmployeeId());
			items.setMedicalTime(MedicalTimeDailyPerformDto.fromMedicalCareTime(domain.getMedicalCareTime()));
			items.setScheduleTime(WorkScheduleTimeDailyPerformDto.fromWorkScheduleTime(domain.getWorkScheduleTimeOfDaily()));
			items.setStayingTime(StayingTimeDto.fromStayingTime(domain.getStayingTime()));
			items.setUnemployedTime(getAttendanceTime(domain.getUnEmployedTime()));
			items.exsistData();
		}
		return items;
	}
	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}

	@Override
	public String employeeId() {
		return this.employeeID;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}
	
	@Override
	public AttendanceTimeOfDailyPerformance toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (emp == null) {
			emp = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new AttendanceTimeOfDailyPerformance(emp, date,
				scheduleTime == null ? null : scheduleTime.toDomain(), 
				actualWorkTime == null ? null : actualWorkTime.toDomain(),
				stayingTime == null ? null : stayingTime.toDomain(), 
				budgetTimeVariance == null ? null : new AttendanceTime(budgetTimeVariance),
				unemployedTime == null ? null : new AttendanceTime(unemployedTime));
	}
}
