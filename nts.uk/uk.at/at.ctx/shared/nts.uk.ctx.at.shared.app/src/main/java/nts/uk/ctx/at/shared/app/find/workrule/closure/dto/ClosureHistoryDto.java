/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ClosureHistoryDto.
 */
@Getter
@Setter
public class ClosureHistoryDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The close name. */
	private String closeName;

	/** The closure id. */
	private int closureId;

	/** The end date. */
	private int endDate;

	/** The closure date. */
	private int closureDate;

	/** The start date. */
	private int startDate;
}
