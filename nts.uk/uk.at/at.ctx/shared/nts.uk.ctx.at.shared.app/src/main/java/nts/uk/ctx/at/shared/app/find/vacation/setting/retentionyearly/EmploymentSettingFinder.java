/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.dto.EmploymentSettingFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentSettingFinder.
 */
@Stateless
public class EmploymentSettingFinder {

	/** The repository. */
	@Inject
	private EmploymentSettingRepository repository;

	/**
	 * Find.
	 *
	 * @param employmentCode the employment code
	 * @return the employment setting find dto
	 */
	public EmploymentSettingFindDto find(String employmentCode) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();

		EmploymentSettingFindDto outputData = new EmploymentSettingFindDto();

		Optional<EmptYearlyRetentionSetting> emptYearlyRetentionSetting = this.repository.find(companyId,
				employmentCode);

		if (!emptYearlyRetentionSetting.isPresent()) {
			return null;
		}
		emptYearlyRetentionSetting.get().saveToMemento(outputData);
		return outputData;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<EmploymentSettingFindDto> findAll() {
		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();

		// Get Company Id
		String companyId = loginUserContext.companyId();

		List<EmptYearlyRetentionSetting> empSettingList = this.repository.findAll(companyId);
		return empSettingList.stream().map(empoyment -> {
			EmploymentSettingFindDto dto = new EmploymentSettingFindDto();
			empoyment.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
