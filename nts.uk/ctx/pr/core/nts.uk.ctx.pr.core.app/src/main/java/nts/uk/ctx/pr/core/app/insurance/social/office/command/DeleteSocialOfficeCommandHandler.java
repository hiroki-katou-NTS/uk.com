package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class DeleteSocialOfficeCommandHandler extends CommandHandler<DeleteSocialOfficeCommand>  {
	
	@Inject
	SocialInsuranceService insuranceSocialService;
	
	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DeleteSocialOfficeCommand> command) {
		
		Optional<SocialInsuranceOffice> findOffice = socialInsuranceOfficeRepository.findById(command.getCommand().getSocialInsuranceOfficeDto().getCode().toString());
		
		if(findOffice==null)
		{
			//TODO show error message
		}else
		{
			insuranceSocialService.remove(findOffice.get());
		}
		//TODO return item update
		return;
	}
}
