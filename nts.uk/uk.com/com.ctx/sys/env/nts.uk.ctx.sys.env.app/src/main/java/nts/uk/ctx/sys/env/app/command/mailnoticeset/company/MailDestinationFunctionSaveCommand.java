/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto.MailDestinationFunctionDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;

@Getter
@Setter
public class MailDestinationFunctionSaveCommand {

	/** The mail destination function dto. */
	private MailDestinationFunctionDto mailDestinationFunctionDto;

	/**
	 * To domain.
	 *
	 * @return the mail destination function
	 */
	public MailDestinationFunction toDomain() {
		return new MailDestinationFunction(this.mailDestinationFunctionDto);
	}

	/**
	 * Instantiates a new mail destination function save command.
	 */
	public MailDestinationFunctionSaveCommand() {
		super();
	}

}
