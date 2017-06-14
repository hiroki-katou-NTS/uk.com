/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompanySettingFinder.
 */
@Stateless
public class CompanySettingFinder {

	/** The repository. */
	@Inject
	private CompanySettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/**
	 * Find.
	 *
	 * @return the company setting dto
	 */
	public CompanySettingDto find(int year) {
		Optional<CompanySetting> optCompanySetting = this.repository.find(companyId, year);
		if(optCompanySetting.isPresent()) {
			return CompanySettingDto.fromDomain(optCompanySetting.get());
		}
		// TODO ko co du lieu thi vao new mode.
		return null;
	}
}
