package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonTimeCountDto;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.Late;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.LeaveEarly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の遅刻早退 */
public class LateLeaveEarlyOfMonthlyDto implements ItemConst {

	/** 早退: 早退 */
	@AttendanceItemLayout(jpPropertyName = LEAVE_EARLY, layout = LAYOUT_A)
	private CommonTimeCountDto leaveEarly;

	/** 遅刻: 遅刻 */
	@AttendanceItemLayout(jpPropertyName = LATE, layout = LAYOUT_B)
	private CommonTimeCountDto late;

	public static LateLeaveEarlyOfMonthlyDto from(LateLeaveEarlyOfMonthly domain) {
		LateLeaveEarlyOfMonthlyDto dto = new LateLeaveEarlyOfMonthlyDto();
		if(domain != null) {
			dto.setLate(CommonTimeCountDto.from(domain.getLate()));
			dto.setLeaveEarly(CommonTimeCountDto.from(domain.getLeaveEarly()));
		}
		return dto;
	}
	public LateLeaveEarlyOfMonthly toDomain() {
		return LateLeaveEarlyOfMonthly.of(leaveEarly == null ? new LeaveEarly() : leaveEarly.toLeaveEarly(),
										late == null ? new Late() : late.toLate());
	}
}
