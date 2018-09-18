package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 
 * @author Hoi1102
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveLeaveUsageImport {
	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用日数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 月度残日数 */
	private ReserveLeaveRemainingDayNumber remainingDays;

}
