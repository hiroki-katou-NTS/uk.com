/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class Period.
 */
@Data
public class PeriodDto {

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/**
	 * Instantiates a new period.
	 */
	public PeriodDto() {
		super();
	}

	/**
	 * Instantiates a new period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public PeriodDto(GeneralDate startDate, GeneralDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
