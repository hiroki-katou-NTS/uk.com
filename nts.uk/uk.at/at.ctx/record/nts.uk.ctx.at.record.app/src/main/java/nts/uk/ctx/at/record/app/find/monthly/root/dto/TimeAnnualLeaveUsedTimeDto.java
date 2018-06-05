package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.TimeAnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 時間年休使用時間 */
/** 年休上限残時間 */
public class TimeAnnualLeaveUsedTimeDto {

	/** 使用回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数", layout = "A")
	private int usedTimes;
	
	/** 使用時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "B")
	private int usedTime;
	
	/** 使用時間付与前 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間付与前", layout = "C")
	private int usedTimeBeforeGrant;
	
	/** 使用時間付与後 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間付与後", layout = "D")
	private Integer usedTimeAfterGrant;

	public static TimeAnnualLeaveUsedTimeDto from(TimeAnnualLeaveUsedTime domain) {
		return domain == null ? null : new TimeAnnualLeaveUsedTimeDto(
						domain.getUsedTimes().v(), 
						domain.getUsedTime().valueAsMinutes(),
						domain.getUsedTimeBeforeGrant().valueAsMinutes(),
						domain.getUsedTimeAfterGrant().isPresent() ? domain.getUsedTimeAfterGrant().get().valueAsMinutes() : null);
	}

	public TimeAnnualLeaveUsedTime toDomain() {
		return TimeAnnualLeaveUsedTime.of(
							new UsedTimes(usedTimes), new UsedMinutes(usedTime),
							new UsedMinutes(usedTimeBeforeGrant),
							Optional.ofNullable(usedTimeAfterGrant == null ? null : new UsedMinutes(usedTimeAfterGrant)));
	}
	
	public static TimeAnnualLeaveUsedTimeDto from(AnnualLeaveMaxRemainingTime domain) {
		return domain == null ? null : new TimeAnnualLeaveUsedTimeDto(
						0, domain.getTime().valueAsMinutes(),
						domain.getTimeBeforeGrant().valueAsMinutes(),
						domain.getTimeAfterGrant().isPresent() ? domain.getTimeAfterGrant().get().valueAsMinutes() : null);
	}

	public AnnualLeaveMaxRemainingTime toMaxRemainingTimeDomain() {
		return AnnualLeaveMaxRemainingTime.of(
							new RemainingMinutes(usedTime),
							new RemainingMinutes(usedTimeBeforeGrant),
							Optional.ofNullable(usedTimeAfterGrant == null ? null : new RemainingMinutes(usedTimeAfterGrant)));
	}
}
