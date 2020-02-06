package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class DayMonthChange {
	/** The before closure date. */
	private DatePeriod beforeClosureDate;
	
	
	/** The after closure date. */
	private DatePeriod afterClosureDate;

}
