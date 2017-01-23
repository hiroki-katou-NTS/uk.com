package nts.uk.ctx.pr.core.app.command.insurance.social;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

public class DeleteSocialOfficeCommandHandler extends CommandHandler<DeleteSocialOfficeCommand>  {
	
	@Inject
	SocialInsuranceService insuranceSocialService;
	
	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteSocialOfficeCommand> command) {
		
		SocialInsuranceOffice findOffice = socialInsuranceOfficeRepository.findById(command.getCommand().getSIODto().getCode().toString());
		
		if(findOffice==null)
		{
			//TODO show error message
		}else
		{
			insuranceSocialService.remove(findOffice);
		}
		//TODO return item update
		return;
	}
}
