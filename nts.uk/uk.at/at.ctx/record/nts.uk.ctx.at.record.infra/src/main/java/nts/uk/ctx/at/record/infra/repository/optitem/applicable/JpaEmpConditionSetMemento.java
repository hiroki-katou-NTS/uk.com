/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpConPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmploymentCondition;

/**
 * The Class JpaEmpConditionSetMemento.
 */
public class JpaEmpConditionSetMemento implements EmpConditionSetMemento {

	/** The type values. */

	@Getter
	private List<KrcstApplEmpCon> typeValues;

	/** The cid. */
	private String cid;

	/** The opt no. */
	private Integer optNo;

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
		this.cid = comId.v();
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
			KrcstApplEmpCon empCon = this.typeValues.stream()
					.filter(entity -> entity.getKrcstApplEmpConPK().getEmpCd().equals(item.getEmpCd()))
					.findFirst()
					.orElse(null);
			if (empCon != null) {
				// update value
				empCon.setEmpApplAtr(item.getEmpApplicableAtr().value);
			} else {
				// create value
				KrcstApplEmpConPK pk = new KrcstApplEmpConPK(this.cid, this.optNo, item.getEmpCd());
				empCon = new KrcstApplEmpCon(pk);
				empCon.setEmpApplAtr(item.getEmpApplicableAtr().value);
			}
			return empCon;
		}).collect(Collectors.toList());
	}

}
