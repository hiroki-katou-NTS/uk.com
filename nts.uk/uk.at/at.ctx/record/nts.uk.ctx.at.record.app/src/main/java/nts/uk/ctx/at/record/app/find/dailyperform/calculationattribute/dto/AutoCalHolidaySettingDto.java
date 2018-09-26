package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 休出時間の自動計算設定 */
public class AutoCalHolidaySettingDto implements ItemConst {

	/** 休出時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = HOLIDAY_WORK)
	private AutoCalculationSettingDto holidayWorkTime;

	/** 休出深夜時間: 自動計算設定 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE_NIGHT)
	private AutoCalculationSettingDto lateNightTime;

	@Override
	protected AutoCalHolidaySettingDto clone() {
		return new AutoCalHolidaySettingDto(holidayWorkTime == null ? null : holidayWorkTime.clone(),
				lateNightTime == null ? null : lateNightTime.clone());
	}
	
}
