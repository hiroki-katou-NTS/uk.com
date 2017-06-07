/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.EmploymentSettingFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
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
		
		Optional<EmploymentSetting> employmentSetting = this.repository.find(companyId, employmentCode);
		
		if(employmentSetting.isPresent()) {
			employmentSetting.get().saveToMemento(outputData);
			return outputData;
		}
		return null;
	}
}
