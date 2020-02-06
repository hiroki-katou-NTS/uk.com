package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.HistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentAppHistoryItem extends HistoryItem<DatePeriod, GeneralDate> {
	
	/** 履歴ID */
	private String historyId;

	/** 所属期間 */
	private DatePeriod datePeriod;

	@Override
	public DatePeriod span() {
		return datePeriod;
	}

	@Override
	public String identifier() {
		return historyId;
	}

	@Override
	public void changeSpan(DatePeriod newSpan) {
		datePeriod = newSpan;
	}

}
