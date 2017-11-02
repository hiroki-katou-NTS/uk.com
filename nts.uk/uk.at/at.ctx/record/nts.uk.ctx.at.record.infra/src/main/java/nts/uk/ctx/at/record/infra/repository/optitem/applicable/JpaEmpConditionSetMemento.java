/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmploymentCondition;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaEmpConditionSetMemento.
 */
public class JpaEmpConditionSetMemento implements EmpConditionSetMemento {

	/** The type values. */

	@Getter
	private List<KrcstApplEmpCon> typeValues;

	/**
	 * Instantiates a new jpa emp condition set memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmpConditionSetMemento(List<KrcstApplEmpCon> entities) {
		this.typeValues = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setEmpConditions(java.util.List)
	 */
	@Override
	public void setEmpConditions(List<EmploymentCondition> empConditions) {
		Map<String, Integer> mapped = empConditions.stream()
				.collect(Collectors.toMap(EmploymentCondition::getEmpCd, value -> value.getEmpApplicableAtr().value));

		this.typeValues = this.typeValues.stream().map(item -> {
			item.setEmpApplAtr(mapped.get(item.getKrcstApplEmpConPK().getEmpCd()));
			return item;
		}).collect(Collectors.toList());

	}

}
