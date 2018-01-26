package nts.uk.ctx.bs.employee.pub.company;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AffComHistItem {
	
	/** 履歴ID */
	private String historyId;

	/** 出向先データである */
	private boolean destinationData;

	/** 所属期間 */
	private DatePeriod datePeriod;

}
