package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterHealthInsuranceCommandHandler extends CommandHandler<RegisterHealthInsuranceCommand> {

	@Inject
	HealthInsuranceRateService healthInsuranceRateService;
	@Inject
	HealthInsuranceRateRepository healthInsuranceRateRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterHealthInsuranceCommand> context) {
		// Get command.
		RegisterHealthInsuranceCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Transfer data
		HealthInsuranceRate healthInsuranceRate = command.toDomain(companyCode);

		// Validate
		healthInsuranceRateService.validateDateRange(healthInsuranceRate);
		healthInsuranceRateService.validateRequiredItem(healthInsuranceRate);

		// Insert into db.
		healthInsuranceRateRepository.add(healthInsuranceRate);
	}

}
