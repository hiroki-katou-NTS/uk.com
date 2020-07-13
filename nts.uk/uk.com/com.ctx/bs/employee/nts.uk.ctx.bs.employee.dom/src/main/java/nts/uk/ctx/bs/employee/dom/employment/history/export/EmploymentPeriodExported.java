package nts.uk.ctx.bs.employee.dom.employment.history.export;

import java.util.Optional;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentCode;
import nts.uk.ctx.bs.employee.dom.employment.history.SalarySegment;
/**
 * 社員の雇用期間Exported
 * @author Hieult
 *
 */
@Getter

@RequiredArgsConstructor
public class EmploymentPeriodExported {
	
	/**社員ID **/ 
	private final String empID; 
	/**期間 **/ 
	private final DatePeriod datePeriod;
	/**雇用コード **/
	private final EmploymentCode employmentCd;
	/**給与区分 **/
	private final Optional<SalarySegment> otpSalarySegment;
	
	//[C-0] 社員の雇用期間Exported( 社員ID, 期間, 雇用コード, Optional<給与区分> )
}
