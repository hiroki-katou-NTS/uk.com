package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle;

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
public class ExJobTitleHistItemImported {

	private String historyId;

	private DatePeriod period;

	private String jobTitleId;
}
