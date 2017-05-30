/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.common.history;

import java.util.Date;

/**
 * The Class Period.
 */
public class Period {

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/**
	 * Instantiates a new period.
	 */
	public Period() {
	}

	/**
	 * Instantiates a new period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public Period(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
