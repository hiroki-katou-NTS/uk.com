/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.labor.command.dto.LaborInsuranceOfficeDeleteDto;
import nts.uk.ctx.pr.core.app.insurance.labor.command.dto.LaborInsuranceOfficeDto;

/**
 * The Class LaborInsuranceOfficeDeleteCommand.
 */
@Getter
@Setter
public class LaborInsuranceOfficeDeleteCommand {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The labor insurance office delete dto. */
	private LaborInsuranceOfficeDeleteDto laborInsuranceOfficeDeleteDto;

}
