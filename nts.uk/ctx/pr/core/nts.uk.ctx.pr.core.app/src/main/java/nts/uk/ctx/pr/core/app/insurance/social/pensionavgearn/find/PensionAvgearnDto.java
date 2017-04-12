/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

/**
 * The Class PensionAvgearnDto.
 */
@Builder
@Getter
public class PensionAvgearnDto implements PensionAvgearnSetMemento {

	/** The level code. */
	private Integer levelCode;

	/** The child contribution amount. */
	private BigDecimal childContributionAmount;

	/** The company fund. */
	private PensionAvgearnValueDto companyFund;

	/** The company fund exemption. */
	private PensionAvgearnValueDto companyFundExemption;

	/** The company pension. */
	private PensionAvgearnValueDto companyPension;

	/** The personal fund. */
	private PensionAvgearnValueDto personalFund;

	/** The personal fund exemption. */
	private PensionAvgearnValueDto personalFundExemption;

	/** The personal pension. */
	private PensionAvgearnValueDto personalPension;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setLevelCode(java.lang.Integer)
	 */
	@Override
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setChildContributionAmount(nts.uk.ctx.pr.core.
	 * dom.insurance.InsuranceAmount)
	 */
	@Override
	public void setChildContributionAmount(CommonAmount childContributionAmount) {
		this.childContributionAmount = childContributionAmount.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setCompanyFund(nts.uk.ctx.pr.core.dom.insurance.
	 * social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setCompanyFund(PensionAvgearnValue companyFund) {
		this.companyFund = PensionAvgearnValueDto.fromDomain(companyFund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setCompanyFundExemption(nts.uk.ctx.pr.core.dom.
	 * insurance.social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setCompanyFundExemption(PensionAvgearnValue companyFundExemption) {
		this.companyFundExemption = PensionAvgearnValueDto.fromDomain(companyFundExemption);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setCompanyPension(nts.uk.ctx.pr.core.dom.
	 * insurance.social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setCompanyPension(PensionAvgearnValue companyPension) {
		this.companyPension = PensionAvgearnValueDto.fromDomain(companyPension);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setPersonalFund(nts.uk.ctx.pr.core.dom.insurance
	 * .social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setPersonalFund(PensionAvgearnValue personalFund) {
		this.personalFund = PensionAvgearnValueDto.fromDomain(personalFund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setPersonalFundExemption(nts.uk.ctx.pr.core.dom.
	 * insurance.social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setPersonalFundExemption(PensionAvgearnValue personalFundExemption) {
		this.personalFundExemption = PensionAvgearnValueDto.fromDomain(personalFundExemption);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setPersonalPension(nts.uk.ctx.pr.core.dom.
	 * insurance.social.pensionavgearn.PensionAvgearnValue)
	 */
	@Override
	public void setPersonalPension(PensionAvgearnValue personalPension) {
		this.personalPension = PensionAvgearnValueDto.fromDomain(personalPension);
	}
}
