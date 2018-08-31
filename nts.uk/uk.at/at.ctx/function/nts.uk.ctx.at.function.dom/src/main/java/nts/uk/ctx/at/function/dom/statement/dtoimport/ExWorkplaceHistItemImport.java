package nts.uk.ctx.at.function.dom.statement.dtoimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkplaceHistItemImport {

	private String historyId;
	
	private DatePeriod period;
	
	private String workplaceId;
}
