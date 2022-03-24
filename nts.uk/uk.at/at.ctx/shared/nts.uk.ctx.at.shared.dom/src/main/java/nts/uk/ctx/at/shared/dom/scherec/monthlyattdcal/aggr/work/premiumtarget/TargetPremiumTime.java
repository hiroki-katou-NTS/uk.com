package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 割増対象時間
 * @author shuichi_ishida
 */
@Getter
@AllArgsConstructor
public class TargetPremiumTime {

	/** 月割増対象時間 */
	private AttendanceTimeMonth targetPremiumTime;
	/** 加算した休暇使用時間 */
	private AttendanceTimeMonth addedVacationUseTime;
}
