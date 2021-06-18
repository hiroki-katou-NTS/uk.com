/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto.CompensatoryLeaveEmSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CompensatoryLeaveEmploymentFinder.
 */
@Stateless
public class CompensatoryLeaveEmploymentFinder {

	/** The compens leave em set repository. */
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
		CompensatoryLeaveEmSettingDto dto = CompensatoryLeaveEmSettingDto.toDto(findItem);
		return dto;
	}
	
	/**
	 * Find all employment.
	 *
	 * @return the list
	 */
	public List<String> findAllEmployment() {
		String companyId = AppContexts.user().companyId();
		return compensLeaveEmSetRepository.findAll(companyId).stream().map(item -> item.getEmploymentCode().v())
				.collect(Collectors.toList());
	}
}
