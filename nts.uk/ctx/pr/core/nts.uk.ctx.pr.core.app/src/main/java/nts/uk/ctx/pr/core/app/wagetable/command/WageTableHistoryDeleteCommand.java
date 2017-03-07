/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WageTableHistoryDeleteCommand.
 */
@Setter
@Getter
public class WageTableHistoryDeleteCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The wage table code. */
	private String wageTableCode;

	/** The history id. */
	private String historyId;

}
