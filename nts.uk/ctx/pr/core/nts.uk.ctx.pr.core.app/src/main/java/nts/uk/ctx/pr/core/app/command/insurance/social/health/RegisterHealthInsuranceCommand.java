package nts.uk.ctx.pr.core.app.command.insurance.social.health;

import lombok.Data;
import nts.uk.ctx.pr.core.app.finder.healthinsurance.dto.HealthInsuranceRateDto;

@Data
public class RegisterHealthInsuranceCommand {
	HealthInsuranceRateDto HIRDto;
}
