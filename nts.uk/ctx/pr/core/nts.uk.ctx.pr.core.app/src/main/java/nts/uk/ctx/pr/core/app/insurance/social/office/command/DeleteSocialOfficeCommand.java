/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import lombok.Data;

/**
 * Instantiates a new delete social office command.
 */
@Data
public class DeleteSocialOfficeCommand {
	
	/** The insurance office code. */
	String insuranceOfficeCode;
}
