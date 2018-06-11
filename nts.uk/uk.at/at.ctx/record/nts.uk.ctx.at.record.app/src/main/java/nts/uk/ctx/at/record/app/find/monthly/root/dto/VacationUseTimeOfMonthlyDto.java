package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の休暇使用時間 */
public class VacationUseTimeOfMonthlyDto implements ItemConst {

	/** 年休 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = ANNUNAL_LEAVE, layout = LAYOUT_A)
	private Integer annualLeave;

	/** 積立年休 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = RETENTION, layout = LAYOUT_B)
	private Integer retentionYearly;

	/** 特別休暇 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = SPECIAL, layout = LAYOUT_C)
	private Integer specialHoliday;

	/** 代休 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = COMPENSATORY, layout = LAYOUT_D)
	private Integer compensatoryLeave;

	public VacationUseTimeOfMonthly toDomain() {
		return VacationUseTimeOfMonthly.of(
							AnnualLeaveUseTimeOfMonthly.of(annualLeave == null ? null : new AttendanceTimeMonth(annualLeave)),
							RetentionYearlyUseTimeOfMonthly.of(retentionYearly == null ? null : new AttendanceTimeMonth(retentionYearly)),
							SpecialHolidayUseTimeOfMonthly.of(specialHoliday == null ? null : new AttendanceTimeMonth(specialHoliday)),
							CompensatoryLeaveUseTimeOfMonthly.of(compensatoryLeave == null ? null : new AttendanceTimeMonth(compensatoryLeave)));
	}
	
	public static VacationUseTimeOfMonthlyDto from(VacationUseTimeOfMonthly domain) {
		VacationUseTimeOfMonthlyDto dto = new VacationUseTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAnnualLeave(domain.getAnnualLeave() == null ? null : domain.getAnnualLeave().getUseTime().valueAsMinutes());
			dto.setCompensatoryLeave(domain.getCompensatoryLeave() == null ? null : domain.getCompensatoryLeave().getUseTime().valueAsMinutes());
			dto.setRetentionYearly(domain.getRetentionYearly() == null ? null : domain.getRetentionYearly().getUseTime().valueAsMinutes());
			dto.setSpecialHoliday(domain.getSpecialHoliday() == null ? null : domain.getSpecialHoliday().getUseTime().valueAsMinutes());
		}
		return dto;
	}
}
