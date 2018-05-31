/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;


import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class SearchEmployeeQuery.
 */
@Data
public class SearchEmployeeQuery {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The use closure. */
	private boolean useClosure;

	/** The closure id. */
	private Integer closureId;

	/** The system type. */
	private Integer systemType;

	/** The period. */
	private PeriodDto period;

	/** The reference date. */
	private GeneralDate referenceDate;

	/**
	 * Checks if is all closure.
	 *
	 * @return true, if is all closure
	 */
	public boolean isAllClosure() {
		return this.closureId == 0;
	}

	/**
	 * Gets the date period.
	 *
	 * @return the date period
	 */
	public DatePeriod getDatePeriod() {
		return new DatePeriod(this.period.getStartDate(), this.period.getEndDate());
	}

	/**
	 * Gets the reference date period.
	 *
	 * @return the reference date period
	 */
	public DatePeriod getReferenceDatePeriod() {
		return new DatePeriod(this.referenceDate, this.referenceDate);
	}
}
