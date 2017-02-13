/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaPensionAvgearnGetMemento implements PensionAvgearnGetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionAvgearnGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getLevelCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsuranceAmount getChildContributionAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getCompanyFund() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getCompanyFundExemption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getCompanyPension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getPersonalFund() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getPersonalFundExemption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PensionAvgearnValue getPersonalPension() {
		// TODO Auto-generated method stub
		return null;
	}

}
