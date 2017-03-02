/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CreateHealthInsuranceAvgearnCommand.
 */
@Getter
@Setter
public class CreateHealthInsuranceAvgearnCommand {
	/** The list health insurance avgearn. */
	private List<HealthInsuranceAvgearnCommandDto> listHealthInsuranceAvgearn;
}
