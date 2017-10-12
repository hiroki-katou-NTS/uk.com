/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ClosureGetMonthDay.
 */
public class ClosureGetMonthDay {

	/** The Constant ONE_HUNDRED_COUNT. */
	public static final int ONE_HUNDRED_COUNT = 100;

	/** The Constant TOTAL_MONTH_OF_YEAR. */
	public static final int TOTAL_MONTH_OF_YEAR = 12;

	/** The Constant NEXT_DAY_MONT. */
	public static final int NEXT_DAY_MONTH = 1;

	/** The Constant ZERO_DAY_MONT. */
	public static final int ZERO_DAY_MONTH = 0;

	/** The Constant FORMAT_DATE. */
	public static final String FORMAT_DATE_STR = "yyyy/MM/dd";

	/** The end month. */
	private int month;

	/** The format date. */
	private SimpleDateFormat formatDate;

	/**
	 * Next day.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date nextDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, NEXT_DAY_MONTH);
		return cal.getTime();
	}

	/**
	 * Previous month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date previousDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, -NEXT_DAY_MONTH);
		return cal.getTime();
	}

	/**
	 * Next month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date nextMonth(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.MONTH, NEXT_DAY_MONTH);
		return cal.getTime();
	}

	/**
	 * Previous month.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date previousMonth(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.MONTH, -NEXT_DAY_MONTH);
		return cal.getTime();
	}

	/**
	 * To day.
	 *
	 * @param closureDate
	 *            the closure date
	 * @return the date
	 */
	public Date toDay(int closureDate) {
		int date = closureDate;
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		if (closureDate == ZERO_DAY_MONTH) {
			return this.toDate(year, month, NEXT_DAY_MONTH);
		}
		return this.toDate(year, month, date);
	}

	/**
	 * Last month.
	 *
	 * @return the date
	 */
	public Date lastMonth() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.previousDay(this.toDate(year, month + NEXT_DAY_MONTH, NEXT_DAY_MONTH));
	}

	/**
	 * Last month next month.
	 *
	 * @return the date
	 */
	public Date lastMonthNextMonth() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.previousDay(
				this.toDate(year, month + NEXT_DAY_MONTH + NEXT_DAY_MONTH, NEXT_DAY_MONTH));
	}

	/**
	 * Last month next month next.
	 *
	 * @return the date
	 */
	public Date lastMonthNextMonthNext() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.previousDay(this.toDate(year,
				month + NEXT_DAY_MONTH + NEXT_DAY_MONTH + NEXT_DAY_MONTH, NEXT_DAY_MONTH));
	}

	/**
	 * Begin month.
	 *
	 * @return the date
	 */
	public Date beginMonth() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.toDate(year, month, NEXT_DAY_MONTH);
	}

	/**
	 * Begin month next month.
	 *
	 * @return the date
	 */
	public Date beginMonthNextMonth() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.toDate(year, month + NEXT_DAY_MONTH, NEXT_DAY_MONTH);
	}

	/**
	 * Begin month next month next.
	 *
	 * @return the date
	 */
	public Date beginMonthNextMonthNext() {
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.toDate(year, month + NEXT_DAY_MONTH + NEXT_DAY_MONTH, NEXT_DAY_MONTH);
	}

	/**
	 * To date.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date toDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - NEXT_DAY_MONTH, day, ZERO_DAY_MONTH, ZERO_DAY_MONTH);
		return cal.getTime();
	}

	/**
	 * Gets the month day.
	 *
	 * @param date
	 *            the date
	 * @return the month day
	 */
	public int getMonthDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) + NEXT_DAY_MONTH) % TOTAL_MONTH_OF_YEAR;
	}

	/**
	 * Gets the day month.
	 *
	 * @param input
	 *            the input
	 * @return the day month
	 */
	public DatePeriod getDayMonth(ClosureDate input, int yearMonth) {

		this.month = yearMonth;
		Date today = this.toDay(input.getClosureDay().v());

		// check last month
		if (input.getLastDayOfMonth()) {
			return new DatePeriod(GeneralDate.legacyDate(this.beginMonth()),
					GeneralDate.legacyDate((this.lastMonth())));
		}

		// check equal month
		if (this.getMonthDay(today) == this.getMonthDate(this.month)) {
			return new DatePeriod(GeneralDate.legacyDate(this.nextDay(this.previousMonth(today))),
					GeneralDate.legacyDate(today));
		}

		// previous month
		this.month = this.month - NEXT_DAY_MONTH;
		today = this.toDay(input.getClosureDay().v());
		GeneralDate startDate = null;

		// check equal month
		if (this.getMonthDay(this.nextDay(today)) == this.getMonthDate(this.month)) {
			startDate = GeneralDate.legacyDate((this.nextDay(today)));
		} else {
			startDate = GeneralDate.legacyDate(this.beginMonthNextMonth());
		}

		return new DatePeriod(startDate, GeneralDate.legacyDate(this.lastMonthNextMonth()));
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
		this.month = yearMonth;
		// get to day change
		Date todayChange = this.toDay(inputChange.getClosureDay().v());

		// data res
		DayMonthChange change = new DayMonthChange();
		DatePeriod beforeClosureDate = null;
		DatePeriod afterClosureDate = null;

		// check last month
		if (input.getLastDayOfMonth() && inputChange.getLastDayOfMonth()) {
			beforeClosureDate = new DatePeriod(GeneralDate.legacyDate(this.beginMonth()),
					GeneralDate.legacyDate(this.lastMonth()));
			afterClosureDate = new DatePeriod(GeneralDate.legacyDate(this.beginMonthNextMonth()),
					GeneralDate.legacyDate(this.lastMonthNextMonth()));
			change.setBeforeClosureDate(beforeClosureDate);
			change.setAfterClosureDate(afterClosureDate);
			return change;
		}

		// check last month
		if (input.getLastDayOfMonth()) {
			GeneralDate beforeClosureStartDate = GeneralDate.legacyDate(this.beginMonth());
			GeneralDate beforeClosureEndDate = null;
			GeneralDate afterClosureStartDate = null;
			GeneralDate afterClosureEndDate = null;
			// check equal month
			if (this.getMonthDay(todayChange) != this.getMonthDate(this.month)) {
				beforeClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
				afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(this.lastMonth()));
				// next month
				this.month = this.month + 1;
				todayChange = this.toDay(inputChange.getClosureDay().v());
				// re check equal month
				if (this.getMonthDay(todayChange) != this.getMonthDate(this.month)) {
					afterClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
				} else {
					afterClosureEndDate = GeneralDate.legacyDate(todayChange);
				}
			} else {
				beforeClosureEndDate = GeneralDate.legacyDate(todayChange);
				afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(todayChange));
				afterClosureEndDate = GeneralDate.legacyDate(this.nextMonth(todayChange));
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
			this.month = this.month - 1;
			Date today = this.toDay(input.getClosureDay().v());
			// check equal month
			if (this.getMonthDay(this.nextDay(today)) == this.getMonthDate(this.month)) {
				beforeClosureDate = new DatePeriod(GeneralDate.legacyDate(this.nextDay(today)),
						GeneralDate.legacyDate(this.lastMonth()));
				afterClosureDate = new DatePeriod(
						GeneralDate.legacyDate(this.beginMonthNextMonth()),
						GeneralDate.legacyDate(this.lastMonthNextMonth()));
			} else {
				beforeClosureDate = new DatePeriod(
						GeneralDate.legacyDate(this.beginMonthNextMonth()),
						GeneralDate.legacyDate(this.lastMonthNextMonth()));
				afterClosureDate = new DatePeriod(
						GeneralDate.legacyDate(this.beginMonthNextMonthNext()),
						GeneralDate.legacyDate(this.lastMonthNextMonthNext()));
			}
			change.setBeforeClosureDate(beforeClosureDate);
			change.setAfterClosureDate(afterClosureDate);
			return change;
		}

		// change equal
		if (input.getClosureDay().v() == inputChange.getClosureDay().v()) {
			// previous month
			this.month = this.month - NEXT_DAY_MONTH;
			todayChange = this.toDay(input.getClosureDay().v());
			// check equal month
			if (this.getMonthDay(nextDay(todayChange)) == this.getMonthDate(this.month)) {
				beforeClosureDate = new DatePeriod(
						GeneralDate.legacyDate(this.nextDay(todayChange)),
						GeneralDate.legacyDate(this.nextMonth(todayChange)));

				GeneralDate afterClosureEndDate = null;

				// next next month
				this.month = this.month + NEXT_DAY_MONTH + NEXT_DAY_MONTH;
				todayChange = this.toDay(input.getClosureDay().v());
				// check equal month
				if (this.getMonthDay(todayChange) == this.getMonthDate(this.month)) {
					afterClosureEndDate = GeneralDate.legacyDate(todayChange);
				} else {
					afterClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
				}
				afterClosureDate = new DatePeriod(
						GeneralDate.legacyDate(this.nextDay(this.nextMonth(todayChange))),
						afterClosureEndDate);
			} else {
				// next month
				this.month = this.month + NEXT_DAY_MONTH;
				todayChange = this.toDay(input.getClosureDay().v());
				GeneralDate beforeClosureStartDate = GeneralDate.legacyDate(this.beginMonth());
				GeneralDate beforeClosureEndDate = null;
				GeneralDate afterClosureStartDate = null;
				GeneralDate afterClosureEndDate = null;
				// check equal month
				if (this.getMonthDay(todayChange) == this.getMonthDate(this.month)) {
					beforeClosureEndDate = GeneralDate.legacyDate(todayChange);
					afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(todayChange));
				} else {
					beforeClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
					afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(this.lastMonth()));
				}
				// next month
				this.month = this.month + NEXT_DAY_MONTH;
				todayChange = this.toDay(input.getClosureDay().v());
				if (this.getMonthDay(todayChange) == this.getMonthDate(this.month)) {
					afterClosureEndDate = GeneralDate.legacyDate(todayChange);
				} else {
					afterClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
				}
				beforeClosureDate = new DatePeriod(beforeClosureStartDate, beforeClosureEndDate);
				afterClosureDate = new DatePeriod(afterClosureStartDate, afterClosureEndDate);
			}
		} else {

			// previous month
			this.month = this.month - NEXT_DAY_MONTH;
			Date today = this.toDay(input.getClosureDay().v());
			todayChange = this.toDay(inputChange.getClosureDay().v());
			GeneralDate beforeClosureStartDate = null;
			GeneralDate beforeClosureEndDate = null;
			GeneralDate afterClosureStartDate = null;
			GeneralDate afterClosureEndDate = null;
			// check equal month
			if (this.getMonthDay(this.nextDay(today)) == this.getMonthDate(this.month)) {
				beforeClosureStartDate = GeneralDate.legacyDate(this.nextDay(today));
			} else {
				beforeClosureStartDate = GeneralDate.legacyDate(this.beginMonth());
			}

			if (input.getClosureDay().v() > inputChange.getClosureDay().v()) {
				this.month = this.month + NEXT_DAY_MONTH;
				todayChange = this.toDay(inputChange.getClosureDay().v());
			}
			// check equal month
			if (this.getMonthDay(todayChange) == this.getMonthDate(this.month)) {
				beforeClosureEndDate = GeneralDate.legacyDate(todayChange);
				afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(todayChange));
			} else {
				beforeClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
				afterClosureStartDate = GeneralDate.legacyDate(this.nextDay(this.lastMonth()));
			}

			// next month
			this.month = this.month + NEXT_DAY_MONTH;
			todayChange = this.toDay(inputChange.getClosureDay().v());

			// check equal month
			if (this.getMonthDay(todayChange) == this.getMonthDate(this.month)) {
				afterClosureEndDate = GeneralDate.legacyDate(todayChange);
			} else {
				afterClosureEndDate = GeneralDate.legacyDate(this.lastMonth());
			}
			beforeClosureDate = new DatePeriod(beforeClosureStartDate, beforeClosureEndDate);
			afterClosureDate = new DatePeriod(afterClosureStartDate, afterClosureEndDate);
		}
		change.setBeforeClosureDate(beforeClosureDate);
		change.setAfterClosureDate(afterClosureDate);
		return change;
	}

	/**
	 * Gets the month date.
	 *
	 * @param month
	 *            the month
	 * @return the month date
	 */
	private int getMonthDate(int month) {
		return (month % ONE_HUNDRED_COUNT) % TOTAL_MONTH_OF_YEAR;
	}

	/**
	 * Format date.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public String formatDate(Date date) {
		return this.formatDate.format(date);
	}

}
