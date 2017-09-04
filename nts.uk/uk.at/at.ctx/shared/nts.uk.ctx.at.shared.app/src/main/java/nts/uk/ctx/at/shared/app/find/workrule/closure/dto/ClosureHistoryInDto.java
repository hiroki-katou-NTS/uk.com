/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ClosureHistoryInDto.
 */
@Getter
@Setter
public class ClosureHistoryInDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The closure id. */
	private int closureId;

	/** The end date. */
	private int endDate;

	/** The start date. */
	private int startDate;
}
