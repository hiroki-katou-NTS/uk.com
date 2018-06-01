package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.EndClockOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の終業時刻 */
public class EndWorkHourOfMonthlyDto {

	@AttendanceItemLayout(jpPropertyName = "回数", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 回数: 勤怠月間回数 */
	private Integer times;

	@AttendanceItemLayout(jpPropertyName = "合計時刻", layout = "B")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 合計時刻: 勤怠月間時間 */
	private Integer totalHours;

	@AttendanceItemLayout(jpPropertyName = "平均時刻", layout = "C")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 平均時刻: 勤怠月間時間 */
	private Integer averageHours;
	
	public static EndWorkHourOfMonthlyDto from(EndClockOfMonthly domain){
		if(domain != null){
			return new EndWorkHourOfMonthlyDto(domain.getTimes() == null ? null : domain.getTimes().v(), 
												domain.getTotalClock() == null ? null : domain.getTotalClock().valueAsMinutes(), 
												domain.getAverageClock() == null ? null : domain.getAverageClock().valueAsMinutes());
		}
		return null;
	}
	
	public EndClockOfMonthly toDomain(){
		return EndClockOfMonthly.of(times == null ? null : new AttendanceTimesMonth(times),
									totalHours == null ? null : new AttendanceTimeMonth(totalHours),
									averageHours == null ? null : new AttendanceTimeMonth(averageHours));
	}
}
