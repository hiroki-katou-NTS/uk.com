package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;

@Data
/** 年休付与情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveGrantDto {

	/** 付与日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "付与日数", layout = "A")
	private double grantDays;
	
	/** 付与労働日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "付与労働日数", layout = "B")
	private double grantWorkingDays;
	
	/** 付与所定日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "付与所定日数", layout = "C")
	private double grantPrescribedDays;
	
	/** 付与控除日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "付与控除日数", layout = "D")
	private double grantDeductedDays;
	
	/** 控除日数付与前 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "控除日数付与前", layout = "E")
	private double deductedDaysBeforeGrant;
	
	/** 控除日数付与後 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "控除日数付与後", layout = "F")
	private double deductedDaysAfterGrant;
	
	/** 出勤率 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "出勤率", layout = "G")
	private double attendanceRate;

	public static AnnualLeaveGrantDto from(AnnualLeaveGrant domain) {
		return domain == null ? null : new AnnualLeaveGrantDto(
												domain.getGrantDays().v(), 
												domain.getGrantWorkingDays().v(), 
												domain.getGrantPrescribedDays().v(), 
												domain.getGrantDeductedDays().v(), 
												domain.getDeductedDaysBeforeGrant().v(), 
												domain.getDeductedDaysAfterGrant().v(), 
												domain.getAttendanceRate().v().doubleValue());
	}
	
	public AnnualLeaveGrant toDomain(){
		return AnnualLeaveGrant.of(new AnnualLeaveGrantDayNumber(grantDays),
									new YearlyDays(grantWorkingDays), 
									new YearlyDays(grantPrescribedDays), 
									new YearlyDays(grantDeductedDays), 
									new MonthlyDays(deductedDaysBeforeGrant),
									new MonthlyDays(deductedDaysAfterGrant), 
									new AttendanceRate(attendanceRate));
	}
}
