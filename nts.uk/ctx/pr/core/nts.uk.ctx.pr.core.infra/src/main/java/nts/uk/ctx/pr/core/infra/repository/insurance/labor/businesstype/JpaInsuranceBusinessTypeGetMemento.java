/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeGetMemento;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.businesstype.QismtBusinessType;

/**
 * The Class JpaInsuranceBusinessTypeGetMemento.
 */
public class JpaInsuranceBusinessTypeGetMemento implements InsuranceBusinessTypeGetMemento {

	/** The type value. */
	protected QismtBusinessType typeValue;

	/**
	 * Instantiates a new jpa insurance business type get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaInsuranceBusinessTypeGetMemento(QismtBusinessType typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeGetMemento#getBizOrder()
	 */
	@Override
	public BusinessTypeEnum getBizOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.
	 * InsuranceBusinessTypeGetMemento#getBizName()
	 */
	@Override
	public BusinessName getBizName() {
		// TODO Auto-generated method stub
		return null;
	}

}
