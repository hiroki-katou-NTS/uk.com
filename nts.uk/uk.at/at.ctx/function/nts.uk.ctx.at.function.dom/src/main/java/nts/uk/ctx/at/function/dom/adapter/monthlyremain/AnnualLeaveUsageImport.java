package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

/**
 * 
 * @author Hoi1102
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsageImport {
	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用日数 */
	private AnnualLeaveUsedDayNumber usedDays;
	/** 月間使用時間 */
	private Optional<UsedMinutes> usedTime;
	/** 月度残日数 */
	private AnnualLeaveRemainingDayNumber remainingDays;
	/** 月度残時間 */
	private Optional<RemainingMinutes> remainingTime;
}
