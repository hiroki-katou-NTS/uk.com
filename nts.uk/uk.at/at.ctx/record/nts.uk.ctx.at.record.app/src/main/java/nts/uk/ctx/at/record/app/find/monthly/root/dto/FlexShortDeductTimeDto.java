package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** フレックス不足控除時間 */
public class FlexShortDeductTimeDto implements ItemConst {

	/** 欠勤控除時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = ABSENCE, layout = LAYOUT_A)
	private Integer absenceDeductTime;

	/** 控除前のフレックス不足時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private Integer flexShortTimeBeforeDeduct;

	/** 年休控除日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = ANNUNAL_LEAVE, layout = LAYOUT_C)
	private Double annualLeaveDeductDays;

	public FlexShortDeductTime toDomain() {
		return FlexShortDeductTime.of(
						annualLeaveDeductDays == null ? null : new AttendanceDaysMonth(annualLeaveDeductDays),
						absenceDeductTime == null ? null : new AttendanceTimeMonth(absenceDeductTime),
						flexShortTimeBeforeDeduct == null ? null : new AttendanceTimeMonth(flexShortTimeBeforeDeduct));
	}
	
	public static FlexShortDeductTimeDto from(FlexShortDeductTime domain) {
		FlexShortDeductTimeDto dto = new FlexShortDeductTimeDto();
		if(domain != null) {
			dto.setAbsenceDeductTime(domain.getAbsenceDeductTime() == null ? null : domain.getAbsenceDeductTime().valueAsMinutes());
			dto.setAnnualLeaveDeductDays(domain.getAnnualLeaveDeductDays() == null ? null : domain.getAnnualLeaveDeductDays().v());
			dto.setFlexShortTimeBeforeDeduct(domain.getFlexShortTimeBeforeDeduct() == null ? null : domain.getFlexShortTimeBeforeDeduct().valueAsMinutes());
		}
		return dto;
	}
}
