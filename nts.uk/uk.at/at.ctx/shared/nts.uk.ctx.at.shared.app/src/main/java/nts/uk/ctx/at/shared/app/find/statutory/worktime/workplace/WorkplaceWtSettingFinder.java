/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceWtSettingFinder.
 */
@Stateless
public class WorkplaceWtSettingFinder {

	/** The repository. */
	@Inject
	private WorkPlaceWtSettingRepository repository;

	/**
	 * Find.
	 *
	 * @param request
	 *            the request
	 * @return the workplace wt setting dto
	 */
	public WorkplaceWtSettingDto find(WorkplaceWtSettingRequest request) {
		/** The company id. */
		String companyId = AppContexts.user().companyId();

		Optional<WorkPlaceWtSetting> optWorkplaceWtSetting = this.repository.find(companyId,
				request.getYear(), request.getWorkplaceId());
		// Update mode.
		if (optWorkplaceWtSetting.isPresent()) {
			return WorkplaceWtSettingDto.fromDomain(optWorkplaceWtSetting.get());
		}
		// New mode.
		return null;
	}

	/**
	 * Find all.
	 *
	 * @param year
	 *            the year
	 * @return the list
	 */
	public List<String> findAll(int year) {
		return this.repository.findAll(AppContexts.user().companyId(), year);
	}
}
