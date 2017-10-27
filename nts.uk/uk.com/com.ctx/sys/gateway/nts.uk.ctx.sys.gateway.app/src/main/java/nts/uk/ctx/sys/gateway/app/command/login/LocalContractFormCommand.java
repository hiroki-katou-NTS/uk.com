/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class LocalContractFormCommand.
 */
@Getter
@Setter
public class LocalContractFormCommand {

	/** The contract code. */
	private String contractCode;
	
	/** The contract password. */
	private String contractPassword;
}
