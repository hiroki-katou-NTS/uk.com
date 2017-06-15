/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.DayMonthChangeDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.DayMonthChangeInDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.DayMonthDto;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.DayMonthInDto;

/**
 * The Class ClosureDateFinder.
 */

@Getter
@Setter
public class ClosureDateFinder {

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

	/** The begin closure date. */
	private int beginClosureDate;

	/** The end month. */
	private int month;

	/** The closure date. */
	private int closureDate;
	
	/** The format date. */
	private SimpleDateFormat formatDate;

	/**
	 * Instantiates a new closure date finder.
	 */
	public ClosureDateFinder() {
		super();
		this.formatDate = new SimpleDateFormat(FORMAT_DATE_STR);
	}


	/**
	 * Next day.
	 *
	 * @param day the day
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
	 * @param day the day
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
	 * @param day the day
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
	 * @param day the day
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
	 * @param closureDate the closure date
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
	public Date lastMonth(){
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.previousDay(this.toDate(year, month + NEXT_DAY_MONTH, NEXT_DAY_MONTH));
	}
	
	
	/**
	 * Begin month.
	 *
	 * @return the date
	 */
	public Date beginMonth(){
		int year = this.month / ONE_HUNDRED_COUNT;
		int month = this.month % ONE_HUNDRED_COUNT;
		return this.toDate(year, month, NEXT_DAY_MONTH);
	}
	
	/**
	 * To date.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
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
	 * @param date the date
	 * @return the month day
	 */
	public int getMonthDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + NEXT_DAY_MONTH;
	}

	/**
	 * Gets the day month.
	 *
	 * @param input the input
	 * @return the day month
	 */
	public DayMonthDto getDayMonth(DayMonthInDto input){
		this.setBeginClosureDate(input.getClosureDate());
		this.setMonth(input.getMonth());
		Date today = this.toDay(input.getClosureDate());
		DayMonthDto dto = new DayMonthDto();
		if (this.getMonthDay(today) == this.getMonth() % ONE_HUNDRED_COUNT) {
			dto.setBeginDay(this.formatDate(this.previousMonth(this.nextDay(today))));
			dto.setEndDay(this.formatDate(today));
		} else {
			dto.setBeginDay(this.formatDate(this.beginMonth()));
			dto.setEndDay(this.formatDate(this.lastMonth()));
		}
		return dto;
	}
	
	/**
	 * Gets the day month change.
	 *
	 * @param input the input
	 * @return the day month change
	 */
	public DayMonthChangeDto getDayMonthChange(DayMonthChangeInDto input){
		this.setMonth(input.getMonth());
		Date today = this.toDay(input.getClosureDate());
		DayMonthChangeDto changeDto = new DayMonthChangeDto();
		DayMonthDto beforeClosureDate = new DayMonthDto();
		DayMonthDto afterClosureDate = new DayMonthDto();
		if (input.getChangeClosureDate() == input.getClosureDate()) {
			if (this.getMonthDay(today) == this.getMonth() % ONE_HUNDRED_COUNT) {
				beforeClosureDate
						.setBeginDay(this.formatDate(this.previousMonth(this.nextDay(today))));
				beforeClosureDate.setEndDay(this.formatDate(today));
				afterClosureDate
						.setBeginDay(this.formatDate(this.previousMonth(this.nextDay(today))));
				afterClosureDate.setEndDay(this.formatDate(today));
			} else {
				beforeClosureDate.setBeginDay(this.formatDate(this.beginMonth()));
				beforeClosureDate.setEndDay(this.formatDate(this.lastMonth()));
				afterClosureDate.setBeginDay(this.formatDate(this.beginMonth()));
				afterClosureDate.setEndDay(this.formatDate(this.lastMonth()));
			}
		}else {
			 Date todayChange = this.toDay(input.getChangeClosureDate());
			if (this.getMonthDay(today) == this.getMonth() % ONE_HUNDRED_COUNT) {
				beforeClosureDate
						.setBeginDay(this.formatDate(this.previousMonth(this.nextDay(today))));
			} else {
				beforeClosureDate.setBeginDay(this.formatDate(this.beginMonth()));
			}
			
			if(input.getClosureDate() > input.getChangeClosureDate()){
				if (this.getMonthDay(todayChange) == this.getMonth() % ONE_HUNDRED_COUNT) {
					beforeClosureDate.setEndDay(this.formatDate(todayChange));
				} else {
					beforeClosureDate.setEndDay(this.formatDate(this.lastMonth()));
				}
			}else {
				beforeClosureDate.setEndDay(this.formatDate(this.previousMonth(todayChange)));
			}
			
			if (this.getMonthDay(todayChange) == this.getMonth() % ONE_HUNDRED_COUNT) {
				afterClosureDate.setBeginDay(this.formatDate(this.nextDay(todayChange)));
				if (this.getMonthDay(this.nextMonth(
						todayChange)) == (this.getMonth() % ONE_HUNDRED_COUNT + NEXT_DAY_MONTH)
								% TOTAL_MONTH_OF_YEAR) {
					afterClosureDate.setEndDay(this.formatDate(this.nextMonth(todayChange)));
				}
				else {
					afterClosureDate.setEndDay(this.formatDate(this.nextMonth(this.lastMonth())));
				}
			}else {
				afterClosureDate.setBeginDay(this.formatDate(this.nextMonth(this.beginMonth())));
				afterClosureDate.setEndDay(this.formatDate(this.nextMonth(this.lastMonth())));
			}
			
		}
		changeDto.setBeforeClosureDate(beforeClosureDate);
		changeDto.setAfterClosureDate(afterClosureDate);
		return changeDto;
	}
	
	/**
	 * Format date.
	 *
	 * @param date the date
	 * @return the string
	 */
	public String formatDate(Date date){
		return this.formatDate.format(date);
	}
}
