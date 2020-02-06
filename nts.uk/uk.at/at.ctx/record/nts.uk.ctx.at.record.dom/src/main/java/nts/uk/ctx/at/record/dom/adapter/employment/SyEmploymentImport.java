package nts.uk.ctx.at.record.dom.adapter.employment;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class SyEmploymentImport {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The job title code. */
	// 雇用コード
	private String employmentCode;

	/** The job title name. */
	// 雇用名称
	private String employmentName;

	/** The period. */
	// 配属期間 
	private DatePeriod period;

	public SyEmploymentImport(String employeeId, String employmentCode, String employmentName, DatePeriod period) {
		super();
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
		this.period = period;
	}
}
