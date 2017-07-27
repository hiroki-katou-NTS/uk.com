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

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WeeklyWorkSettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
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
	
	/** The work monthly setting repository. */
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepository;
	
	/** The weekly work setting finder. */
	@Inject
	private WeeklyWorkSettingFinder weeklyWorkSettingFinder;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingBatchSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		MonthlyPatternSettingBatchSaveCommand command = context.getCommand();
		
		// startDate
		Date toStartDate = this.toDate(command.getStartYearMonth() * MONTH_MUL + NEXT_DAY);
		
		// check by next day of begin end
		while (this.getYearMonth(toStartDate) <= command.getEndYearMonth()) {
						
			Optional<PublicHoliday> publicHoliday = this.publicHolidayRepository.getHolidaysByDate(
					companyId, new BigDecimal(this.getYearMonthDate(toStartDate)));

			// find by id
			Optional<WorkMonthlySetting> workMonthlySetting = this.workMonthlySettingRepository
					.findById(companyId, command.getMonthlyPatternCode(),
							this.getYearMonthDate(toStartDate));
			// check day is public holiday
			if (publicHoliday.isPresent()) {

				// check exist data
				if (!workMonthlySetting.isPresent()) {
					this.workMonthlySettingRepository.add(command.toDomainPublicHolidays(companyId,
							this.getYearMonthDate(toStartDate)));
				} else if (command.isOverwrite()) {
					this.workMonthlySettingRepository.update(command.toDomainPublicHolidays(companyId,
							this.getYearMonthDate(toStartDate)));
				}
			} else {
				WeeklyWorkSettingDto dto = this.weeklyWorkSettingFinder
						.checkWeeklyWorkSetting(this.getBaseDate(toStartDate));
				// is work day
				switch (EnumAdaptor.valueOf(dto.getWorkdayDivision(), WorkdayDivision.class)) {
				case WORKINGDAYS:
					if (!workMonthlySetting.isPresent()) {
						this.workMonthlySettingRepository.add(command.toDomainWorkDays(companyId,
								this.getYearMonthDate(toStartDate)));
					} else if (command.isOverwrite()) {
						this.workMonthlySettingRepository.update(command.toDomainWorkDays(companyId,
								this.getYearMonthDate(toStartDate)));
					}
					break;

				case NON_WORKINGDAY_EXTRALEGAL:
					if (!workMonthlySetting.isPresent()) {
						this.workMonthlySettingRepository.add(command.toDomainNoneStatutoryHolidays(
								companyId, this.getYearMonthDate(toStartDate)));
					} else if (command.isOverwrite()) {
						this.workMonthlySettingRepository
								.update(command.toDomainNoneStatutoryHolidays(companyId,
										this.getYearMonthDate(toStartDate)));
					}
					break;
				case NON_WORKINGDAY_INLAW:
					if (!workMonthlySetting.isPresent()) {
						this.workMonthlySettingRepository.add(command.toDomainStatutoryHolidays(
								companyId, this.getYearMonthDate(toStartDate)));
					} else if (command.isOverwrite()) {
						this.workMonthlySettingRepository.update(command.toDomainStatutoryHolidays(
								companyId, this.getYearMonthDate(toStartDate)));
					}
					break;
				}
			}
			toStartDate = this.nextDay(toStartDate);
		}

	}
	
	/** The Constant NEXT_DAY. */
	public static final int NEXT_DAY = 1;
	
	/** The Constant BEGIN_END_MONTH. */
	public static final int BEGIN_END_MONTH = 12;
	
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
		return cal.get(Calendar.YEAR) * YEAR_MUL + (cal.get(Calendar.MONTH) + NEXT_DAY) * MONTH_MUL
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
	 * Gets the year month.
	 *
	 * @param day the day
	 * @return the year month
	 */
	public GeneralDate getBaseDate(Date day) {
		Calendar cal = Calendar.getInstance();
		return GeneralDate.ymd(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + NEXT_DAY),
				cal.get(Calendar.DAY_OF_MONTH));
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
