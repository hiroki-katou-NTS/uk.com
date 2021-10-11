package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;

/**
 * @author thanh_nx
 *
 *         逐次休暇の未相殺数
 */
@AllArgsConstructor
@Getter
public class UnoffsetNumSeqVacation {

	// 日数
	private final LeaveRemainingDayNumber days;

	// 時間
	private final LeaveRemainingTime remainTime;

}
