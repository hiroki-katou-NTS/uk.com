package nts.uk.ctx.pr.core.app.command.insurance.social;

import lombok.Data;
import nts.uk.ctx.pr.core.app.find.insurance.social.SocialInsuranceOfficeDto;

@Data
public class DeleteSocialOfficeCommand {
	SocialInsuranceOfficeDto SIODto;
}
