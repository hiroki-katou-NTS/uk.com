/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingAllDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WeeklyWorkSettingFinder.
 */
@Stateless
public class WeeklyWorkSettingFinder {

	/** The repository. */
	@Inject
	private WeeklyWorkSettingRepository repository;

	/**
	 * Check weekly work setting.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the weekly work setting dto
	 */
	public WeeklyWorkSettingDto checkWeeklyWorkSetting(GeneralDate baseDate) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get day of week by input base date
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate.date());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		// return data
		WeeklyWorkSettingDto dto = new WeeklyWorkSettingDto();
		Optional<WeeklyWorkSetting> weeklyWorkSetting = this.repository.findById(companyId,
				this.converNtsDayOfWeek(dayOfWeek).value);
		if (weeklyWorkSetting.isPresent()) {
			weeklyWorkSetting.get().saveToMemento(dto);
		}
		return dto;
	}

	/**
	 * Conver nts day of week.
	 *
	 * @param dayOfWeek
	 *            the day of week
	 * @return the day of week
	 */
	private DayOfWeek converNtsDayOfWeek(int dayOfWeek) {
		switch (dayOfWeek) {
		case Calendar.MONDAY:
			return DayOfWeek.MONDAY;

		case Calendar.TUESDAY:
			return DayOfWeek.TUESDAY;

		case Calendar.WEDNESDAY:
			return DayOfWeek.WEDNESDAY;

		case Calendar.THURSDAY:
			return DayOfWeek.THURSDAY;

		case Calendar.FRIDAY:
			return DayOfWeek.FRIDAY;

		case Calendar.SATURDAY:
			return DayOfWeek.SATURDAY;

		case Calendar.SUNDAY:
			return DayOfWeek.SUNDAY;

		default:
			throw new RuntimeException("Convert DayOfWeek fail!");
		}
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public WeeklyWorkSettingAllDto findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// data all company
		List<WeeklyWorkSetting> weeklyWorkSettings = this.repository.findAll(companyId);

		WeeklyWorkSettingAllDto dto = new WeeklyWorkSettingAllDto();

		weeklyWorkSettings.forEach(weekly -> {

			switch (weekly.getDayOfWeek()) {
			case MONDAY:
				dto.setMonday(weekly.getWorkdayDivision().value);
				break;
			case TUESDAY:
				dto.setTuesday(weekly.getWorkdayDivision().value);
				break;
			case WEDNESDAY:
				dto.setWednesday(weekly.getWorkdayDivision().value);
				break;
			case THURSDAY:
				dto.setThursday(weekly.getWorkdayDivision().value);
				break;
			case FRIDAY:
				dto.setFriday(weekly.getWorkdayDivision().value);
				break;
			case SATURDAY:
				dto.setSaturday(weekly.getWorkdayDivision().value);
				break;
			case SUNDAY:
				dto.setSunday(weekly.getWorkdayDivision().value);
				break;
			default:
				break;
			}
		});

		return dto;
	}

}
