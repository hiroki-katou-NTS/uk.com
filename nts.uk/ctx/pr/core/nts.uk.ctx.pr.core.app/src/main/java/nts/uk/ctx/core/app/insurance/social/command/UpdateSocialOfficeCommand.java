package nts.uk.ctx.core.app.insurance.social.command;

import lombok.Data;
import nts.uk.ctx.core.app.insurance.social.find.dto.SocialInsuranceOfficeDto;;

@Data
public class UpdateSocialOfficeCommand {
	SocialInsuranceOfficeDto socialInsuranceOfficeDto;
}
