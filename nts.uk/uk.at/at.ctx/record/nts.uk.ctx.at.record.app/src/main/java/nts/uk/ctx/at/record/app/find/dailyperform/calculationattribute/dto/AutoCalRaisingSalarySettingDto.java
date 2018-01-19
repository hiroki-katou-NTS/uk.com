package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 加給の自動計算設定 */
public class AutoCalRaisingSalarySettingDto {

	/** 加給: 加給計算区分 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "加給")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int salaryCalSetting;

	/** 特定加給計算区分: 特定加給計算区分 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "特定加給")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int specificSalaryCalSetting;
}
