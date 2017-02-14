package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.service.PensionAvgearnService;

@Stateless
public class UpdatePensionAvgearnCommandHandler extends CommandHandler<UpdatePensionAvgearnCommand> {

	@Inject
	private PensionAvgearnService pensionAvgearnService;
	@Inject
	private PensionAvgearnRepository pensionAvgearnRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdatePensionAvgearnCommand> context) {
		// Get command.
		UpdatePensionAvgearnCommand command = context.getCommand();

		// Get the pensionAvgearn.
		PensionAvgearn pensionAvgearn = (PensionAvgearn) pensionAvgearnRepository.find(command.getHistoryId(),
				command.getLevelCode());

		// Transfer data
		PensionAvgearn updatedPensionAvgearn = command.toDomain(pensionAvgearn.getHistoryId(),
				pensionAvgearn.getLevelCode());

		// Validate
		pensionAvgearnService.validateRequiredItem(updatedPensionAvgearn);

		// Update to db.
		pensionAvgearnRepository.update(updatedPensionAvgearn);
	}

}
