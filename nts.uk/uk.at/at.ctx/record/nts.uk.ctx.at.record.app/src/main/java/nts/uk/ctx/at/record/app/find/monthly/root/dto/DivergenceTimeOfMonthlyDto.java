package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceAtrOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の乖離時間 -> 集計乖離時間 */
public class DivergenceTimeOfMonthlyDto {

	@AttendanceItemLayout(jpPropertyName = "控除後乖離時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 控除後乖離時間: 勤怠月間時間 */
	private Integer divergenceTimeAfterDeduction;

	@AttendanceItemLayout(jpPropertyName = "控除時間", layout = "B")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 控除時間: 勤怠月間時間 */
	private Integer deductionTime;

	@AttendanceItemLayout(jpPropertyName = "乖離フラグ", layout = "C")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 乖離フラグ: 月別実績の乖離フラグ */
	private Integer divergenceAtr;

	@AttendanceItemLayout(jpPropertyName = "乖離時間", layout = "D")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 乖離時間: 勤怠月間時間 */
	private Integer divergenceTime;

	/** 乖離時間NO: 乖離時間NO */
	private int divergenceTimeNo;

	public static DivergenceTimeOfMonthlyDto from(AggregateDivergenceTime domain) {
		DivergenceTimeOfMonthlyDto dto = new DivergenceTimeOfMonthlyDto();
		if(domain != null) {
			dto.setDeductionTime(domain.getDeductionTime() == null ? null : domain.getDeductionTime().valueAsMinutes());
			dto.setDivergenceAtr(domain.getDivergenceAtr() == null ? null : domain.getDivergenceAtr().value);
			dto.setDivergenceTime(domain.getDivergenceTime() == null ? null : domain.getDivergenceTime().valueAsMinutes());
			dto.setDivergenceTimeAfterDeduction(domain.getDivergenceTimeAfterDeduction() == null 
					? null : domain.getDivergenceTimeAfterDeduction().valueAsMinutes());
			dto.setDivergenceTimeNo(domain.getDivergenceTimeNo());
		}
		return dto;
	}
	public AggregateDivergenceTime toDomain() {
		return AggregateDivergenceTime.of(divergenceTimeNo, toAttendanceTimeMonth(divergenceTime), 
				toAttendanceTimeMonth(deductionTime), toAttendanceTimeMonth(divergenceTimeAfterDeduction),
				ConvertHelper.getEnum(divergenceAtr, DivergenceAtrOfMonthly.class));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return time == null ? null : new AttendanceTimeMonth(time);
	}
}
