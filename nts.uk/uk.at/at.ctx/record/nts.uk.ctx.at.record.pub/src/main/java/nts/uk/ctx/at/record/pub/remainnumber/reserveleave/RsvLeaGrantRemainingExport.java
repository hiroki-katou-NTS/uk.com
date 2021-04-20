package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

/**
 * 積立年休付与残数データ
 * @author shuichi_ishida
 */
@Getter
@AllArgsConstructor
public class RsvLeaGrantRemainingExport {

	/** 付与日 */
	private GeneralDate grantDate;
	/** 期限日 */
	private GeneralDate deadline;
	/** 付与日数 */
	private LeaveGrantDayNumber grantNumber;
	/** 使用数 */
	private LeaveUsedNumber usedNumber;
	/** 残日数 */
	private LeaveRemainingDayNumber remainingNumber;
}
