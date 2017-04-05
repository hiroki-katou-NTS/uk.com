/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearnPK;

/**
 * The Class JpaPensionAvgearnSetMemento.
 */
public class JpaPensionAvgearnSetMemento implements PensionAvgearnSetMemento {

	/** The type value. */
	protected QismtPensionAvgearn typeValue;

	/**
	 * Instantiates a new jpa pension avgearn set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaPensionAvgearnSetMemento(QismtPensionAvgearn typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		QismtPensionAvgearnPK pensionAvgearnPK = new QismtPensionAvgearnPK();
		pensionAvgearnPK.setHistId(historyId);
		this.typeValue.setQismtPensionAvgearnPK(pensionAvgearnPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnSetMemento#setLevelCode(java.lang.Integer)
	 */
	@Override
	public void setLevelCode(Integer levelCode) {
		QismtPensionAvgearnPK pensionAvgearnPK = this.typeValue.getQismtPensionAvgearnPK();
		pensionAvgearnPK.setPensionGrade(BigDecimal.valueOf(levelCode));
		this.typeValue.setQismtPensionAvgearnPK((pensionAvgearnPK));
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
		this.typeValue.setChildContributionMny(childContributionAmount.v());
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
		this.typeValue.setCFundFemMny(companyFund.getFemaleAmount().v());
		this.typeValue.setCFundMaleMny(companyFund.getMaleAmount().v());
		this.typeValue.setCFundMinerMny(companyFund.getUnknownAmount().v());

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
		this.typeValue.setCFundExemptFemMny(companyFundExemption.getFemaleAmount().v());
		this.typeValue.setCFundExemptMaleMny(companyFundExemption.getMaleAmount().v());
		this.typeValue.setCFundExemptMinerMny(companyFundExemption.getUnknownAmount().v());
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
		this.typeValue.setCPensionFemMny(companyPension.getFemaleAmount().v());
		this.typeValue.setCPensionMaleMny(companyPension.getMaleAmount().v());
		this.typeValue.setCPensionMinerMny(companyPension.getUnknownAmount().v());
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
		this.typeValue.setPFundFemMny(personalFund.getFemaleAmount().v());
		this.typeValue.setPFundMaleMny(personalFund.getMaleAmount().v());
		this.typeValue.setPFundMinerMny(personalFund.getUnknownAmount().v());
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
		this.typeValue.setPFundExemptFemMny(personalFundExemption.getFemaleAmount().v());
		this.typeValue.setPFundExemptMaleMny(personalFundExemption.getMaleAmount().v());
		this.typeValue.setPFundExemptMinerMny(personalFundExemption.getUnknownAmount().v());
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
		this.typeValue.setPPensionFemMny(personalPension.getFemaleAmount().v());
		this.typeValue.setPPensionMaleMny(personalPension.getMaleAmount().v());
		this.typeValue.setPPensionMinerMny(personalPension.getUnknownAmount().v());
	}

}
