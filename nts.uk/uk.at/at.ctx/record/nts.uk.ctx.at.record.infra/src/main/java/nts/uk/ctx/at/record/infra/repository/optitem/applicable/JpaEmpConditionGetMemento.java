/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.infra.entity.optitem.applicable.KrcstApplEmpCon;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmploymentCondition;

/**
 * The Class JpaEmpConditionGetMemento.
 */
public class JpaEmpConditionGetMemento implements EmpConditionGetMemento {

	/** The type values. */
	private List<KrcstApplEmpCon> typeValues;

	/** The company id. */
	private String companyId;

	/** The opt item no. */
	private Integer optItemNo;

	/**
	 * Instantiates a new jpa emp condition get memento.
	 *
	 * @param companyId the company id
	 * @param optionalItemNo the optional item no
	 * @param typeValues the type values
	 */
	public JpaEmpConditionGetMemento(String companyId, Integer optionalItemNo, List<KrcstApplEmpCon> typeValues) {
		this.typeValues = typeValues;
		this.companyId = companyId;
		this.optItemNo = optionalItemNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optItemNo);
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
				.map(item -> new EmploymentCondition(item.getKrcstApplEmpConPK().getEmpCd(), item.getEmpApplAtr()))
				.collect(Collectors.toList());
	}

}
