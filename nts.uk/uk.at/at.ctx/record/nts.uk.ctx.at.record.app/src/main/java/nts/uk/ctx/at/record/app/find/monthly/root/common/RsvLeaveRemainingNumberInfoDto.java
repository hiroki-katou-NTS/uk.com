package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 積立年休残情報 */
@NoArgsConstructor
@AllArgsConstructor
public class RsvLeaveRemainingNumberInfoDto implements ItemConst {
	
	/** 合計残日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private RsvLeaveRemainingNumberDto totalRemainingDays;

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private RsvLeaveRemainingNumberDto before;
	
	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private RsvLeaveRemainingNumberDto after;
	
	
	public static RsvLeaveRemainingNumberInfoDto from(ReserveLeaveRemainingNumberInfo domain) {
		
		return domain == null ? null : new RsvLeaveRemainingNumberInfoDto(
				RsvLeaveRemainingNumberDto.from(domain.getTotalRemaining()),
				RsvLeaveRemainingNumberDto.from(domain.getBeforeGrant()),
				RsvLeaveRemainingNumberDto.from(domain.getAfterGrant().orElse(null)));
	}
	
	public ReserveLeaveRemainingNumberInfo toReserveDomain() {
		return ReserveLeaveRemainingNumberInfo.of(
				totalRemainingDays.toReserveDomain(),
				before.toReserveDomain(),
				Optional.of(after.toReserveDomain()));
	}
}
