package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

@Builder
@Getter
public class PensionAvgearnDto {
	/** The history id. */
	private String historyId;

	/** The child contribution amount. */
	private InsuranceAmount childContributionAmount;

	/** The company fund. */
	private PensionAvgearnValue companyFund;

	/** The company fund exemption. */
	private PensionAvgearnValue companyFundExemption;

	/** The company pension. */
	private PensionAvgearnValue companyPension;

	/** The personal fund. */
	private PensionAvgearnValue personalFund;

	/** The personal fund exemption. */
	private PensionAvgearnValue personalFundExemption;

	/** The personal pension. */
	private PensionAvgearnValue personalPension;

	public static PensionAvgearnDto fromDomain(PensionAvgearn domain) {
		return null;
	}
}
