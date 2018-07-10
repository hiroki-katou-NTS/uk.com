/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingFinder.
 */
@Stateless
public class CompanyWtSettingFinder {

	/** The repository. */
	@Inject
	private CompanyWtSettingRepository repository;

	/**
	 * Find.
	 *
	 * @return the company setting dto
	 */
	public CompanyWtSettingDto find(int year) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();
		
		Optional<CompanyWtSetting> optCompanySetting = this.repository.find(companyId, year);
		// Update mode.
		if(optCompanySetting.isPresent()) {
			return CompanyWtSettingDto.fromDomain(optCompanySetting.get());
		}
		// New mode.
		return null;
	}
}
