package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 年休使用数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedNumberDto {

	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = "使用日数", layout = "A")
	private AnnualLeaveUsedDaysDto usedDays;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = "使用時間", layout = "B")
	private TimeAnnualLeaveUsedTimeDto usedTime;

	public static AnnualLeaveUsedNumberDto from(AnnualLeaveUsedNumber domain) {
		return domain == null ? null : new AnnualLeaveUsedNumberDto(
												AnnualLeaveUsedDaysDto.from(domain.getUsedDays()),
												TimeAnnualLeaveUsedTimeDto.from(domain.getUsedTime().orElse(null)));
	}
	
	public AnnualLeaveUsedNumber toDomain() {
		return AnnualLeaveUsedNumber.of(
								usedDays == null ? null : usedDays.toDomain(), 
								Optional.ofNullable(usedTime == null ? null : usedTime.toDomain()));
	}
}
