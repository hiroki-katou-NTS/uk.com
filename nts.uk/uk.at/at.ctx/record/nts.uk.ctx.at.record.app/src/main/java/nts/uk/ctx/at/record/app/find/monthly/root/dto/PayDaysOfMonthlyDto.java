package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays.PayDaysOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の給与用日数 */
public class PayDaysOfMonthlyDto implements ItemConst {

	/** 給与出勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = ATTENDANCE, layout = LAYOUT_A)
	private double payAttendanceDays;

	/** 給与欠勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = ABSENCE, layout = LAYOUT_B)
	private double payAbsenceDays;
	
	public static PayDaysOfMonthlyDto from(PayDaysOfMonthly domain) {
		PayDaysOfMonthlyDto dto = new PayDaysOfMonthlyDto();
		if(domain != null) {
			dto.setPayAbsenceDays(domain.getPayAbsenceDays() == null ? 0 : domain.getPayAbsenceDays().v());
			dto.setPayAttendanceDays(domain.getPayAttendanceDays() == null ? 0 : domain.getPayAttendanceDays().v());
		}
		return dto;
	}
}
