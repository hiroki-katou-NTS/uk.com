/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpConditionFinder.
 */
@Stateless
public class EmpConditionFinder {

	@Inject
	private EmpConditionRepository repo;

	/**
	 * Find.
	 *
	 * @return the emp condition dto
	 */
	public EmpConditionDto find(String itemNo) {
		EmpConditionDto dto = new EmpConditionDto();
		Optional<EmpCondition> dom = this.repo.find(AppContexts.user().companyId(), itemNo);
		if (dom.isPresent()) {
			dom.get().saveToMemento(dto);
		}
		return dto;
	}
}
