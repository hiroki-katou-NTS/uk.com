package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;

@Data
/** 年休付与情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveGrantDto implements ItemConst {

	/** 付与日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double grantDays;
	
	/** 付与労働日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = LABOR, layout = LAYOUT_B)
	private double grantWorkingDays;
	
	/** 付与所定日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY, layout = LAYOUT_C)
	private double grantPrescribedDays;
	
	/** 付与控除日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION, layout = LAYOUT_D)
	private double grantDeductedDays;
	
	/** 控除日数付与前 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION + BEFORE, layout = LAYOUT_E)
	private double deductedDaysBeforeGrant;
	
	/** 控除日数付与後 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION + AFTER, layout = LAYOUT_F)
	private double deductedDaysAfterGrant;
	
	/** 出勤率 */
	@AttendanceItemValue(type = ValueType.RATE)
	@AttendanceItemLayout(jpPropertyName = ATTENDANCE + RATE, layout = LAYOUT_G)
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
