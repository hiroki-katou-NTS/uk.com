package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSocialOfficeCommandHandler extends CommandHandler<UpdateSocialOfficeCommand> {

	@Inject
	SocialInsuranceService insuranceSocialService;

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateSocialOfficeCommand> context) {

		UpdateSocialOfficeCommand command = context.getCommand();
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		
		// Convert Dto to Domain
		SocialInsuranceOffice socialInsuranceOffice = command.toDomain(companyCode);
		
		//TODO validate
		
		Optional<SocialInsuranceOffice> findOffice = socialInsuranceOfficeRepository
				.findById(command.getCode().toString());
		if (findOffice.get() == null) {
			// TODO show error message
		} else {
			insuranceSocialService.update(socialInsuranceOffice);
		}
		// TODO return item update
		return;
	}

}
