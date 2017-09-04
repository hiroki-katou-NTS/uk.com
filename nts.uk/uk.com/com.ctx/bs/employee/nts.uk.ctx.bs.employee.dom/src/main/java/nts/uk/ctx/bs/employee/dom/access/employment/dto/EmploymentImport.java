/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.employment.dto;

import lombok.Data;

/**
 * The Class AcEmploymentDto.
 */
@Data
public class EmploymentImport {

	/** The company id. */
	private String companyId;

	/** The work closure id. */
	private Integer workClosureId;

	/** The salary closure id. */
	private Integer salaryClosureId;

	/** The employment code. */
	private String employmentCode;

	/** The employment name. */
	private String employmentName;

	/**
	 * Instantiates a new ac employment dto.
	 *
	 * @param companyId
	 *            the company id
	 * @param workClosureId
	 *            the work closure id
	 * @param salaryClosureId
	 *            the salary closure id
	 * @param employmentCode
	 *            the employment code
	 * @param employmentName
	 *            the employment name
	 */
	public EmploymentImport(String companyId, Integer workClosureId, Integer salaryClosureId,
			String employmentCode, String employmentName) {
		super();
		this.companyId = companyId;
		this.workClosureId = workClosureId;
		this.salaryClosureId = salaryClosureId;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
	}

}
