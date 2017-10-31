/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.commonset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.commonset.dto.CommonGuidelineSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CommonGuidelineSettingFinder.
 */
@Stateless
public class CommonGuidelineSettingFinder {

	/** The common guideline setting repo. */
	@Inject
	private CommonGuidelineSettingRepository commonGuidelineSettingRepo;

	/**
	 * Find by company code.
	 *
	 * @return the common guideline setting dto
	 */
	public CommonGuidelineSettingDto findByCompanyId() {
		// Get the company id.
		String companyId = AppContexts.user().companyId();

		Optional<CommonGuidelineSetting> optCommonGuidelineSetting = this.commonGuidelineSettingRepo
				.findByCompanyId(companyId);

		// Create dto
		CommonGuidelineSettingDto commonGuidelineSettingDto = new CommonGuidelineSettingDto();

		// Check exist
		if (optCommonGuidelineSetting.isPresent()) {
			// Convert to dto
			optCommonGuidelineSetting.get().saveToMemento(commonGuidelineSettingDto);
		}

		// Return
		return commonGuidelineSettingDto;
	}

}
