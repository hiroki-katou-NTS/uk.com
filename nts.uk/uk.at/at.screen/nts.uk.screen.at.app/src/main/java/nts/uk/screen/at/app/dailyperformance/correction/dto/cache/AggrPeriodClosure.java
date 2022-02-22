package nts.uk.screen.at.app.dailyperformance.correction.dto.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ClosureDateDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggrPeriodClosure {

	/** 締めID */
	private Integer closureId;
	/** 締め日 */
//	private ClosureDate closureDate;
	private ClosureDateDto closureDate;
	/** 年月 */
	private int yearMonth;
	/** 期間 */
	private DateRange period;
}
