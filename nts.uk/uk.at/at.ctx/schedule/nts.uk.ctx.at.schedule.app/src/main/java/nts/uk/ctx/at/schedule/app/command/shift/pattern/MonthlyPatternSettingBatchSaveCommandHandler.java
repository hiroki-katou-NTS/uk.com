/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternSettingBatchSaveCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingBatchSaveCommandHandler
		extends CommandHandler<MonthlyPatternSettingBatchSaveCommand> {
	
	/** The public holiday repository. */
	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	
	/** The Weekly work setting repository. */
	@Inject
	private WeeklyWorkSettingRepository WeeklyWorkSettingRepository; 

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingBatchSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get employee id
		String employeeId = loginUserContext.employeeId();
		
		// get command
		MonthlyPatternSettingBatchSaveCommand command = context.getCommand();
		
		// validate command
		if (validate(command)) {
			return;
		}

		// startDate
		Date toStartDate = this.toDate(command.getStartYearMonth() * MONTH_MUL + NEXT_DAY);
		
		// check by next day of begin end
		while(this.getYearMonth(this.nextDay(toStartDate)) <= command.getEndYearMonth()){
			Optional<PublicHoliday> publicHoliday = this.publicHolidayRepository.getHolidaysByDate(
					companyId, new BigDecimal(this.getYearMonthDate(toStartDate)));
			
			// check day is public holiday
			if(publicHoliday.isPresent()){
				
			}else {
				
			}
		}
		
	}
	
	/**
	 * Validate.
	 *
	 * @param command the command
	 * @return true, if successful
	 */
	public boolean validate(MonthlyPatternSettingBatchSaveCommand command) {
		return false;
	}

	
	
	/** The Constant NEXT_DAY. */
	public static final int NEXT_DAY = 1;
	
	/** The Constant FORMAT_DATE_STR. */
	public static final String FORMAT_DATE_STR = "yyyy/MM/dd";

	/** The Constant ZERO_DAY_MONTH. */
	public static final int ZERO_DAY_MONTH = 0;
	
	/** The Constant YEAR_MUL. */
	public static final int YEAR_MUL = 10000;
	
	/** The Constant MONTH_MUL. */
	public static final int MONTH_MUL = 100;

	/**
	 * Next day.
	 *
	 * @param day the day
	 * @return the date
	 */
	public Date nextDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, NEXT_DAY); 
		return cal.getTime();
	}
	
	/**
	 * Previous day.
	 *
	 * @param day the day
	 * @return the date
	 */
	public Date previousDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, -NEXT_DAY); 
		return cal.getTime();
	}
	
	/**
	 * Gets the year month date.
	 *
	 * @param day the day
	 * @return the year month date
	 */
	public int getYearMonthDate(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		return cal.get(Calendar.YEAR) * YEAR_MUL + cal.get(Calendar.MONTH) * MONTH_MUL
				+ cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Gets the year month.
	 *
	 * @param day the day
	 * @return the year month
	 */
	public int getYearMonth(Date day) {
		return getYearMonthDate(day) / MONTH_MUL;
	}
	
	/**
	 * To date.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @return the date
	 */
	public Date toDate(int yearMonthDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearMonthDate / YEAR_MUL, (yearMonthDate % YEAR_MUL) / MONTH_MUL - NEXT_DAY,
				yearMonthDate % MONTH_MUL, ZERO_DAY_MONTH, ZERO_DAY_MONTH);
		return cal.getTime();
	}
	
	
}
