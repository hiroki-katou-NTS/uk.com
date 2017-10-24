/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpConditionFinder.
 */
@Stateless
public class EmpConditionFinder {

	/** The repo. */
	@Inject
	private EmpConditionRepository repo;

	/** The adapter. */
	@Inject
	private EmploymentAdapter adapter;

	/**
	 * Find.
	 *
	 * @return the emp condition dto
	 */
	public EmpConditionDto find(String itemNo) {
		String comId = AppContexts.user().companyId();

		Map<String, String> employments = this.adapter.getAllEmployment(comId).stream()
				.collect(Collectors.toMap(EmploymentImported::getEmpCd, EmploymentImported::getEmpName));

		EmpConditionDto dto = new EmpConditionDto();
		Optional<EmpCondition> dom = this.repo.find(comId, itemNo);
		if (dom.isPresent()) {
			dom.get().saveToMemento(dto);

			// Set employment name.
			//dto.getEmpConditions().forEach(item -> item.setEmpName(employments.get(item.getEmpCd())));
			dto.getEmpConditions().forEach(item -> item.setEmpName("mock data")); //mock data
		}
		return dto;
	}
}
