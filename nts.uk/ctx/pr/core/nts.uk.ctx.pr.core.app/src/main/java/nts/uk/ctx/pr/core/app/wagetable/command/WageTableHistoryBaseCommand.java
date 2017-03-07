/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableItemDto;

/**
 * The Class WageTableHistoryAddCommand.
 */
@Setter
@Getter
public abstract class WageTableHistoryBaseCommand {

	/** The code. */
	private String code;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

}
