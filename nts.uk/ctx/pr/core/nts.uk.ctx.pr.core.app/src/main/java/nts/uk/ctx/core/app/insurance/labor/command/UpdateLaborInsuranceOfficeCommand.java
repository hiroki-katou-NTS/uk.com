/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.core.app.insurance.labor.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;

// TODO: Auto-generated Javadoc
@Getter
@Setter
public class UpdateLaborInsuranceOfficeCommand {
	
	/** The labor insurance office. */
	private LaborInsuranceOffice laborInsuranceOffice;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public LaborInsuranceOffice toDomain() {
		return this.laborInsuranceOffice;
	}
}
