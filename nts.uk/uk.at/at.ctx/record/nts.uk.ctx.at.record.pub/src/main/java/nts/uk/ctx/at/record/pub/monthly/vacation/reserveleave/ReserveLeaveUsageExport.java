package nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休利用状況
 * @author shuichu_ishida
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveLeaveUsageExport {

	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用日数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 月度残日数 */
	private ReserveLeaveRemainingDayNumber remainingDays;
}
