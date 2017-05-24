/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;

/**
 * The Class VacationAcquisitionRuleFinder.
 */
@Stateless
public class VacationAcquisitionRuleFinder {

	/** The repo. */
	@Inject
	private AcquisitionRuleRepository repo;

	/**
	 * Find.
	 *
	 * @param companyId
	 *            the company id
	 * @return the optional
	 */
	public Optional<VacationAcquisitionRuleDto> find(String companyId) {
		Optional<AcquisitionRule> vaAcRule = repo.findById(companyId);

		VacationAcquisitionRuleDto dto = VacationAcquisitionRuleDto.builder().build();

		if (vaAcRule.isPresent()) {
			vaAcRule.get().saveToMemento(dto);
		}

		return Optional.ofNullable(dto);
	}

}
