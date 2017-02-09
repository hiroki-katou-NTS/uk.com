package nts.uk.ctx.pr.core.app.insurance.social.command;

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
public class RegisterSocialOfficeCommandHandler extends CommandHandler<RegisterSocialOfficeCommand> {

	@Inject
	SocialInsuranceService insuranceSocialService;

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterSocialOfficeCommand> context) {
		RegisterSocialOfficeCommand command = context.getCommand();
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		
		//convert to domain
		SocialInsuranceOffice socialInsuranceOffice = command.toDomain(companyCode);
		
		// Validate
		// unitPriceHistoryService.validateRequiredItem(unitPriceHistory);
		// unitPriceHistoryService.validateDateRange(unitPriceHistory);
		
		socialInsuranceOfficeRepository.add(socialInsuranceOffice);
		return;
	}
}
