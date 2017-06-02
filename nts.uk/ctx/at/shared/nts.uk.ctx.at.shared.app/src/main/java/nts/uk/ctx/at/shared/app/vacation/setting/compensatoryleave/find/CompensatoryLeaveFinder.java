/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto.CompensatoryLeaveComSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompensatoryLeaveFinder {

	@Inject
	CompensLeaveComSetRepository compensLeaveComSetRepository;

	public CompensatoryLeaveComSettingDto findByCompanyId() {
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveComSetting findItem = compensLeaveComSetRepository.find(companyId);
		if (findItem == null) {
			return null;
		}
		CompensatoryLeaveComSettingDto dto = new CompensatoryLeaveComSettingDto();
		findItem.saveToMemento(dto);
		return dto;
	}
}
