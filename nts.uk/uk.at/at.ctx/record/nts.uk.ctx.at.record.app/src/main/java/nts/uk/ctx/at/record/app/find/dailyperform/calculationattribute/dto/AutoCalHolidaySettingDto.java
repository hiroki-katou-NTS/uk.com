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
/** 休出時間の自動計算設定 */
public class AutoCalHolidaySettingDto implements ItemConst, AttendanceItemDataGate {

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
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case HOLIDAY_WORK:
			return Optional.ofNullable(holidayWorkTime);
		case LATE_NIGHT:
			return Optional.ofNullable(lateNightTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case HOLIDAY_WORK:
			holidayWorkTime = (AutoCalculationSettingDto) value;
			break;
		case LATE_NIGHT:
			lateNightTime = (AutoCalculationSettingDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case HOLIDAY_WORK:
		case LATE_NIGHT:
			return new AutoCalculationSettingDto();
		default:
			return null;
		}
	}
}
