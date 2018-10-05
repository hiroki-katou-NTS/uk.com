package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 残業時間の自動計算設定 */
public class AutoCalOfOverTimeDto implements ItemConst {

	/** 早出残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = EARLY_SHIFT)
	private AutoCalculationSettingDto earlyOverTime;

	/** 早出深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = EARLY_SHIFT + LATE_NIGHT)
	private AutoCalculationSettingDto earlyMidnightOverTime;

	/** 普通残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = NORMAL)
	private AutoCalculationSettingDto normalOverTime;

	/** 普通深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = NORMAL + LATE_NIGHT)
	private AutoCalculationSettingDto normalMidnightOverTime;

	/** 法定内残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = LEGAL)
	private AutoCalculationSettingDto legalOverTime;

	/** 法定内深夜残業時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = LEGAL + LATE_NIGHT)
	private AutoCalculationSettingDto legalMidnightOverTime;

	@Override
	protected AutoCalOfOverTimeDto clone() {
		return new AutoCalOfOverTimeDto(earlyOverTime == null ? null : earlyOverTime.clone(), 
										earlyMidnightOverTime == null ? null : earlyMidnightOverTime.clone(),
										normalOverTime == null ? null : normalOverTime.clone(), 
										normalMidnightOverTime == null ? null : normalMidnightOverTime.clone(),
										legalOverTime == null ? null : legalOverTime.clone(), 
										legalMidnightOverTime == null ? null : legalMidnightOverTime.clone());
	}
	
}
