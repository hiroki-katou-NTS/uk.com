/**
 * 
 */
package nts.uk.ctx.at.auth.app.find.employmentrole.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author hieult
 *
 */
@Value
@Setter
@Getter
public class DateProcessed {
	private int closureID;
	private YearMonth targetDate;
	private DatePeriod datePeriod;
	
}
