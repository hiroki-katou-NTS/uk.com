/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationAcquisitionRuleFinder.
 */
@Stateless
public class AcquisitionRuleFinder {

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
	public AcquisitionRuleDto find() {
		String companyId = AppContexts.user().companyId();

		Optional<AcquisitionRule> vaAcRule = repo.findById(companyId);

		if (!vaAcRule.isPresent()) {
			return null;
		}

		AcquisitionRuleDto dto = AcquisitionRuleDto.builder().build();
		vaAcRule.get().saveToMemento(dto);
		return dto;
	}
}
