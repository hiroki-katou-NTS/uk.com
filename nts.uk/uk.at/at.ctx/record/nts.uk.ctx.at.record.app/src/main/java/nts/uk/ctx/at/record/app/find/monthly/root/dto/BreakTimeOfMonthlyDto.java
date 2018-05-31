package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の休憩時間 */
public class BreakTimeOfMonthlyDto {

	/** 休憩時間: 勤怠月間時間 */
	@AttendanceItemLayout(jpPropertyName = "休憩時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer breakTime;

	/** 所定内休憩時間: 勤怠月間時間 */
	@AttendanceItemLayout(jpPropertyName = "所定内休憩時間", layout = "B")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer withinBreakTime;

	/** 所定外休憩時間: 勤怠月間時間 */
	@AttendanceItemLayout(jpPropertyName = "所定外休憩時間", layout = "C")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer excessBreakTime;

	public static BreakTimeOfMonthlyDto from(BreakTimeOfMonthly domain) {
		return domain == null ? null : 
					new BreakTimeOfMonthlyDto(domain.getBreakTime() == null ? null : domain.getBreakTime().valueAsMinutes(), null, null);
	}
	
	public BreakTimeOfMonthly toDomain() {
		return BreakTimeOfMonthly.of(breakTime == null ? null : new AttendanceTimeMonth(breakTime));
	}
}
