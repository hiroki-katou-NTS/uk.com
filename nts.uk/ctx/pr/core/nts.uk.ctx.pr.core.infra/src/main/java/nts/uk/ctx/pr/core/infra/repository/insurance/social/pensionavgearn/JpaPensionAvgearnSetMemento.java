/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaPensionAvgearnSetMemento implements PensionAvgearnSetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionAvgearnSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevelCode(Integer levelCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setChildContributionAmount(InsuranceAmount childContributionAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompanyFund(PensionAvgearnValue companyFund) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompanyFundExemption(PensionAvgearnValue companyFundExemption) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompanyPension(PensionAvgearnValue companyPension) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPersonalFund(PensionAvgearnValue personalFund) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPersonalFundExemption(PensionAvgearnValue personalFundExemption) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPersonalPension(PensionAvgearnValue personalPension) {
		// TODO Auto-generated method stub

	}

}
