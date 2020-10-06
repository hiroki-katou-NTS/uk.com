package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.TimeUsedNumberDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;

@Data
/** 年休使用数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedNumberDto implements ItemConst {

	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private AnnualLeaveUsedDaysDto usedDays;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private TimeUsedNumberDto usedTime;

	public static AnnualLeaveUsedNumberDto from(AnnualLeaveUsedNumber domain) {
		return domain == null ? null : new AnnualLeaveUsedNumberDto(
												AnnualLeaveUsedDaysDto.from(domain.getUsedDays()),
												TimeUsedNumberDto.from(domain.getUsedTime().orElse(null)));
	}
	
	public AnnualLeaveUsedNumber toDomain() {
		return AnnualLeaveUsedNumber.of(
								usedDays == null ? new AnnualLeaveUsedDays() : usedDays.toDomain(), 
								Optional.ofNullable(usedTime == null ? null : usedTime.toDomain()));
	}
}
