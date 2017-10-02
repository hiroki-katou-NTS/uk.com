/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.organization;

import lombok.Getter;

/**
 * The Class EmploymentImported.
 */
// 雇用
@Getter
public class EmploymentImported {

	/** The com id. */
	// 雇用コード
	private String comId;

	/** The emp cd. */
	// 会社ID
	private String empCd;

	/** The emp name. */
	// 雇用名称
	private String empName;

	/**
	 * Instantiates a new employment imported.
	 *
	 * @param comId the com id
	 * @param empCd the emp cd
	 * @param empName the emp name
	 */
	public EmploymentImported(String comId, String empCd, String empName) {
		super();
		this.comId = comId;
		this.empCd = empCd;
		this.empName = empName;
	}

}
