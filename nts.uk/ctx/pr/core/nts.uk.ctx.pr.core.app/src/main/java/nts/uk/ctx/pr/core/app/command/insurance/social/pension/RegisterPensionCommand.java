package nts.uk.ctx.pr.core.app.command.insurance.social.pension;

import lombok.Data;
import nts.uk.ctx.pr.core.app.finder.pension.dto.PensionRateDto;

@Data
public class RegisterPensionCommand {
	PensionRateDto pensionRate;
}
