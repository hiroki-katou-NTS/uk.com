package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class ExEmploymentHistItemImported {
	
	private String historyId;

	private DatePeriod period;

	private String employmentCode;
}
