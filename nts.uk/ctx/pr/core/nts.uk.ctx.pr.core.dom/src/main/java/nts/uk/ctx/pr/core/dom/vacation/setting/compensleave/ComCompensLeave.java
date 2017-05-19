/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.compensleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CompanyCompensatoryLeave.
 */
@Getter
public class ComCompensLeave extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The setting. */
	private CompensatoryLeaveSetting setting;

	/**
	 * Instantiates a new company compensatory leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param setting
	 *            the setting
	 */
	public ComCompensLeave(String companyId, CompensatoryLeaveSetting setting) {
		super();
		this.companyId = companyId;
		this.setting = setting;
	}

}
