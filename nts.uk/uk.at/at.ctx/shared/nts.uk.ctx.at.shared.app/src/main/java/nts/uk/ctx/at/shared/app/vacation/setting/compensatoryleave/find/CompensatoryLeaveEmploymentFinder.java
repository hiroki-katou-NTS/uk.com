/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto.CompensatoryLeaveEmSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompensatoryLeaveEmploymentFinder.
 */
@Stateless
public class CompensatoryLeaveEmploymentFinder {

	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;

	/**
	 * Find by company id.
	 *
	 * @return the compensatory leave em setting dto
	 */
	public CompensatoryLeaveEmSettingDto findByEmploymentCode(String employmentCode) {
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveEmSetting findItem = compensLeaveEmSetRepository.find(companyId,employmentCode);
		if (findItem == null) {
			return null;
		}
		CompensatoryLeaveEmSettingDto compensatoryLeaveEmSettingDto = new CompensatoryLeaveEmSettingDto();
		findItem.saveToMemento(compensatoryLeaveEmSettingDto);
		return compensatoryLeaveEmSettingDto;
	}
}
