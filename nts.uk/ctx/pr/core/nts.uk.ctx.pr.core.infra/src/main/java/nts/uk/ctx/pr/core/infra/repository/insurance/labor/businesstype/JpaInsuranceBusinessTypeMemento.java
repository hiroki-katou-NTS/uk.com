/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeMemento;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaInsuranceBusinessTypeMemento implements InsuranceBusinessTypeMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaInsuranceBusinessTypeMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessTypeEnum getBizOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessName getBizName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBizOrder(BusinessTypeEnum businessTypeEnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBizName(BusinessName name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
		
	}

}
