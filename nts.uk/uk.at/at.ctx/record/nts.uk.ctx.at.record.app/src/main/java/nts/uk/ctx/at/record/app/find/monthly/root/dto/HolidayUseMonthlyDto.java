package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.HolidayUsageOfMonthly;

@Data
/** 月別実績の休暇使用時間 */
@NoArgsConstructor
@AllArgsConstructor
public class HolidayUseMonthlyDto implements ItemConst {

	/** 振休使用時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TRANSFER_HOLIDAY, layout = LAYOUT_A)
	private int transfer;

	/** 欠勤使用時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ABSENCE, layout = LAYOUT_B)
	private int absence;
	
	public static HolidayUseMonthlyDto from(HolidayUsageOfMonthly domain) {
		HolidayUseMonthlyDto dto = new HolidayUseMonthlyDto();
		if(domain != null) {
			dto.setAbsence(domain.getAbsence().valueAsMinutes());
			dto.setTransfer(domain.getTransferHoliday().valueAsMinutes());
		}
		return dto;
	}

	public HolidayUsageOfMonthly toDomain(){
		return HolidayUsageOfMonthly.of(new AttendanceTimeMonth(transfer), new AttendanceTimeMonth(absence));
	}
}
