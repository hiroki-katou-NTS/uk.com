package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;


/** 日別実績の所定外時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOfStatutoryTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 所定外深夜時間: 所定外深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LATE_NIGHT)
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業時間: 日別実績の残業時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = OVERTIME)
	private OverTimeWorkDailyPerformDto overTimeWork;

	/** 休出時間: 日別実績の休出時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY_WORK)
	private WorkHolidayTimeDailyPerformDto workHolidayTime;
	
	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = TEMPORARY)
	private TemporaryTimeDailyPerformDto temporaryTime;

	public static ExcessOfStatutoryTimeDailyPerformDto fromExcessOfStatutoryTimeDailyPerform(ExcessOfStatutoryTimeOfDaily domain){
		return domain == null ? null : new ExcessOfStatutoryTimeDailyPerformDto(
				getExcessStatutory(domain.getExcessOfStatutoryMidNightTime()), 
				OverTimeWorkDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getOverTimeWork().orElse(null)), 
				WorkHolidayTimeDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getWorkHolidayTime().orElse(null)),
				TemporaryTimeDailyPerformDto.fromDomain(domain.getTemporaryTime()));
	}
	
	@Override
	public ExcessOfStatutoryTimeDailyPerformDto clone() {
		return new ExcessOfStatutoryTimeDailyPerformDto(excessOfStatutoryMidNightTime == null ? null : excessOfStatutoryMidNightTime.clone(),
														overTimeWork == null ? null : overTimeWork.clone(),
														workHolidayTime == null ? null : workHolidayTime.clone(),
														temporaryTime == null ? null : temporaryTime.clone());
	}

	private static ExcessOfStatutoryMidNightTimeDto getExcessStatutory(ExcessOfStatutoryMidNightTime domain) {
		return domain == null ? null : new ExcessOfStatutoryMidNightTimeDto(
				CalcAttachTimeDto.toTimeWithCal(domain.getTime()), 
				getAttendanceTime(domain.getBeforeApplicationTime()));
	}
	
	public ExcessOfStatutoryTimeOfDaily toDomain() {
		return new ExcessOfStatutoryTimeOfDaily(
				toExcessOfStatutory(),
				overTimeWork == null ? Optional.empty() : Optional.of(overTimeWork.toDomain()), 
				workHolidayTime == null ? Optional.empty() : Optional.of(workHolidayTime.toDomain()),
				temporaryTime.toDomain());
	}
	
	public static ExcessOfStatutoryTimeOfDaily defaultDomain() {
		return new ExcessOfStatutoryTimeOfDaily(
				ExcessOfStatutoryMidNightTimeDto.defaultDomain(),
				Optional.empty(), Optional.empty(),
				TemporaryTimeDailyPerformDto.defaultDomain());
	}

	private ExcessOfStatutoryMidNightTime toExcessOfStatutory() {
		return excessOfStatutoryMidNightTime == null ? new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.defaultValue(), AttendanceTime.ZERO)
											: new ExcessOfStatutoryMidNightTime(
												excessOfStatutoryMidNightTime.getTime().createTimeDivWithCalc(),
												toAttendanceTime(excessOfStatutoryMidNightTime.getBeforeApplicationTime()));
	}
	
	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}
	
	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? 0 : domain.valueAsMinutes();
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case LATE_NIGHT:
			return new ExcessOfStatutoryMidNightTimeDto();
		case OVERTIME:
			return new OverTimeWorkDailyPerformDto();
		case HOLIDAY_WORK:
			return new WorkHolidayTimeDailyPerformDto();
		case TEMPORARY:
			return new TemporaryTimeDailyPerformDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case LATE_NIGHT:
			return Optional.ofNullable(excessOfStatutoryMidNightTime);
		case OVERTIME:
			return Optional.ofNullable(overTimeWork);
		case HOLIDAY_WORK:
			return Optional.ofNullable(workHolidayTime);
		case TEMPORARY:
			return Optional.ofNullable(temporaryTime);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case LATE_NIGHT:
			excessOfStatutoryMidNightTime = (ExcessOfStatutoryMidNightTimeDto) value;
			break;
		case OVERTIME:
			overTimeWork = (OverTimeWorkDailyPerformDto) value;
			break;
		case HOLIDAY_WORK:
			workHolidayTime = (WorkHolidayTimeDailyPerformDto) value;
			break;
		case TEMPORARY:
			temporaryTime = (TemporaryTimeDailyPerformDto) value;
			break;
		default:
			break;
		}
	}

	
}
