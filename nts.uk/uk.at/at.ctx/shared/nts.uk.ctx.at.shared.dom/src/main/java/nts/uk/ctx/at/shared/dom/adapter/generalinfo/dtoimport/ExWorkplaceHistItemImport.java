package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkplaceHistItemImport {

	private String historyId;
	
	private DatePeriod period;
	
	private String workplaceId;
}
