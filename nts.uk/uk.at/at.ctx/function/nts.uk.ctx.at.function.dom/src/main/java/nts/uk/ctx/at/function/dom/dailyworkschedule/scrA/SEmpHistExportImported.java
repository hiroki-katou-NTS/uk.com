/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule.scrA;

import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


/**
 * Gets the employment name.
 *
 * @return the employment name
 * @author HoangDD
 */
@Getter
public class SEmpHistExportImported {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The period. */
	// 配属期間
	private DatePeriod period;

	/** The employment code. */
	// 雇用コード
	private String employmentCode;
	
	/** The employment name. */
	// 雇用名称
	private String employmentName;

	/**
	 * Instantiates a new s emp hist export imported.
	 *
	 * @param employeeId the employee id
	 * @param period the period
	 * @param employmentCode the employment code
	 * @param employmentName the employment name
	 */
	public SEmpHistExportImported(String employeeId, DatePeriod period, String employmentCode, String employmentName) {
		super();
		this.employeeId = employeeId;
		this.period = period;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
	}
}
