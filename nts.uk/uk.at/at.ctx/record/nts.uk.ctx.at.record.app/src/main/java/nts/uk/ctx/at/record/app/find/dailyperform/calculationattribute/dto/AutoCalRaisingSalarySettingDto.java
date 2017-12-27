package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
@AllArgsConstructor
/** 加給の自動計算設定 */
public class AutoCalRaisingSalarySettingDto {

	/** 加給: 加給計算区分 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int salaryCalSetting;

	/** 特定加給計算区分: 特定加給計算区分 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int specificSalaryCalSetting;
}
