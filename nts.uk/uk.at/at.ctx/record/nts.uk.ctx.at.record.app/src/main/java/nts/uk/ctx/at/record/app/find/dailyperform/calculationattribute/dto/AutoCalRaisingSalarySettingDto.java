package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 加給の自動計算設定 */
public class AutoCalRaisingSalarySettingDto implements ItemConst {

	/** 加給: 加給計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = RAISING_SALARY)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int salaryCalSetting;

	/** 特定加給計算区分: 特定加給計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = SPECIFIC)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int specificSalaryCalSetting;

	@Override
	protected AutoCalRaisingSalarySettingDto clone() {
		return new AutoCalRaisingSalarySettingDto(salaryCalSetting, specificSalaryCalSetting);
	}
	
}
