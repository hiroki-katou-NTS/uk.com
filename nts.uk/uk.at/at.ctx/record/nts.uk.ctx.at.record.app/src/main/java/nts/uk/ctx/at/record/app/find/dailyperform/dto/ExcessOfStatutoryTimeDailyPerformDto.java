package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の所定外時間 */
@Data
@AllArgsConstructor
public class ExcessOfStatutoryTimeDailyPerformDto {

	/** 所定外深夜時間: 所定外深夜時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "所定外深夜時間")
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業時間: 日別実績の残業時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "残業時間")
	private OverTimeWorkDailyPerformDto overTimeWork;

	/** 休出時間: 日別実績の休出時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "休出時間")
	private WorkHolidayTimeDailyPerformDto workHolidayTime;

	public static ExcessOfStatutoryTimeDailyPerformDto fromExcessOfStatutoryTimeDailyPerform(
			ExcessOfStatutoryTimeOfDaily domain) {
		return domain == null ? null
				: new ExcessOfStatutoryTimeDailyPerformDto(
						new ExcessOfStatutoryMidNightTimeDto(
								new CalcAttachTimeDto(
										domain.getExcessOfStatutoryMidNightTime().getTime().getCalcTime()
												.valueAsMinutes(),
										domain.getExcessOfStatutoryMidNightTime().getTime().getTime().valueAsMinutes()),
								domain.getExcessOfStatutoryMidNightTime().getBeforeApplicationTime().valueAsMinutes()),
						OverTimeWorkDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getOverTimeWork().orElse(null)),
						WorkHolidayTimeDailyPerformDto
								.fromOverTimeWorkDailyPerform(domain.getWorkHolidayTime().orElse(null)));
	}

	public ExcessOfStatutoryTimeOfDaily toDomain() {
		return new ExcessOfStatutoryTimeOfDaily(
				new ExcessOfStatutoryMidNightTime(
						TimeWithCalculation.createTimeWithCalculation(
								new AttendanceTime(excessOfStatutoryMidNightTime.getTime().getTime()),
								new AttendanceTime(excessOfStatutoryMidNightTime.getTime().getCalcTime())),
						new AttendanceTime(excessOfStatutoryMidNightTime.getBeforeApplicationTime())),
				overTimeWork == null ? Optional.empty() : Optional.of(overTimeWork.toDomain()),
				workHolidayTime == null ? Optional.empty() : Optional.of(workHolidayTime.toDomain()));
	}
}
