package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の所定外時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	public static ExcessOfStatutoryTimeDailyPerformDto fromExcessOfStatutoryTimeDailyPerform(ExcessOfStatutoryTimeOfDaily domain){
		return domain == null ? null : new ExcessOfStatutoryTimeDailyPerformDto(
				new ExcessOfStatutoryMidNightTimeDto(
						new CalcAttachTimeDto(
								domain.getExcessOfStatutoryMidNightTime().getTime().getCalcTime().valueAsMinutes(),
								domain.getExcessOfStatutoryMidNightTime().getTime().getTime().valueAsMinutes()), 
						domain.getExcessOfStatutoryMidNightTime().getBeforeApplicationTime().valueAsMinutes()), 
				OverTimeWorkDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getOverTimeWork().orElse(null)), 
				WorkHolidayTimeDailyPerformDto.fromOverTimeWorkDailyPerform(domain.getWorkHolidayTime().orElse(null)));
	}
}
