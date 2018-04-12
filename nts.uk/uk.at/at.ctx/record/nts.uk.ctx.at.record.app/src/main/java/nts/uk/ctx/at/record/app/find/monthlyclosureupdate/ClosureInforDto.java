package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureInfor;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class ClosureInforDto {
	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	// 締め名称
	private String closureName;

	/** The closure month. */
	// 当月
	private int closureMonth;

	/** The period. */
	// 期間
	private GeneralDate periodStart;
	private GeneralDate periodEnd;

	/** The closure date. */
	// 締め日
	private int closureDay;
	private boolean isLastDay;

	public static ClosureInforDto fromDomain(ClosureInfor domain) {
		return new ClosureInforDto(domain.getClosureId().value, domain.getClosureName().v(),
				domain.getClosureMonth().getProcessingYm().v(), domain.getPeriod().start(), domain.getPeriod().end(),
				domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth());
	}
}
