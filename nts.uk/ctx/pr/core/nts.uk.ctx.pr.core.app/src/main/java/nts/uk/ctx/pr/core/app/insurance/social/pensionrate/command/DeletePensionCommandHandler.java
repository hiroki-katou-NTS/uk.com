package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;

@Stateless
public class DeletePensionCommandHandler extends CommandHandler<DeletePensionCommand> {

	/** The pension rate repository. */
	@Inject
	PensionRateRepository pensionRateRepository;

	@Override
	protected void handle(CommandHandlerContext<DeletePensionCommand> command) {
		// Get the current company code.
		// String companyCode = AppContexts.user().companyCode();
		// String officeCode = command.getCommand().getOfficeCode();
		String historyId = command.getCommand().getHistoryId();
		
		pensionRateRepository.remove(historyId);
		return;
	}
}