package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author ThanhNX
 * 締め履歴期間一覧
 */
@AllArgsConstructor
@Getter
public class ClosureHistPeriod {

	private ClosureId closureId;

	private DatePeriod period;
}
