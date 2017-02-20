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
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate;

/**
 * The Class JpaPensionRateGetMemento.
 */
public class JpaPensionRateGetMemento implements PensionRateGetMemento {

	/** The type value. */
	protected QismtPensionRate typeValue;

	/**
	 * Instantiates a new jpa pension rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionRateGetMemento(QismtPensionRate typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getOfficeCode()
	 */
	@Override
	public OfficeCode getOfficeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getApplyRange()
	 */
	@Override
	public MonthRange getApplyRange() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getMaxAmount()
	 */
	@Override
	public CommonAmount getMaxAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getFundRateItems()
	 */
	@Override
	public List<FundRateItem> getFundRateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getPremiumRateItems()
	 */
	@Override
	public List<PensionPremiumRateItem> getPremiumRateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getChildContributionRate()
	 */
	@Override
	public Ins2Rate getChildContributionRate() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getRoundingMethods()
	 */
	@Override
	public List<PensionRateRounding> getRoundingMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getFundInputApply()
	 */
	@Override
	public Boolean getFundInputApply() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getAutoCalculate()
	 */
	@Override
	public Boolean getAutoCalculate() {
		// TODO Auto-generated method stub
		return null;
	}

}
