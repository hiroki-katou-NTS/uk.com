package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.math.BigDecimal;

import lombok.Builder;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

@Builder
public class PensionAvgearnDto implements PensionAvgearnSetMemento {
	/** The history id. */
	public String historyId;

	/** The level code. */
	public Integer levelCode;

	/** The child contribution amount. */
	public BigDecimal childContributionAmount;

	/** The company fund. */
	public PensionAvgearnValueDto companyFund;

	/** The company fund exemption. */
	public PensionAvgearnValueDto companyFundExemption;

	/** The company pension. */
	public PensionAvgearnValueDto companyPension;

	/** The personal fund. */
	public PensionAvgearnValueDto personalFund;

	/** The personal fund exemption. */
	public PensionAvgearnValueDto personalFundExemption;

	/** The personal pension. */
	public PensionAvgearnValueDto personalPension;

	/** The version. */
	public Long version;

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	@Override
	public void setChildContributionAmount(InsuranceAmount childContributionAmount) {
		this.childContributionAmount = childContributionAmount.v();
	}

	@Override
	public void setCompanyFund(PensionAvgearnValue companyFund) {
		this.companyFund = PensionAvgearnValueDto.fromDomain(companyFund);
	}

	@Override
	public void setCompanyFundExemption(PensionAvgearnValue companyFundExemption) {
		this.companyFundExemption = PensionAvgearnValueDto.fromDomain(companyFundExemption);
	}

	@Override
	public void setCompanyPension(PensionAvgearnValue companyPension) {
		this.companyPension = PensionAvgearnValueDto.fromDomain(companyPension);
	}

	@Override
	public void setPersonalFund(PensionAvgearnValue personalFund) {
		this.personalFund = PensionAvgearnValueDto.fromDomain(personalFund);
	}

	@Override
	public void setPersonalFundExemption(PensionAvgearnValue personalFundExemption) {
		this.personalFundExemption = PensionAvgearnValueDto.fromDomain(personalFundExemption);
	}

	@Override
	public void setPersonalPension(PensionAvgearnValue personalPension) {
		this.personalPension = PensionAvgearnValueDto.fromDomain(personalPension);
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
