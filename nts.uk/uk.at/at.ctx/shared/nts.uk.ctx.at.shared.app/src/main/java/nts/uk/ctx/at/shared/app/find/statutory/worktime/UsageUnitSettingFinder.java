/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class UsageUnitSettingFinder.
 */
@Stateless
public class UsageUnitSettingFinder {

	/** The repository. */
	@Inject
	private UsageUnitSettingRepository repository;
	
	/**
	 * Gets the setting.
	 *
	 * @return the setting
	 */
	public UsageUnitSettingDto getSetting() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// call repository
		Optional<UsageUnitSetting> setting = this.repository.findByCompany(companyId);
		
		UsageUnitSettingDto dto = new UsageUnitSettingDto();
		
		// to data object
		if(setting.isPresent()){
			setting.get().saveToMemento(dto);
		}
		
		return dto;

	}
}
