package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 期間別の縦計 */
@NoArgsConstructor
@AllArgsConstructor
public class VerticalTotalOfMonthlyDto implements ItemConst {

	/** 勤務時間: 月別実績の勤務時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private WorkTimeOfMonthlyDto workTime;

	/** 勤務時刻: 月別実績の勤務時刻 */
	@AttendanceItemLayout(jpPropertyName = CLOCK, layout = LAYOUT_B)
	private WorkHourOfMonthlyDto workHour;

	/** 勤務日数: 月別実績の勤務日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_C)
	private WorkDaysOfMonthlyDto workDays;
	
	public VerticalTotalOfMonthly toDomain(){
		return VerticalTotalOfMonthly.of(workDays == null ? null : workDays.toDomain(), 
										workTime == null ? null : workTime.toDomain(), 
										workHour == null ? null : workHour.toDomain());
	}
	
	public static VerticalTotalOfMonthlyDto from(VerticalTotalOfMonthly domain) {
		VerticalTotalOfMonthlyDto dto = new VerticalTotalOfMonthlyDto();
		if(domain != null) {
			dto.setWorkTime(WorkTimeOfMonthlyDto.from(domain.getWorkTime()));
			dto.setWorkDays(WorkDaysOfMonthlyDto.from(domain.getWorkDays()));
			dto.setWorkHour(WorkHourOfMonthlyDto.from(domain.getWorkClock()));
		}
		return dto;
	}
}
