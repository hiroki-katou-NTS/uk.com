/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class SEmpHistExport.
 */
@Data
@Builder
// 社員所属雇用履歴を取得
public class SEmpHistoryImport {

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

	public SEmpHistoryImport(String employeeId, String employmentCode, String employmentName, DatePeriod period) {
		super();
		this.employeeId = employeeId;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
		this.period = period;
	}
	
	

}
