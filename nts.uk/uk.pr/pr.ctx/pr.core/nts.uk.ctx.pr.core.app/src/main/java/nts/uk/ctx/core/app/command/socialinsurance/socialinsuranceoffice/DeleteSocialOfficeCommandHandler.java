package nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class DeleteSocialOfficeCommandHandler extends CommandHandler<FindSocialOfficeCommand> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<FindSocialOfficeCommand> context) {
		FindSocialOfficeCommand command = context.getCommand();
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findById(AppContexts.user().companyId(), command.getCode());
		if(data.isPresent()) {
			socialInsuranceOfficeRepository.remove(AppContexts.user().companyId(), command.getCode());
		}
	}
}
