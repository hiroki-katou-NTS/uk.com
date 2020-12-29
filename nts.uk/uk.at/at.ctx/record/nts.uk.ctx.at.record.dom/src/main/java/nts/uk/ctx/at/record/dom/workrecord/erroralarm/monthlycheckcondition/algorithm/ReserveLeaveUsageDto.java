package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveLeaveUsageDto {
	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用日数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 月度残日数 */
	private ReserveLeaveRemainingDayNumber remainingDays;
}
