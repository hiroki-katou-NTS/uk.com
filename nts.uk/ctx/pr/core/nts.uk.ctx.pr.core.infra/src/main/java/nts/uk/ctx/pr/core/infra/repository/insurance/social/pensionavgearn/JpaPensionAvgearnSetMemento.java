/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearnPK;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaPensionAvgearnSetMemento implements PensionAvgearnSetMemento {

	/** The type value. */
	protected QismtPensionAvgearn typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionAvgearnSetMemento(QismtPensionAvgearn typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setHistoryId(String historyId) {
		QismtPensionAvgearnPK pensionAvgearnPK = new QismtPensionAvgearnPK();
		pensionAvgearnPK.setHistId(historyId);
		this.typeValue.setQismtPensionAvgearnPK(pensionAvgearnPK);
	}

	@Override
	public void setLevelCode(Integer levelCode) {
		QismtPensionAvgearnPK pensionAvgearnPK = this.typeValue.getQismtPensionAvgearnPK();
		pensionAvgearnPK.setPensionGrade(BigDecimal.valueOf(levelCode));
		//fake code
		pensionAvgearnPK.setCcd("0001");
		pensionAvgearnPK.setSiOfficeCd("23");
		this.typeValue.setQismtPensionAvgearnPK((pensionAvgearnPK));
	}

	@Override
	public void setChildContributionAmount(InsuranceAmount childContributionAmount) {
		this.typeValue.setChildContributionMny(childContributionAmount.v());
	}

	@Override
	public void setCompanyFund(PensionAvgearnValue companyFund) {
		//thua truong?
//		this.typeValue.setCFundFemMny(companyFund.getFemaleAmount().v());
//		this.typeValue.setCFundMaleMny(companyFund.getMaleAmount().v());
//		this.typeValue.setCFundMinerMny(companyFund.getUnknownAmount().v());
		this.typeValue.setCFundFemMny(BigDecimal.valueOf(5));
		this.typeValue.setCFundMaleMny(BigDecimal.valueOf(5));
		this.typeValue.setCFundMinerMny(BigDecimal.valueOf(5));

	}

	@Override
	public void setCompanyFundExemption(PensionAvgearnValue companyFundExemption) {
		this.typeValue.setCFundExemptFemMny(companyFundExemption.getFemaleAmount().v());
		this.typeValue.setCFundExemptMaleMny(companyFundExemption.getMaleAmount().v());
		this.typeValue.setCFundExemptMinerMny(companyFundExemption.getUnknownAmount().v());
	}

	@Override
	public void setCompanyPension(PensionAvgearnValue companyPension) {
		this.typeValue.setCPensionFemMny(companyPension.getFemaleAmount().v());
		this.typeValue.setCPensionMaleMny(companyPension.getMaleAmount().v());
		this.typeValue.setCPensionMinerMny(companyPension.getUnknownAmount().v());
	}

	@Override
	public void setPersonalFund(PensionAvgearnValue personalFund) {
		//thua truong?
//		this.typeValue.setPFundFemMny(personalFund.getFemaleAmount().v());
//		this.typeValue.setPFundMaleMny(personalFund.getMaleAmount().v());
//		this.typeValue.setPFundMinerMny(personalFund.getUnknownAmount().v());
		this.typeValue.setPFundFemMny(BigDecimal.valueOf(5));
		this.typeValue.setPFundMaleMny(BigDecimal.valueOf(5));
		this.typeValue.setPFundMinerMny(BigDecimal.valueOf(5));
	}

	@Override
	public void setPersonalFundExemption(PensionAvgearnValue personalFundExemption) {
		this.typeValue.setPFundExemptFemMny(personalFundExemption.getFemaleAmount().v());
		this.typeValue.setPFundExemptMaleMny(personalFundExemption.getMaleAmount().v());
		this.typeValue.setPFundExemptMinerMny(personalFundExemption.getUnknownAmount().v());
	}

	@Override
	public void setPersonalPension(PensionAvgearnValue personalPension) {
		this.typeValue.setPPensionFemMny(personalPension.getFemaleAmount().v());
		this.typeValue.setPPensionMaleMny(personalPension.getMaleAmount().v());
		this.typeValue.setPPensionMinerMny(personalPension.getUnknownAmount().v());
	}

}
