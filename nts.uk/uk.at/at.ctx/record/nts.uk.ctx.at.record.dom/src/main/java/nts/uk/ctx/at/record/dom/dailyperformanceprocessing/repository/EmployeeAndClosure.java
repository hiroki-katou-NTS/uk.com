package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import lombok.Value;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class EmployeeAndClosure {

	private String employeeId;
	private int closureId;
	private DatePeriod period;
}
