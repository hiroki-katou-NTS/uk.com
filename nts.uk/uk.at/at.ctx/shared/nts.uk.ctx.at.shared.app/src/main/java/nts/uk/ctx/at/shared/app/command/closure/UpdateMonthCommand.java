/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.closure;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * Instantiates a new update month command.
 */
@Data
public class UpdateMonthCommand {
	
	/** The company id. */
	private String companyId;
	
	/** The closure id. */
	private ClosureId closureId;

}
