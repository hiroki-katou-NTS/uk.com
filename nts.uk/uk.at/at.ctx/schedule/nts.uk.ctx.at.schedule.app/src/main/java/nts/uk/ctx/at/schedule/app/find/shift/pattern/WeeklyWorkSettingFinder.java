/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.UserInfoDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WeeklyWorkSettingFinder.
 */
@Stateless
public class WeeklyWorkSettingFinder {

	/** The repository. */
	@Inject
	WeeklyWorkSettingRepository repository;
	
	
	/**
	 * Check weekly work setting.
	 *
	 * @param baseDate the base date
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
				dayOfWeek);
		if (weeklyWorkSetting.isPresent()) {
			weeklyWorkSetting.get().saveToMemento(dto);
		}
		return dto;
	}
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<WeeklyWorkSettingDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// return data
		return this.repository.findAll(companyId).stream().map(domain -> {
			WeeklyWorkSettingDto dto = new WeeklyWorkSettingDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Gets the user info.
	 *
	 * @return the user info
	 */
	public UserInfoDto getUserInfo() {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();

		// data return
		UserInfoDto dto = new UserInfoDto();
		dto.setCompanyId(companyId);
		dto.setEmployeeId(employeeId);
		return dto;
	}
}
