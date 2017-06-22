/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.employment.find;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentWtSettingFinder.
 */
@Stateless
public class EmploymentWtSettingFinder {

	/** The repository. */
	@Inject
	private EmploymentWtSettingRepository repository;

	/** The company id. */
	String companyId = AppContexts.user().companyId();

	/**
	 * Find.
	 *
	 * @param request the request
	 * @return the employment wt setting dto
	 */
	public EmploymentWtSettingDto find(EmploymentWtSettingRequest request) {
		Optional<EmploymentWtSetting> optEmploymentSetting = this.repository.find(companyId, request.getYear(),
				request.getEmploymentCode());
		// Update mode.
		if (optEmploymentSetting.isPresent()) {
			return EmploymentWtSettingDto.fromDomain(optEmploymentSetting.get());
		}
		// New mode.
		return null;
	}

	/**
	 * Findall.
	 *
	 * @param year the year
	 * @return the list
	 */
	public List<String> findall(int year) {
		return this.repository.findAll(companyId, year);
	}
}
