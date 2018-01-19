package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 残業時間の自動計算設定 */
public class AutoCalOfOverTimeDto {

	/** 早出残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "早出残業時間")
	private AutoCalculationSettingDto earlyOverTime;

	/** 早出深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "早出深夜残業時間")
	private AutoCalculationSettingDto earlyMidnightOverTime;

	/** 普通残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "普通残業時間")
	private AutoCalculationSettingDto normalOverTime;

	/** 普通深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "普通深夜残業時間")
	private AutoCalculationSettingDto normalMidnightOverTime;

	/** 法定内残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "法定内残業時間")
	private AutoCalculationSettingDto legalOverTime;

	/** 法定内深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "法定内深夜残業時間")
	private AutoCalculationSettingDto legalMidnightOverTime;
}
