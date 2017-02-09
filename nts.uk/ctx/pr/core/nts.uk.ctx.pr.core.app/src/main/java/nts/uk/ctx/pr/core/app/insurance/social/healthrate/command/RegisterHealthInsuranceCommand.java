package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import lombok.Data;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateDto;

@Data
public class RegisterHealthInsuranceCommand {
	HealthInsuranceRateDto healthInsuranceRateDto;
}
