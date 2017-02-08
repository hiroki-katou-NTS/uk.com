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
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaPensionRateMemento implements PensionRateMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionRateMemento(Object typeValue) {
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
	public CommonAmount getMaxAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FundRateItem> getFundRateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PensionPremiumRateItem> getPremiumRateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ins2Rate getChildContributionRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PensionRateRounding> getRoundingMethods() {
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

}
