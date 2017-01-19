/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaHealthInsuranceRateMemento implements HealthInsuranceRateMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceRateMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfficeCode getOfficeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthRange getApplyRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getAutoCalculate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getMaxAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InsuranceRateItem> getRateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HealthInsuranceRounding> getRoundingMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOfficeCode(OfficeCode officeCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxAmount(Long maxAmount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRateItems(List<InsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRoundingMethods(List<HealthInsuranceRounding> roundingMethods) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
		
	}

}
