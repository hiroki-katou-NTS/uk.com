package nts.uk.ctx.pr.core.app.command.insurance.social;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.finder.socialoffice.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;

public class RegisterSocialOfficeCommandHandler extends CommandHandler<RegisterSocialOfficeCommand> {

	@Inject
	SocialInsuranceService insuranceSocialService;

	@Override
	protected void handle(CommandHandlerContext<RegisterSocialOfficeCommand> command) {

		SocialInsuranceOfficeDto SODto = command.getCommand().getSIODto();
		// TODO get data from session
		// Integer companyCode = 1;
		// String contractCode = "1";

		// TODO convert Dto to Domain
		SocialInsuranceOffice SIODomain = new SocialInsuranceOffice(SODto.getCompanyCode(), SODto.getCode(),
				SODto.getName(), SODto.getShortName(), SODto.getPicName(), SODto.getPicPosition(), SODto.getPotalCode(),
				SODto.getPrefecture(), SODto.getAddress1st(), SODto.getAddress2nd(), SODto.getKanaAddress1st(),
				SODto.getKanaAddress2nd(), SODto.getPhoneNumber(), SODto.getHealthInsuOfficeRefCode1st(),
				SODto.getHealthInsuOfficeRefCode2nd(), SODto.getPensionOfficeRefCode1st(),
				SODto.getPensionOfficeRefCode2nd(), SODto.getWelfarePensionFundCode(), SODto.getOfficePensionFundCode(),
				SODto.getHealthInsuCityCode(), SODto.getHealthInsuOfficeSign(), SODto.getPensionCityCode(),
				SODto.getPensionOfficeSign(), SODto.getHealthInsuOfficeCode(), SODto.getHealthInsuAssoCode(),
				SODto.getMemo());

		insuranceSocialService.add(SIODomain);
		return;
	}
}
