package nts.uk.ctx.pr.core.app.command.insurance.social;
import lombok.Data;
import nts.uk.ctx.pr.core.app.socialoffice.finder.dto.SocialInsuranceOfficeDto;

@Data
// TODO add implements for class
public class RegisterSocialOfficeCommand {
	
	private SocialInsuranceOfficeDto SIODto;
}
