package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.LaborTimeOfMonthly;

@Data
/** 月別実績の労働時間 */
@NoArgsConstructor
@AllArgsConstructor
public class LaborTimeMonthlyDto implements ItemConst {

	/** 実働時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ACTUAL, layout = LAYOUT_A)
	private int actualTime;

	/** 総計算時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL_CALC, layout = LAYOUT_B)
	private int totalCalcTime;

	/** 計算差異時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = CALC + DIFF, layout = LAYOUT_B)
	private int calcDiffTime;
	
	public static LaborTimeMonthlyDto from(LaborTimeOfMonthly domain) {
		LaborTimeMonthlyDto dto = new LaborTimeMonthlyDto();
		if(domain != null) {
			dto.setActualTime(domain.getActualWorkTime().valueAsMinutes());
			dto.setCalcDiffTime(domain.getCalcDiffTime().valueAsMinutes());
			dto.setTotalCalcTime(domain.getTotalCalcTime().valueAsMinutes());
		}
		return dto;
	}

	public LaborTimeOfMonthly toDomain(){
		return LaborTimeOfMonthly.of(
				new AttendanceTimeMonth(actualTime),
				new AttendanceTimeMonth(totalCalcTime), 
				new AttendanceTimeMonth(calcDiffTime));
	}
}
