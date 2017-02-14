package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find.PensionAvgearnValueDto;

@Getter
@Setter
public abstract class PensionAvgearnBaseCommand {
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
}
