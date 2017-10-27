/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmploymentCondition;
import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaEmpConditionGetMemento.
 */
public class JpaEmpConditionGetMemento implements EmpConditionGetMemento {

	/** The Constant FIRST_ITEM_INDEX. */
	private static final int FIRST_ITEM_INDEX = 0;

	/** The type values. */
	private List<KrcstApplEmpCon> typeValues;

	/**
	 * Instantiates a new jpa emp condition get memento.
	 *
	 * @param typeValues
	 *            the type values
	 */
	public JpaEmpConditionGetMemento(List<KrcstApplEmpCon> typeValues) {
		this.typeValues = typeValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValues.get(FIRST_ITEM_INDEX).getKrcstApplEmpConPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(
				this.typeValues.get(FIRST_ITEM_INDEX).getKrcstApplEmpConPK().getOptionalItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getEmploymentConditions()
	 */
	@Override
	public List<EmploymentCondition> getEmploymentConditions() {
		return typeValues.stream()
				.map(item -> new EmploymentCondition(item.getKrcstApplEmpConPK().getEmpCd(),
						item.getEmpApplAtr()))
				.collect(Collectors.toList());
	}

}
