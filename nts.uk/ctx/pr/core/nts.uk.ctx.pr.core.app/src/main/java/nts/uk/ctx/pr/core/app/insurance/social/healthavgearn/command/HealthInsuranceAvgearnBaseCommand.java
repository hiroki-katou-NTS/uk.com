package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

@Getter
@Setter
public abstract class HealthInsuranceAvgearnBaseCommand {
	/** The version. */
	private Long version;

	/** The history id. */
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValue personalAvg;
}
