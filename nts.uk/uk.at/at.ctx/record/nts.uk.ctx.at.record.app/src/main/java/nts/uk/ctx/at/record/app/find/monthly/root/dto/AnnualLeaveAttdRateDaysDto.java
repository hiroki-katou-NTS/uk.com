package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;

@Data
/** 年休出勤率日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveAttdRateDaysDto {

	/** 労働日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "労働日数", layout = "A")
	private double workingDays;
	
	/** 所定日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "所定日数", layout = "B")
	private double prescribedDays;
	
	/** 控除日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "控除日数", layout = "C")
	private double deductedDays;
	
	public static AnnualLeaveAttdRateDaysDto from(AnnualLeaveAttdRateDays domain) {
		return domain == null ? null : new AnnualLeaveAttdRateDaysDto(
									domain.getWorkingDays().v(), 
									domain.getPrescribedDays().v(),
									domain.getDeductedDays().v());
	}
	
	public AnnualLeaveAttdRateDays toAttdRateDaysDomain() {
		return AnnualLeaveAttdRateDays.of(new MonthlyDays(workingDays), 
											new MonthlyDays(prescribedDays), 
											new MonthlyDays(deductedDays));
	}
}
