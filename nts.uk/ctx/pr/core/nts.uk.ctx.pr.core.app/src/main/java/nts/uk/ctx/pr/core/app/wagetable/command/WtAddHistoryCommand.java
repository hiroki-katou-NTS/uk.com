/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WageTableHistoryAddCommand.
 */
@Setter
@Getter
public class WtAddHistoryCommand {

	// Header
	/** The name. */
	private String code;

	/** The start month. */
	private Integer startMonth;

}
