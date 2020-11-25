package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;

@Data
/** 年休使用日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedDaysDto implements ItemConst {

	/** 使用日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double usedDays;

	/** 使用日数付与前 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private double usedDaysBeforeGrant;

	/** 使用日数付与後 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private Double usedDaysAfterGrant;

	public static AnnualLeaveUsedDaysDto from(AnnualLeaveUsedDays domain) {
		return domain == null ? null : new AnnualLeaveUsedDaysDto(
									domain.getUsedDays().v(), 
									domain.getUsedDaysBeforeGrant().v(),
									domain.getUsedDaysAfterGrant().isPresent() ? domain.getUsedDaysAfterGrant().get().v() : null);
	}
	
	public AnnualLeaveUsedDays toDomain() {
		return AnnualLeaveUsedDays.of(
								new AnnualLeaveUsedDayNumber(usedDays), 
								new AnnualLeaveUsedDayNumber(usedDaysBeforeGrant), 
								Optional.ofNullable(usedDaysAfterGrant == null 
										? null : new AnnualLeaveUsedDayNumber(usedDaysAfterGrant)));
	}
}
