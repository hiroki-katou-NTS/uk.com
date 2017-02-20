/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;

/**
 * The Class JpaHealthInsuranceRateSetMemento.
 */
public class JpaHealthInsuranceRateSetMemento implements HealthInsuranceRateSetMemento {

	/** The type value. */
	protected QismtHealthInsuRate typeValue;

	/**
	 * Instantiates a new jpa health insurance rate set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceRateSetMemento(QismtHealthInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setCompanyCode(nts.uk.ctx.core.dom.company.
	 * CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setOfficeCode(nts.uk.ctx.pr.core.dom.
	 * insurance.OfficeCode)
	 */
	@Override
	public void setOfficeCode(OfficeCode officeCode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.
	 * insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setAutoCalculate(java.lang.Boolean)
	 */
	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setMaxAmount(nts.uk.ctx.pr.core.dom.
	 * insurance.CommonAmount)
	 */
	@Override
	public void setMaxAmount(CommonAmount maxAmount) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setRateItems(java.util.Set)
	 */
	@Override
	public void setRateItems(Set<InsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateSetMemento#setRoundingMethods(java.util.Set)
	 */
	@Override
	public void setRoundingMethods(Set<HealthInsuranceRounding> roundingMethods) {
		// TODO Auto-generated method stub

	}

}
