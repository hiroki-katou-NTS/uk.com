/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ClosureGetMonthDay.
 */
public class ClosureGetMonthDay {

	/** The Constant NEXT_DAY_MONT. */
	public static final int NEXT_DAY_MONTH = 1;

	/** The Constant ZERO_DAY_MONT. */
	public static final int ZERO_DAY_MONTH = 0;

	/** The end month. */
	private YearMonth yearMonth;

	/**
	 * Next day.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public GeneralDate nextDay(GeneralDate day) {
		return day.nextValue(true);
	}

	/**
	 * Previous month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public GeneralDate previousDay(GeneralDate day) {
		return day.nextValue(false);
	}

	/**
	 * Next month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public GeneralDate nextMonth(GeneralDate day) {
		return day.addMonths(NEXT_DAY_MONTH);
	}

	/**
	 * Previous month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public GeneralDate previousMonth(GeneralDate day) {
		return day.addMonths(-NEXT_DAY_MONTH);
	}

	/**
	 * To day.
	 *
	 * @param closureDate
	 *            the closure date
	 * @return the date
	 */
	public GeneralDate toDay(int closureDate) {
		int date = closureDate;
		if (closureDate == ZERO_DAY_MONTH) {
			return GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH);
		}
		return GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), date);
	}

	/**
	 * Last month.
	 *
	 * @return the date
	 */
	public GeneralDate lastMonth() {
		return this.previousDay(
				GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH)
						.addMonths(NEXT_DAY_MONTH));
	}

	/**
	 * Last month next month.
	 *
	 * @return the date
	 */
	public GeneralDate lastMonthNextMonth() {
		return this.previousDay(
				GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH)
						.addMonths(NEXT_DAY_MONTH + NEXT_DAY_MONTH));
	}

	/**
	 * Last month next month next.
	 *
	 * @return the date
	 */
	public GeneralDate lastMonthNextMonthNext() {
		return this.previousDay(
				GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH)
						.addMonths(NEXT_DAY_MONTH + NEXT_DAY_MONTH + NEXT_DAY_MONTH));
	}

	/**
	 * Begin month.
	 *
	 * @return the date
	 */
	public GeneralDate beginMonth() {
		return GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH);
	}

	/**
	 * Begin month next month.
	 *
	 * @return the date
	 */
	public GeneralDate beginMonthNextMonth() {
		return GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH)
				.addMonths(NEXT_DAY_MONTH);
	}

	/**
	 * Begin month next month next.
	 *
	 * @return the date
	 */
	public GeneralDate beginMonthNextMonthNext() {
		return GeneralDate.ymd(this.yearMonth.year(), this.yearMonth.month(), NEXT_DAY_MONTH)
				.addMonths(NEXT_DAY_MONTH + NEXT_DAY_MONTH);
	}

	/**
	 * Gets the day month.
	 *
	 * @param input
	 *            the input
	 * @return the day month
	 */
	public DatePeriod getDayMonth(ClosureDate input, int yearMonth) {
		this.yearMonth = YearMonth.of(yearMonth);
		GeneralDate today = this.toDay(input.getClosureDay().v());

		// check last month
		if (input.getLastDayOfMonth()) {
			return new DatePeriod(this.beginMonth(), this.lastMonth());
		}

		// check equal month
		if (today.month() == this.yearMonth.month()) {
			if (this.previousMonth(today).day() < today.day()) {
				return new DatePeriod(this.previousMonth(today), today);
			}

			return new DatePeriod(this.nextDay(this.previousMonth(today)), today);
		}

		// previous month
		this.yearMonth = this.yearMonth.previousMonth();
		today = this.toDay(input.getClosureDay().v());
		GeneralDate startDate = null;

		// check equal month
		if (this.nextDay(today).month() == this.yearMonth.month()) {
			startDate = this.nextDay(today);
		} else {
			startDate = this.beginMonthNextMonth();
		}

		return new DatePeriod(startDate, this.lastMonthNextMonth());
	}

	/**
	 * Gets the day month change.
	 *
	 * @param input
	 *            the input
	 * @return the day month change
	 */
	public DayMonthChange getDayMonthChange(ClosureDate input, ClosureDate inputChange,
			int yearMonth) {
		// set month
		this.yearMonth = YearMonth.of(yearMonth);
		// get to day change
		GeneralDate todayChange = this.toDay(inputChange.getClosureDay().v());

		// data res
		DayMonthChange change = new DayMonthChange();
		DatePeriod beforeClosureDate = null;
		DatePeriod afterClosureDate = null;

		// check last month
		if (input.getLastDayOfMonth() && inputChange.getLastDayOfMonth()) {
			beforeClosureDate = new DatePeriod(this.beginMonth(), this.lastMonth());
			afterClosureDate = new DatePeriod(this.beginMonthNextMonth(),
					this.lastMonthNextMonth());
			change.setBeforeClosureDate(beforeClosureDate);
			change.setAfterClosureDate(afterClosureDate);
			return change;
		}

		// check last month
		if (input.getLastDayOfMonth()) {
			GeneralDate beforeClosureStartDate = this.beginMonth();
			GeneralDate beforeClosureEndDate = null;
			GeneralDate afterClosureStartDate = null;
			GeneralDate afterClosureEndDate = null;
			// check equal month
			if (todayChange.month() != this.yearMonth.month()) {
				beforeClosureEndDate = this.lastMonth();
				afterClosureStartDate = this.nextDay(this.lastMonth());
				// next month
				this.yearMonth = this.yearMonth.addMonths(1);
				todayChange = this.toDay(inputChange.getClosureDay().v());
				// re check equal month
				if (todayChange.month() != this.yearMonth.month()) {
					afterClosureEndDate = this.lastMonth();
				} else {
					afterClosureEndDate = todayChange;
				}
			} else {
				beforeClosureEndDate = todayChange;
				afterClosureStartDate = this.nextDay(todayChange);
				afterClosureEndDate = this.nextMonth(todayChange);
			}
			beforeClosureDate = new DatePeriod(beforeClosureStartDate, beforeClosureEndDate);
			afterClosureDate = new DatePeriod(afterClosureStartDate, afterClosureEndDate);
			change.setBeforeClosureDate(beforeClosureDate);
			change.setAfterClosureDate(afterClosureDate);
			return change;
		}

		// check last date
		if (inputChange.getLastDayOfMonth()) {
			// previous month
			this.yearMonth = this.yearMonth.previousMonth();
			GeneralDate today = this.toDay(input.getClosureDay().v());
			// check equal month
			if (this.nextDay(today).month() == this.yearMonth.month()) {
				beforeClosureDate = new DatePeriod(this.nextDay(today), this.lastMonth());
				afterClosureDate = new DatePeriod(this.beginMonthNextMonth(),
						this.lastMonthNextMonth());
			} else {
				beforeClosureDate = new DatePeriod(this.beginMonthNextMonth(),
						this.lastMonthNextMonth());
				afterClosureDate = new DatePeriod(this.beginMonthNextMonthNext(),
						this.lastMonthNextMonthNext());
			}
			change.setBeforeClosureDate(beforeClosureDate);
			change.setAfterClosureDate(afterClosureDate);
			return change;
		}

		// change equal
		if (input.getClosureDay().v() == inputChange.getClosureDay().v()) {
			// previous month
			this.yearMonth = this.yearMonth.previousMonth();
			todayChange = this.toDay(input.getClosureDay().v());
			// check equal month
			if (nextDay(todayChange).month() == this.yearMonth.month()) {
				beforeClosureDate = new DatePeriod(this.nextDay(todayChange),
						this.nextMonth(todayChange));

				GeneralDate afterClosureEndDate = null;

				// next next month
				this.yearMonth = this.yearMonth.addMonths(2);
				todayChange = this.toDay(input.getClosureDay().v());
				// check equal month
				if (todayChange.month() == this.yearMonth.month()) {
					afterClosureEndDate = todayChange;
				} else {
					afterClosureEndDate = this.lastMonth();
				}
				afterClosureDate = new DatePeriod(this.nextDay(this.nextMonth(todayChange)),
						afterClosureEndDate);
			} else {
				// next month
				this.yearMonth = this.yearMonth.nextMonth();
				todayChange = this.toDay(input.getClosureDay().v());
				GeneralDate beforeClosureStartDate = this.beginMonth();
				GeneralDate beforeClosureEndDate = null;
				GeneralDate afterClosureStartDate = null;
				GeneralDate afterClosureEndDate = null;
				// check equal month
				if (todayChange.month() == this.yearMonth.month()) {
					beforeClosureEndDate = todayChange;
					afterClosureStartDate = this.nextDay(todayChange);
				} else {
					beforeClosureEndDate = this.lastMonth();
					afterClosureStartDate = this.nextDay(this.lastMonth());
				}
				// next month
				this.yearMonth = this.yearMonth.nextMonth();
				todayChange = this.toDay(input.getClosureDay().v());
				// check equal month
				if (todayChange.month() == this.yearMonth.month()) {
					afterClosureEndDate = todayChange;
				} else {
					afterClosureEndDate = this.lastMonth();
				}
				beforeClosureDate = new DatePeriod(beforeClosureStartDate, beforeClosureEndDate);
				afterClosureDate = new DatePeriod(afterClosureStartDate, afterClosureEndDate);
			}
		} else {

			// previous month
			this.yearMonth = this.yearMonth.previousMonth();
			GeneralDate today = this.toDay(input.getClosureDay().v());
			todayChange = this.toDay(inputChange.getClosureDay().v());
			GeneralDate beforeClosureStartDate = null;
			GeneralDate beforeClosureEndDate = null;
			GeneralDate afterClosureStartDate = null;
			GeneralDate afterClosureEndDate = null;
			// check equal month
			if (this.nextDay(today).month() == this.yearMonth.month()) {
				beforeClosureStartDate = this.nextDay(today);
			} else {
				beforeClosureStartDate = this.beginMonth();
			}

			if (input.getClosureDay().v() > inputChange.getClosureDay().v()) {
				// Next month
				this.yearMonth = this.yearMonth.nextMonth();
				todayChange = this.toDay(inputChange.getClosureDay().v());
			}
			// check equal month
			if (todayChange.month() == this.yearMonth.month()) {
				beforeClosureEndDate = todayChange;
				afterClosureStartDate = this.nextDay(todayChange);
			} else {
				beforeClosureEndDate = this.lastMonth();
				afterClosureStartDate = this.nextDay(this.lastMonth());
			}

			// next month
			this.yearMonth = this.yearMonth.nextMonth();
			todayChange = this.toDay(inputChange.getClosureDay().v());

			// check equal month
			if (todayChange.month() == this.yearMonth.month()) {
				afterClosureEndDate = todayChange;
			} else {
				afterClosureEndDate = this.lastMonth();
			}
			beforeClosureDate = new DatePeriod(beforeClosureStartDate, beforeClosureEndDate);
			afterClosureDate = new DatePeriod(afterClosureStartDate, afterClosureEndDate);
		}
		change.setBeforeClosureDate(beforeClosureDate);
		change.setAfterClosureDate(afterClosureDate);
		return change;
	}

	// public static void main(String[] args) {
	// ClosureGetMonthDay test = new ClosureGetMonthDay();
	// ClosureDate input = new ClosureDate(28, false);
	// int yearMonth = 201804;
	// DatePeriod period = test.getDayMonth(input, yearMonth);
	// System.out.println(period.start().toString() + " - " +
	// period.end().toString());
	// }

}
