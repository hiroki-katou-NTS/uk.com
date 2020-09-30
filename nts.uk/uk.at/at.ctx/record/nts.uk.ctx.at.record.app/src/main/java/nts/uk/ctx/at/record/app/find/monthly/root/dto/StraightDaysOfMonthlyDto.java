package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.StgGoStgBackDaysOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の給与用日数 */
public class StraightDaysOfMonthlyDto implements ItemConst {

	/** 給与出勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_GO, layout = LAYOUT_A)
	private double straightGo;

	/** 給与欠勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_BACK, layout = LAYOUT_B)
	private double straightBack;

	/** 給与欠勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_GO_BACK, layout = LAYOUT_C)
	private double straightGoBack;
	
	public static StraightDaysOfMonthlyDto from(StgGoStgBackDaysOfMonthly domain) {
		StraightDaysOfMonthlyDto dto = new StraightDaysOfMonthlyDto();
		if(domain != null) {
			dto.setStraightGo(domain.getStraightGo() == null ? 0 : domain.getStraightGo().v());
			dto.setStraightBack(domain.getStraightBack() == null ? 0 : domain.getStraightBack().v());
			dto.setStraightGoBack(domain.getStraightGoStraightBack() == null ? 0 : domain.getStraightGoStraightBack().v());
		}
		return dto;
	}
}
