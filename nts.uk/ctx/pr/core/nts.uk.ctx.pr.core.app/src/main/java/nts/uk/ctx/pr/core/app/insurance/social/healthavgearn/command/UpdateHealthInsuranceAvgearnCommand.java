package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class UpdateHealthInsuranceAvgearnCommand.
 */
@Getter
@Setter
public class UpdateHealthInsuranceAvgearnCommand {

	/** The list health insurance avgearn. */
	List<HealthInsuranceAvgearnCommandDto> listHealthInsuranceAvgearn;

	/** The office code. */
	String officeCode;
}
