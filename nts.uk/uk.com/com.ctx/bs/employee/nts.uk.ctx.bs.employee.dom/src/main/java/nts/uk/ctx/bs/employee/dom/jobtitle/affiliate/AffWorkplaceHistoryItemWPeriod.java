package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;

/**
 * 期間付き職場履歴項目
 * @author nws-dungdv
 *
 */
@AllArgsConstructor
@Getter
public class AffWorkplaceHistoryItemWPeriod {
	
	// 期間
	private DatePeriod period;
	
	// 履歴項目
	private AffWorkplaceHistoryItem item;
}
