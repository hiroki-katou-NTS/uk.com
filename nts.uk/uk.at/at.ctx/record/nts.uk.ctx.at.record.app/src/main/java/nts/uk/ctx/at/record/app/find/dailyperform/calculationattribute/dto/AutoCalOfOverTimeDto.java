package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 残業時間の自動計算設定 */
public class AutoCalOfOverTimeDto implements ItemConst, AttendanceItemDataGate {

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
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case EARLY_SHIFT:
			return Optional.ofNullable(earlyOverTime);
		case (EARLY_SHIFT + LATE_NIGHT):
			return Optional.ofNullable(earlyMidnightOverTime);
		case NORMAL:
			return Optional.ofNullable(normalOverTime);
		case (NORMAL + LATE_NIGHT):
			return Optional.ofNullable(normalMidnightOverTime);
		case LEGAL:
			return Optional.ofNullable(legalOverTime);
		case (LEGAL + LATE_NIGHT):
			return Optional.ofNullable(legalMidnightOverTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case EARLY_SHIFT:
			earlyOverTime = (AutoCalculationSettingDto) value;
			break;
		case (EARLY_SHIFT + LATE_NIGHT):
			earlyMidnightOverTime = (AutoCalculationSettingDto) value;
			break;
		case NORMAL:
			normalOverTime =  (AutoCalculationSettingDto) value;
			break;
		case (NORMAL + LATE_NIGHT):
			normalMidnightOverTime = (AutoCalculationSettingDto) value;
			break;
		case LEGAL:
			legalOverTime =  (AutoCalculationSettingDto) value;
			break;
		case (LEGAL + LATE_NIGHT):
			legalMidnightOverTime =  (AutoCalculationSettingDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case EARLY_SHIFT:
		case (EARLY_SHIFT + LATE_NIGHT):
		case NORMAL:
		case (NORMAL + LATE_NIGHT):
		case LEGAL:
		case (LEGAL + LATE_NIGHT):
			return new AutoCalculationSettingDto();
		default:
			return null;
		}
	}
}
