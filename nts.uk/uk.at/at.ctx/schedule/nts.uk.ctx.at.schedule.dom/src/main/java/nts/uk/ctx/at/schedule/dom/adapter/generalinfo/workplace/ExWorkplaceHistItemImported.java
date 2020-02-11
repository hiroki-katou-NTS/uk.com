package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class ExWorkplaceHistItemImported {

	private String historyId;

	private DatePeriod period;

	private String workplaceId;
}
