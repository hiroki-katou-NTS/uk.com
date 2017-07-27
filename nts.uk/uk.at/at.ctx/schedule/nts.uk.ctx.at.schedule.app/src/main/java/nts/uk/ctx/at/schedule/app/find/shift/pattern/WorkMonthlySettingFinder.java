/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkMonthlySettingFinder.
 */
@Stateless
public class WorkMonthlySettingFinder {
	
	/** The Constant START_DAY. */
	public static final int START_DAY = 1;
	
	/** The Constant NEXT_DAY. */
	public static final int NEXT_DAY = 1;
	
	/** The Constant YEAR_MUL. */
	public static final int YEAR_MUL = 10000;
	
	/** The Constant MONTH_MUL. */
	public static final int MONTH_MUL = 100;
	
	/** The repository. */
	@Inject
	private WorkMonthlySettingRepository repository;
	
	/**
	 * Find by month.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @param yearMonth the year month
	 * @return the list
	 */
	
	public List<WorkMonthlySettingDto> findByMonth(String monthlyPatternCode, int yearMonth){

		// month setting by connection
		YearMonth monthSetting = YearMonth.of(yearMonth);
		
		// set start month
		GeneralDate startMonth = GeneralDate.ymd(monthSetting.year(), monthSetting.month(),
				START_DAY);
		
		// set end month
		GeneralDate endMonth = GeneralDate.ymd(monthSetting.nextMonth().year(),
				monthSetting.nextMonth().month(), START_DAY);
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// call repository find by month
		List<WorkMonthlySettingDto> workMonthlySettings = this.repository
				.findByStartEndDate(companyId, monthlyPatternCode, this.getYearMonthDay(startMonth),
						this.getYearMonthDay(endMonth))
				.stream()
				.map(domain -> {
					WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
					domain.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
		
		// convert to map
		Map<Integer, WorkMonthlySettingDto> map = workMonthlySettings.stream()
				.collect(Collectors.toMap((settings) -> {
					return settings.getYmdk();
				}, Function.identity()));

		List<WorkMonthlySettingDto> resDataWorkMonthlySetting  = new ArrayList<>();
		
		// loop in month setting
		while (this.getYearMonth(startMonth) == yearMonth) {
			// check exist data
			if (map.containsKey(this.getYearMonthDay(startMonth))) {
				resDataWorkMonthlySetting.add(map.get(this.getYearMonthDay(startMonth)));
			} else {
				// data default
				WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
				WorkMonthlySetting domain = new WorkMonthlySetting(
						BigDecimal.valueOf(this.getYearMonthDay(startMonth)), monthlyPatternCode);
				domain.saveToMemento(dto);
				resDataWorkMonthlySetting.add(dto);
			}
			startMonth = startMonth.addDays(NEXT_DAY);
		}
		return resDataWorkMonthlySetting;
	}
	
	/**
	 * Gets the year month day.
	 *
	 * @param baseDate the base date
	 * @return the year month day
	 */
	public int getYearMonthDay(GeneralDate baseDate){
		return baseDate.year() * YEAR_MUL + baseDate.month() * MONTH_MUL + baseDate.day();
	}
	
	/**
	 * Gets the year month.
	 *
	 * @param baseDate the base date
	 * @return the year month
	 */
	public int getYearMonth(GeneralDate baseDate){
		return baseDate.year() * MONTH_MUL + baseDate.month();
	}

}
