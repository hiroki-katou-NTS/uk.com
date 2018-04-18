package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 自動計算設定 */
public class AutoCalculationSettingDto {

	/** 計算区分: 時間外の自動計算区分 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "計算区分")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int calculationAttr;

	/** 上限の設定: 時間外の上限設定 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "上限の設定")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int upperLimitSetting;
}
