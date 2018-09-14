package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の所定外時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOfStatutoryTimeDailyPerformDto implements ItemConst {

	/** 所定外深夜時間: 所定外深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LATE_NIGHT)
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業時間: 日別実績の残業時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = OVERTIME)
	private OverTimeWorkDailyPerformDto overTimeWork;

	/** 休出時間: 日別実績の休出時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY_WORK)
	private WorkHolidayTimeDailyPerformDto workHolidayTime;
	
	public static ExcessOfStatutoryTimeDailyPerformDto fromExcessOfStatutoryTimeDailyPerform(ExcessOfStatutoryTimeOfDaily domain){
		return domain == null ? null : new ExcessOfStatutoryTimeDailyPerformDto(
				getExcessStatutory(domain.getExcessOfStatutoryMidNightTime()), 
				OverTimeWorkDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getOverTimeWork().orElse(null)), 
				WorkHolidayTimeDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getWorkHolidayTime().orElse(null)));
	}
	
	@Override
	public ExcessOfStatutoryTimeDailyPerformDto clone() {
		return new ExcessOfStatutoryTimeDailyPerformDto(excessOfStatutoryMidNightTime == null ? null : excessOfStatutoryMidNightTime.clone(),
														overTimeWork == null ? null : overTimeWork.clone(),
														workHolidayTime == null ? null : workHolidayTime.clone());
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
				workHolidayTime == null ? Optional.empty() : Optional.of(workHolidayTime.toDomain()));
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
}
