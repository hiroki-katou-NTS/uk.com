package nts.uk.ctx.pr.core.app.insurance.social.command;

import lombok.Data;
import nts.uk.ctx.pr.core.app.insurance.social.find.dto.SocialInsuranceOfficeDto;

@Data
public class DeleteSocialOfficeCommand {
	SocialInsuranceOfficeDto socialInsuranceOfficeDto;
}
