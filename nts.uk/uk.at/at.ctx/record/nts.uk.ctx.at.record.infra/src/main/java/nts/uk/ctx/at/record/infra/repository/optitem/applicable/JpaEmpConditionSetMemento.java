/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmploymentCondition;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpConPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaEmpConditionSetMemento.
 */
public class JpaEmpConditionSetMemento implements EmpConditionSetMemento {

	/** The company id. */
	private String companyId;

	/** The opt no. */
	private String optNo;

	/** The type values. */
	@Getter
	private List<KrcstApplEmpCon> typeValues;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		this.companyId = comId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		this.optNo = optNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setEmpConditions(java.util.List)
	 */
	@Override
	public void setEmpConditions(List<EmploymentCondition> empConditions) {
		this.typeValues = empConditions.stream().map(item -> {
			KrcstApplEmpConPK pk = new KrcstApplEmpConPK(this.companyId, this.optNo, item.getEmpCd());
			KrcstApplEmpCon entity = new KrcstApplEmpCon(pk);
			entity.setEmpApplAtr(item.getEmpApplicableAtr().value);
			return entity;
		}).collect(Collectors.toList());

	}

}
