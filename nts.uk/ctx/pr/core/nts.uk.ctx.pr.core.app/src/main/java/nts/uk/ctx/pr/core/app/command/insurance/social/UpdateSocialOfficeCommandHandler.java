package nts.uk.ctx.pr.core.app.command.insurance.social;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.finder.socialoffice.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

public class UpdateSocialOfficeCommandHandler extends CommandHandler<UpdateSocialOfficeCommand> {
	
	@Inject
	SocialInsuranceService insuranceSocialService;
	
	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSocialOfficeCommand> command) {
		// TODO Auto-generated method stub
		SocialInsuranceOfficeDto SODto = command.getCommand().getSIODto();
		
		//Convert Dto to Domain
		SocialInsuranceOffice SIODomain = new SocialInsuranceOffice(SODto.getCompanyCode(), SODto.getCode(),
				SODto.getName(), SODto.getShortName(), SODto.getPicName(), SODto.getPicPosition(), SODto.getPotalCode(),
				SODto.getPrefecture(), SODto.getAddress1st(), SODto.getAddress2nd(), SODto.getKanaAddress1st(),
				SODto.getKanaAddress2nd(), SODto.getPhoneNumber(), SODto.getHealthInsuOfficeRefCode1st(),
				SODto.getHealthInsuOfficeRefCode2nd(), SODto.getPensionOfficeRefCode1st(),
				SODto.getPensionOfficeRefCode2nd(), SODto.getWelfarePensionFundCode(), SODto.getOfficePensionFundCode(),
				SODto.getHealthInsuCityCode(), SODto.getHealthInsuOfficeSign(), SODto.getPensionCityCode(),
				SODto.getPensionOfficeSign(), SODto.getHealthInsuOfficeCode(), SODto.getHealthInsuAssoCode(),
				SODto.getMemo());
		SocialInsuranceOffice findOffice = socialInsuranceOfficeRepository.findById(command.getCommand().getSIODto().getCode().toString());
		if(findOffice==null)
		{
			//TODO show error message
		}else
		{
			insuranceSocialService.update(SIODomain);
		}
		//TODO return item update
		return;
	}

}
