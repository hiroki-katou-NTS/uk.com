/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableHeadDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableHistoryDto;

/**
 * The Class WageTableHistoryAddCommand.
 */
@Setter
@Getter
public class WtHistoryBaseCommand {

	/** The wage table head dto. */
	private WageTableHeadDto wageTableHeadDto;

	/** The wage table history dto. */
	private WageTableHistoryDto wageTableHistoryDto;

}
