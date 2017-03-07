/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeSetMemento;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.businesstype.QismtBusinessType;

/**
 * The Class JpaInsuranceBusinessTypeSetMemento.
 */
public class JpaInsuranceBusinessTypeBiz10ThSetMemento implements InsuranceBusinessTypeSetMemento {

	/** The type value. */
	private QismtBusinessType typeValue;

	/**
	 * Instantiates a new jpa insurance business type set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaInsuranceBusinessTypeBiz10ThSetMemento(QismtBusinessType typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeSetMemento#setCompanyCode(nts.uk.ctx.core.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.typeValue.setCcd(companyCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeSetMemento#setBizOrder(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.businesstype.BusinessTypeEnum)
	 */
	@Override
	public void setBizOrder(BusinessTypeEnum businessTypeEnum) {
		// Do nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeSetMemento#setBizName(nts.uk.ctx.pr.core.dom.
	 * insurance.BusinessName)
	 */
	@Override
	public void setBizName(BusinessName name) {
		this.typeValue.setBizName10(name.v());
	}

}
