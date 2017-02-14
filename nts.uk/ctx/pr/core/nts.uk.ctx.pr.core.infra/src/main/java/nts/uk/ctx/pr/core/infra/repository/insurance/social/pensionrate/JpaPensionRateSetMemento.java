/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaPensionRateSetMemento implements PensionRateSetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionRateSetMemento(Object typeValue) {
		this.typeValue = typeValue;
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
	public void setMaxAmount(CommonAmount maxAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFundRateItems(List<FundRateItem> fundRateItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPremiumRateItems(List<PensionPremiumRateItem> premiumRateItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setChildContributionRate(Ins2Rate childContributionRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRoundingMethods(List<PensionRateRounding> roundingMethods) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFundInputApply(Boolean fundInputApply) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		// TODO Auto-generated method stub
		
	}

}
