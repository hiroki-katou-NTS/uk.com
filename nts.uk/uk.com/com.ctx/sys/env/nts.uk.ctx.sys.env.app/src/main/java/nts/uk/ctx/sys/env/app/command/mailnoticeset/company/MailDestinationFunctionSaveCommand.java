/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import lombok.Data;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto.MailDestinationFunctionDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;

@Data
public class MailDestinationFunctionSaveCommand {

	/** The fixed work setting. */
	private MailDestinationFunctionDto mailDestinationFunctionDto;

	/**
	 * To domain.
	 *
	 * @return the mail destination function
	 */
	public MailDestinationFunction toDomain() {
		return new MailDestinationFunction(this.mailDestinationFunctionDto);
	}

}
