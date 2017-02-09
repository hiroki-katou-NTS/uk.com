package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

@Stateless
public class UpdateHealthInsuranceCommandHandler extends CommandHandler<UpdateHealthInsuranceCommand> {

	@Inject
	HealthInsuranceRateRepository healthInsuranceRateRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateHealthInsuranceCommand> command) {
		// healthInsuranceRateRepository.add(command.getCommand().getHealthInsuranceRateDto());
	}

}
