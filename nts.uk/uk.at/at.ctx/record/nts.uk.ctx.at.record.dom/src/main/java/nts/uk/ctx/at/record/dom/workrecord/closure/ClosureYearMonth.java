/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonthHolder;

/**
 * The Class ClosureYearMonth.
 */
public class ClosureYearMonth extends IntegerPrimitiveValue<ClosureYearMonth>
	implements YearMonthHolder {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new closure year month.
	 *
	 * @param rawValue the raw value
	 */
	public ClosureYearMonth(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * Of.
	 *
	 * @param year the year
	 * @param month the month
	 * @return the closure year month
	 */
	public static ClosureYearMonth of(int year, int month) {
		return new ClosureYearMonth(year * 100 + month);
	}

	/**
	 * Create instance.
	 * 
	 * @param yearMonth
	 *            year*100 + month
	 * @return instance
	 */
	public static ClosureYearMonth of(int yearMonth) {
		return new ClosureYearMonth(yearMonth);
	}

	@Override
	public int year() {
		return this.v() / 100;
	}

	@Override
	public int month() {
		return this.v() % 100;
	}

	/**
	 * Adds the months.
	 *
	 * @param monthsToAdd the months to add
	 * @return the closure year month
	 */
	public ClosureYearMonth addMonths(int monthsToAdd) {
		Integer value = this.v();
		value = value + monthsToAdd;
		return this.of(value);
	}

	/**
	 * Returns next month.
	 * 
	 * @return next month
	 */
	public ClosureYearMonth nextMonth() {
		return this.addMonths(1);
	}

	/**
	 * Returns previous month.
	 * 
	 * @return previous month
	 */
	public ClosureYearMonth previousMonth() {
		return this.addMonths(-1);
	}

	/* (non-Javadoc)
	 * @see nts.arc.primitive.PrimitiveValueBase#validate()
	 */
	@Override
	public void validate() {
		boolean valid = DateTimeConstraints.LIMIT_YEAR.contains(this.year())
			&& DateTimeConstraints.LIMIT_MONTH.contains(this.month());
		if (!valid) {
			throw new RuntimeException("'" + this.v() + "' is invalid as YearMonth");
		}
	}
}
