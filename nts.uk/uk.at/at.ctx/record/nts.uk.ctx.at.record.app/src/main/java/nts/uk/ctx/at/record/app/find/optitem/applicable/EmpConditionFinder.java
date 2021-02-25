/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.organization.EmploymentImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpApplicableAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionRepository;
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
	 * @param itemNo the item no
	 * @return the emp condition dto
	 */
	public EmpConditionDto find(Integer itemNo) {
		String comId = AppContexts.user().companyId();

		List<EmploymentImported> employments = this.adapter.getAllEmployment(comId).stream()
				.collect(Collectors.toList());

		// find EmploymentCondition
		EmpCondition dom = this.repo.find(comId, itemNo);

		// to map
		Map<String, Integer> empConditions = dom.getEmpConditions().stream()
				.collect(Collectors.toMap(k -> k.getEmpCd(), v -> v.getEmpApplicableAtr().value));

		EmpConditionDto dto = new EmpConditionDto();
		dom.saveToMemento(dto);
		dto.getEmpConditions().clear();

		employments.forEach(emp -> {
			// default value of EmpApplicableAtr == APPLY
			EmploymentConditionDto empC = new EmploymentConditionDto(emp.getEmpCd(), EmpApplicableAtr.APPLY.value);
			empC.setEmpName(emp.getEmpName());

			// set emp condition
			Integer applicable = empConditions.get(empC.getEmpCd());
			if (applicable != null) {
				empC.setEmpApplicableAtr(applicable);
			}

			dto.getEmpConditions().add(empC);
		});

		return dto;
	}
}
