/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto.CompensatoryLeaveComSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompensatoryLeaveFinder.
 */
@Stateless
public class CompensatoryLeaveFinder {

	/** The compens leave com set repository. */
	@Inject
	CompensLeaveComSetRepository compensLeaveComSetRepository;

	public CompensatoryLeaveComSettingDto findByCompanyId() {
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveComSetting findItem = compensLeaveComSetRepository.find(companyId);
		if (findItem == null) {
			return null;
		}
		CompensatoryLeaveComSettingDto dto = CompensatoryLeaveComSettingDto.toDto(findItem);
		return dto;
	}
}
