/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SaveWindowAccountCommand.
 */
@Setter
@Getter
public class SaveWindowAccountCommand {

	private WindowAccountDto winAcc1;
	private WindowAccountDto winAcc2;
	private WindowAccountDto winAcc3;
	private WindowAccountDto winAcc4;
	private WindowAccountDto winAcc5;
	
	private String userId;
	
}
