package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;

@Data
/** 集計総労働時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateTotalWorkingTimeDto implements ItemConst, AttendanceItemDataGate {


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
				workTime == null ? new WorkTimeOfMonthly() : workTime.toDomain(),
				overTime == null ? new OverTimeOfMonthly() : overTime.toDomain(),
				holidayWorkTime == null ? new HolidayWorkTimeOfMonthly() : holidayWorkTime.toDomain(),
				vacationUseTime == null ? new VacationUseTimeOfMonthly() : vacationUseTime.toDomain(),
				prescribedWorkingTime == null ? new PrescribedWorkingTimeOfMonthly() : prescribedWorkingTime.toDomain());
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
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case WORK_TIME:
			return new WorkingTimeOfMonthlyDto();
		case OVERTIME:
			return new OverTimeOfMonthlyDto();
		case HOLIDAY_WORK:
			return new HolidayWorkTimeOfMonthlyDto();
		case (HOLIDAY + USAGE):
			return new VacationUseTimeOfMonthlyDto();
		case FIXED_WORK:
			return new PrescribedWorkingTimeOfMonthlyDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case WORK_TIME:
			return Optional.ofNullable(workTime);
		case OVERTIME:
			return Optional.ofNullable(overTime);
		case HOLIDAY_WORK:
			return Optional.ofNullable(holidayWorkTime);
		case (HOLIDAY + USAGE):
			return Optional.ofNullable(vacationUseTime);
		case FIXED_WORK:
			return Optional.ofNullable(prescribedWorkingTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case WORK_TIME:
			workTime = (WorkingTimeOfMonthlyDto) value;
			break;
		case OVERTIME:
			overTime = (OverTimeOfMonthlyDto) value;
			break;
		case HOLIDAY_WORK:
			holidayWorkTime = (HolidayWorkTimeOfMonthlyDto) value;
			break;
		case (HOLIDAY + USAGE):
			vacationUseTime = (VacationUseTimeOfMonthlyDto) value;
			break;
		case FIXED_WORK:
			prescribedWorkingTime = (PrescribedWorkingTimeOfMonthlyDto) value;
			break;
		default:
			break;
		}
	}

	
}
