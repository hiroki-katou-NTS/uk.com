/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.RetentionYearlyFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class RetentionYearlyFinder.
 */
@Stateless
public class RetentionYearlyFinder {

	/** The repository. */
	@Inject
	private RetentionYearlySettingRepository repository;
	
	/**
	 * Find by id.
	 *
	 * @return the retention yearly find dto
	 */
	public RetentionYearlyFindDto findById() {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companycode by user login
		String companyId = loginUserContext.companyId();
		
		RetentionYearlyFindDto outputData = new RetentionYearlyFindDto();
		
		Optional<RetentionYearlySetting> data = this.repository.findByCompanyId(companyId);
		if(data.isPresent()) {
			data.get().saveToMemento(outputData);
			return outputData;
		}
		return null;
	}
}
