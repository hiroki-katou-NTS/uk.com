package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service.HealthInsuranceAvgearnService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreateHealthInsuranceAvgearnCommandHandler extends CommandHandler<CreateHealthInsuranceAvgearnCommand> {

	@Inject
	private HealthInsuranceAvgearnService healthInsuranceAvgearnService;
	@Inject
	private HealthInsuranceAvgearnRepository healthInsuranceAvgearnRepository;

	@Override
	protected void handle(CommandHandlerContext<CreateHealthInsuranceAvgearnCommand> context) {
		// Get command.
		CreateHealthInsuranceAvgearnCommand command = context.getCommand();

		command.getListHealthInsuranceAvgearn().forEach(dto -> {

			// Transfer data
			HealthInsuranceAvgearn healthInsuranceAvgearn = dto.toDomain();

			// Validate
			healthInsuranceAvgearnService.validateRequiredItem(healthInsuranceAvgearn);
			// Update to db.
			healthInsuranceAvgearnRepository.add(healthInsuranceAvgearn);

		});
	}
}
