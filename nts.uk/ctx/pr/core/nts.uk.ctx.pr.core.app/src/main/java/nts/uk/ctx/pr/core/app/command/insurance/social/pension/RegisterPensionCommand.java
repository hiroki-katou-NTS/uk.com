package nts.uk.ctx.pr.core.app.command.insurance.social.pension;

import lombok.Data;
import nts.uk.ctx.pr.core.app.find.insurance.social.pensionrate.PensionRateDto;

@Data
public class RegisterPensionCommand {
	PensionRateDto pensionRate;
}
