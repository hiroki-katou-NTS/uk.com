/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.compensleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmploymentCompensatoryLeave.
 */
@Getter
public class EmpCompensLeave extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The emp contract type. */
	private String empContractTypeCode;

	/** The setting. */
	private CompensatoryLeaveSetting setting;

	/**
	 * Instantiates a new emp compens leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param empContractTypeCode
	 *            the emp contract type code
	 * @param setting
	 *            the setting
	 */
	public EmpCompensLeave(String companyId, String empContractTypeCode,
			CompensatoryLeaveSetting setting) {
		super();
		this.companyId = companyId;
		this.empContractTypeCode = empContractTypeCode;
		this.setting = setting;
	}

}
