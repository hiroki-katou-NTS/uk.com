package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

@Data
@AllArgsConstructor
/** 残業時間の自動計算設定 */
public class AutoCalOfOverTimeDto {

	/** 早出残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
	private AutoCalculationSettingDto earlyOverTime;

	/** 早出深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
	private AutoCalculationSettingDto earlyMidnightOverTime;

	/** 普通残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "")
	private AutoCalculationSettingDto normalOverTime;

	/** 普通深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "")
	private AutoCalculationSettingDto normalMidnightOverTime;

	/** 法定内残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "")
	private AutoCalculationSettingDto legalOverTime;

	/** 法定内深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "")
	private AutoCalculationSettingDto legalMidnightOverTime;
}
