/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.dom.adapter.bs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class ConcurrentEmployeeExport.
 */
@Getter
@AllArgsConstructor
// 指定職位の本務兼務社員
public class ConcurrentEmployeeImport {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The employee cd. */
	// 社員コード
	private String employeeCd;

	/** The person name. */
	// 個人名
	private String personName;

	/** The job id. */
	// 職位ID
	private String jobId;

	/** The job cls. */
	// 本務兼務区分
	private Integer jobCls;
	
	public boolean isConcurrent() {
		return this.jobCls == 1;
	}
}
