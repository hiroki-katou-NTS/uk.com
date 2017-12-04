package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeAndClosure {

	private String employeeId;
	private int closureId;
	private DatePeriod period;
	private int lock;
}
