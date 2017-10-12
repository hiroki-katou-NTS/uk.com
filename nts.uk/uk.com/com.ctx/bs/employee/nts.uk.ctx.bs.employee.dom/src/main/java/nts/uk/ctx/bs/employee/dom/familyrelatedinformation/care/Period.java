package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care;

import nts.arc.time.GeneralDate;

public class Period {
	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/**
	 * Instantiates a new period.
	 */
	public Period() {
		super();
	}

	/**
	 * Instantiates a new period.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 */
	public Period(GeneralDate startDate, GeneralDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
