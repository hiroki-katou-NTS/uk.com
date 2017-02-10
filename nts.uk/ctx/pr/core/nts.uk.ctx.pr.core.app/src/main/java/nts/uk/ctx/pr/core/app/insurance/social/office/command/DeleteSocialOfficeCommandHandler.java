package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class DeleteSocialOfficeCommandHandler extends CommandHandler<DeleteSocialOfficeCommand>  {
	
	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DeleteSocialOfficeCommand> command) {
		String officeCode = command.getCommand().getSocialInsuranceOfficeDto().code;
		
		Optional<SocialInsuranceOffice> findOffice = socialInsuranceOfficeRepository.findById(officeCode);
		
		if(findOffice==null)
		{
			//TODO show error message
		}else
		{
			socialInsuranceOfficeRepository.remove(officeCode,0L);
		}
		//TODO return item update
		return;
	}
}
