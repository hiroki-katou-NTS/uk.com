/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.shift.estimate.usagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.shift.estimate.usagesetting.dto.UsageSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UsageSettingFinder.
 */
@Stateless
public class UsageSettingFinder {

	/** The common guideline setting repo. */
	@Inject
	private UsageSettingRepository commonGuidelineSettingRepo;

	/**
	 * Find by company code.
	 *
	 * @return the common guideline setting dto
	 */
	public UsageSettingDto findByCompanyId() {

		String companyId = AppContexts.user().companyId();

		Optional<UsageSetting> optUsageSetting = this.commonGuidelineSettingRepo
				.findByCompanyId(companyId);

		if (!optUsageSetting.isPresent()) {
			return null;
		}

		UsageSettingDto commonGuidelineSettingDto = new UsageSettingDto();

		optUsageSetting.get().saveToMemento(commonGuidelineSettingDto);

		return commonGuidelineSettingDto;
	}

}
