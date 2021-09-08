package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;

/**
 * @author thanh_nx
 *
 *         取得可能な代休日数
 */
@AllArgsConstructor
@Data
public class NumberConsecutiveVacation {

	// 日数
	private LeaveRemainingDayNumber days;

	// 時間
	private LeaveRemainingTime remainTime;

}
