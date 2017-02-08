package nts.uk.ctx.pr.core.app.command.insurance.social.health;

import lombok.Data;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceRateDto;

@Data
public class RegisterHealthInsuranceCommand {
	HealthInsuranceRateDto healthInsuranceRateDto;
}
