package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.SalarySegment;


/**
 * 社員の雇用期間
 * @author Hieult
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class EmploymentPeriod {
	
	/** 社員ID**/
	private String empID;
	
	/**雇用コード**/
	private String employmentCd;
	
	/** 期間 **/
	private DatePeriod datePeriod;
	
	/** 給与区分**/ 
	private final Optional<SalarySegment> otpSalarySegment;
}
