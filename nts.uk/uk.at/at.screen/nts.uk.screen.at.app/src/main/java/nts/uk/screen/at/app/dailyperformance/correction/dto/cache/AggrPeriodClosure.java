package nts.uk.screen.at.app.dailyperformance.correction.dto.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggrPeriodClosure {

	/** 締めID */
	private Integer closureId;
	/** 締め日 */
	private ClosureDate closureDate;
	/** 年月 */
	private int yearMonth;
	/** 期間 */
	private DatePeriod period;
}
