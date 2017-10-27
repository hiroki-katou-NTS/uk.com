/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SubmitContractFormCommand.
 */
@Getter
@Setter
public class SubmitContractFormCommand {

	/** The contract code. */
	private String contractCode;
	
	/** The password. */
	private String password;
}
