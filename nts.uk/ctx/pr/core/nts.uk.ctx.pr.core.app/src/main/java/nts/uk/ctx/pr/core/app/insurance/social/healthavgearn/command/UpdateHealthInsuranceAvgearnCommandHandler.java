package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service.HealthInsuranceAvgearnService;

@Stateless
public class UpdateHealthInsuranceAvgearnCommandHandler extends CommandHandler<UpdateHealthInsuranceAvgearnCommand> {

	@Inject
	private HealthInsuranceAvgearnService healthInsuranceAvgearnService;
	@Inject
	private HealthInsuranceAvgearnRepository healthInsuranceAvgearnRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateHealthInsuranceAvgearnCommand> context) {
		// Get command.
		UpdateHealthInsuranceAvgearnCommand command = context.getCommand();

		// Get the pensionAvgearn.
		HealthInsuranceAvgearn healthInsuranceAvgearn = (HealthInsuranceAvgearn) healthInsuranceAvgearnRepository
				.find(command.getHistoryId(), command.getLevelCode());

		// Transfer data
		HealthInsuranceAvgearn updatedHealthInsuranceAvgearn = command.toDomain(healthInsuranceAvgearn.getHistoryId(),
				healthInsuranceAvgearn.getLevelCode());

		// Validate
		healthInsuranceAvgearnService.validateRequiredItem(updatedHealthInsuranceAvgearn);

		// Update to db.
		healthInsuranceAvgearnRepository.update(updatedHealthInsuranceAvgearn);
	}

}
