package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedAnnualLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 年休未消化数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUndigestedNumberDto {

	/** 未消化日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "未消化日数", layout = "A")
	private double undigestedDays;

	/** 未消化時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "未消化時間", layout = "B")
	private Integer undigestedTime;

	public static AnnualLeaveUndigestedNumberDto from(AnnualLeaveUndigestedNumber domain) {
		return domain == null ? null : new AnnualLeaveUndigestedNumberDto(
						domain.getUndigestedDays() == null ? 0d : domain.getUndigestedDays().getUndigestedDays().v(),
						domain.getUndigestedTime().isPresent()
								? domain.getUndigestedTime().get().getUndigestedTime().valueAsMinutes() : null);
	}

	public AnnualLeaveUndigestedNumber toDomain() {
		return AnnualLeaveUndigestedNumber.of(
				UndigestedAnnualLeaveDays.of(new AnnualLeaveUsedDayNumber(undigestedDays)),
				Optional.ofNullable(undigestedTime == null ? null
						: UndigestedTimeAnnualLeaveTime.of(new UsedMinutes(undigestedTime))));
	}
}
