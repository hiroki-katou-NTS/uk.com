package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 集計総労働時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateTotalWorkingTimeDto implements ItemConst {

	/** 就業時間 */
	@AttendanceItemLayout(jpPropertyName = WORK_TIME, layout = LAYOUT_A)
	private WorkingTimeOfMonthlyDto workTime;

	/** 残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_B)
	private OverTimeOfMonthlyDto overTime;

	/** 休出時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	private HolidayWorkTimeOfMonthlyDto holidayWorkTime;

	/** 臨時時間 */
	// temporaryTime

	/** 休暇使用時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY + USAGE, layout = LAYOUT_D)
	private VacationUseTimeOfMonthlyDto vacationUseTime;

	/** 所定労働時間 */
	@AttendanceItemLayout(jpPropertyName = FIXED_WORK, layout = LAYOUT_E)
	private PrescribedWorkingTimeOfMonthlyDto prescribedWorkingTime;

	public AggregateTotalWorkingTime toDomain() {
		return AggregateTotalWorkingTime.of(
				workTime == null ? null : workTime.toDomain(),
				overTime == null ? null : overTime.toDomain(),
				holidayWorkTime == null ? null : holidayWorkTime.toDomain(),
				vacationUseTime == null ? null : vacationUseTime.toDomain(),
				prescribedWorkingTime == null ? null : prescribedWorkingTime.toDomain());
	}
	
	public static AggregateTotalWorkingTimeDto from(AggregateTotalWorkingTime domain) {
		AggregateTotalWorkingTimeDto dto = new AggregateTotalWorkingTimeDto();
		if(domain != null) {
			dto.setWorkTime(WorkingTimeOfMonthlyDto.from(domain.getWorkTime()));
			dto.setOverTime(OverTimeOfMonthlyDto.from(domain.getOverTime()));
			dto.setHolidayWorkTime(HolidayWorkTimeOfMonthlyDto.from(domain.getHolidayWorkTime()));
			dto.setVacationUseTime(VacationUseTimeOfMonthlyDto.from(domain.getVacationUseTime()));
			dto.setPrescribedWorkingTime(PrescribedWorkingTimeOfMonthlyDto.from(domain.getPrescribedWorkingTime()));
		}
		return dto;
	}
}
