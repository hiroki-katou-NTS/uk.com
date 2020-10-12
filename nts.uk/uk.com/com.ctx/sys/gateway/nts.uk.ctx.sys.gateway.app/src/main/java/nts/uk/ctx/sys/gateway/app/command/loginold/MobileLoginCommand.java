/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SubmitLoginFormOneCommand.
 */
public class MobileLoginCommand extends SubmitLoginFormThreeCommand {

	@Getter
	@Setter
	private boolean loginDirect = false;
	
	/**
	 * Instantiates a new submit login form one command.
	 */
	public MobileLoginCommand() {
		super();
	}

}
