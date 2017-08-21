/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.shift.estimate.guideline;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.shift.estimate.guideline.dto.CommonGuidelineSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSettingRepository;
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

		String companyId = AppContexts.user().companyId();

		Optional<CommonGuidelineSetting> optCommonGuidelineSetting = this.commonGuidelineSettingRepo
				.findByCompanyId(companyId);

		if (!optCommonGuidelineSetting.isPresent()) {
			return null;
		}

		CommonGuidelineSettingDto commonGuidelineSettingDto = new CommonGuidelineSettingDto();

		optCommonGuidelineSetting.get().saveToMemento(commonGuidelineSettingDto);

		return commonGuidelineSettingDto;
	}

}
