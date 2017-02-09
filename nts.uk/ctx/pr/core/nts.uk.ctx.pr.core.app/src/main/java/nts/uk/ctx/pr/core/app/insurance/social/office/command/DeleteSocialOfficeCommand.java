package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import lombok.Data;
import nts.uk.ctx.pr.core.app.insurance.social.office.find.SocialInsuranceOfficeDto;

@Data
public class DeleteSocialOfficeCommand {
	SocialInsuranceOfficeDto socialInsuranceOfficeDto;
}
