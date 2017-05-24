/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.find.vacation.setting.acquisitionrule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRule;

@Stateless
public class VacationAcquisitionRuleFinder {
	
	@Inject
	private AcquisitionRuleRepository repo;
	
	public Optional<VacationAcquisitionRuleDto> find(String companyId) {
		Optional<AcquisitionRule> vaAcRule = repo.findById(companyId);
		VacationAcquisitionRuleDto dto = VacationAcquisitionRuleDto.builder().build();
		if (vaAcRule.isPresent()) {
			vaAcRule.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

}
