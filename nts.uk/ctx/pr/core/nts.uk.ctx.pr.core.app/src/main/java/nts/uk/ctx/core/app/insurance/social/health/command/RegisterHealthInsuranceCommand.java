package nts.uk.ctx.core.app.insurance.social.health.command;

import lombok.Data;
import nts.uk.ctx.core.app.insurance.social.healthrate.find.HealthInsuranceRateDto;

@Data
public class RegisterHealthInsuranceCommand {
	HealthInsuranceRateDto healthInsuranceRateDto;
}
