package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeAndClosureOutput {

	private String employeeId;
	private int closureId;
	private DatePeriod period;
	private int lock;
}
