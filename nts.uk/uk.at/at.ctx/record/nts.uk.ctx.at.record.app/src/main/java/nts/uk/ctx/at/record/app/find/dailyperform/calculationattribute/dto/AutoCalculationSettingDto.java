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
/** 自動計算設定 */
public class AutoCalculationSettingDto implements ItemConst {

	/** 計算区分: 時間外の自動計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CALC_ATTR)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int calculationAttr;

	/** 上限の設定: 時間外の上限設定 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = UPPER_LIMIT)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int upperLimitSetting;

	@Override
	protected AutoCalculationSettingDto clone() {
		return new AutoCalculationSettingDto(calculationAttr, upperLimitSetting);
	}
}
