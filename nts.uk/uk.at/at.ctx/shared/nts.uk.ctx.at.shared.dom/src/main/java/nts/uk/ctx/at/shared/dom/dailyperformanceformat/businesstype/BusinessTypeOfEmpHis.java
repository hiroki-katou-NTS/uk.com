package nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class BusinessTypeOfEmpHis {
	private String companyId;

	private String employeeId;

	private String historyId;

	private DatePeriod period;
}
