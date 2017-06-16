/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingFinder.
 */
@Stateless
public class CompanyWtSettingFinder {

	/** The repository. */
	@Inject
	private CompanyWtSettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/**
	 * Find.
	 *
	 * @return the company setting dto
	 */
	public CompanyWtSettingDto find(int year) {
		Optional<CompanyWtSetting> optCompanySetting = this.repository.find(companyId, year);
		if(optCompanySetting.isPresent()) {
			return CompanyWtSettingDto.fromDomain(optCompanySetting.get());
		}
		// TODO ko co du lieu thi vao new mode.
		return null;
	}
}
