package nts.uk.ctx.at.record.dom.adapter.query.employee;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@Builder
public class HistoryCommonInfo {

	private DatePeriod range;
	
	private String code;
	
	private String employeeId;
}
