package nts.uk.ctx.at.shared.dom.adapter.employment;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author ThanhNX
 *
 *         所属雇用履歴
 */
@Getter
public class EmploymentHistShareImport {

	/** 社員ID */
	private String employeeId;
	/** 雇用コード */
	private String employmentCode;
	/** 期間 */
	private DatePeriod period;

	public EmploymentHistShareImport(String employeeId, String employmentCode, DatePeriod period) {
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.period = period;
	}

	public EmploymentHistShareImport(String employeeId, String employmentCode, GeneralDate start, GeneralDate end) {
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.period = new DatePeriod(start, end);
	}
}
