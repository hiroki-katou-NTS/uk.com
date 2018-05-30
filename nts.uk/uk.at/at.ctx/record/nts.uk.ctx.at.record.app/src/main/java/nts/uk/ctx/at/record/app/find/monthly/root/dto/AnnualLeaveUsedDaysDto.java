package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 年休使用日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedDaysDto {

	/** 使用日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "使用日数", layout = "A")
	private double usedDays;

	/** 使用日数付与前 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "使用日数付与前", layout = "B")
	private double usedDaysBeforeGrant;

	/** 使用日数付与後 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "使用日数付与後", layout = "C")
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
