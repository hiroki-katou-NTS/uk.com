package nts.uk.ctx.pr.transfer.dom.adapter.employment;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 所属雇用履歴
 * @author HungTT
 */
@Getter
public class EmploymentHistImport {

	/** 社員ID */
	private String employeeId;
	/** 雇用コード */
	private String employmentCode;
	/** 期間 */
	private DatePeriod period;
	
	public EmploymentHistImport(String employeeId, String employmentCode, DatePeriod period){
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.period = period;
	}
	
	public EmploymentHistImport(String employeeId, String employmentCode, GeneralDate start, GeneralDate end){
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.period = new DatePeriod(start, end);
	}
}
