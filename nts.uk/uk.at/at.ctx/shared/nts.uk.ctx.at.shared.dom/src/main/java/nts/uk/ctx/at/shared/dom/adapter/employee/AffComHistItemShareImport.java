package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AffComHistItemShareImport {
	/** 履歴ID */
	private String historyId;

	/** 出向先データである */
	private boolean destinationData;

	/** 所属期間 */
	private DatePeriod datePeriod;
}
