package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 休出時間の自動計算設定 */
public class AutoCalHolidaySettingDto {

	/** 休出時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "休出時間")
	private AutoCalculationSettingDto holidayWorkTime;

	/** 休出深夜時間: 自動計算設定 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "休出深夜時間")
	private AutoCalculationSettingDto lateNightTime;
}
