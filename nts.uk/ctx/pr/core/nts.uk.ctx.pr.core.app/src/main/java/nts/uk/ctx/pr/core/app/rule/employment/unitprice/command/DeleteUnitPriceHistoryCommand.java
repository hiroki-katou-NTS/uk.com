/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DeleteUnitPriceHistoryCommand.
 */
@Getter
@Setter
public class DeleteUnitPriceHistoryCommand {

	/** The unit price code. */
	private String unitPriceCode;

	/** The id. */
	private String id;
}
