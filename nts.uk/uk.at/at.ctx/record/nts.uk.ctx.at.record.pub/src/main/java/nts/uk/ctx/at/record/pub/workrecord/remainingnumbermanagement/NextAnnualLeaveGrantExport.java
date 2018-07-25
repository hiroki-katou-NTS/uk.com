package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

@Value
public class NextAnnualLeaveGrantExport {
	/** 付与年月日 */
	public GeneralDate grantDate;
	/** 付与日数 */
	public GrantDays grantDays;
	/** 回数 */
	public GrantNum times;
	/** 時間年休上限日数 */
	public Optional<LimitedTimeHdDays> timeAnnualLeaveMaxDays;
	/** 時間年休上限時間 */
	public Optional<LimitedTimeHdTime> timeAnnualLeaveMaxTime;
	/** 半日年休上限回数 */
	public Optional<LimitedHalfHdCnt> halfDayAnnualLeaveMaxTimes;
}
