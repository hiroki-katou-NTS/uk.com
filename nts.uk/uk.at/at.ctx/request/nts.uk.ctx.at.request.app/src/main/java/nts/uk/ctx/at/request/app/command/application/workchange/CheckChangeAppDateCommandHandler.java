package nts.uk.ctx.at.request.app.command.application.workchange;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.workchange.ICheckChangeApplicanDate;
/**
 * 申請日を変更する
 */
public class CheckChangeAppDateCommandHandler extends CommandHandler<ApplicationDateCommand> {

	@Inject
	private ICheckChangeApplicanDate checkChanged;
	@Override
	protected void handle(CommandHandlerContext<ApplicationDateCommand> context) {
		ApplicationDateCommand command = context.getCommand();
		checkChanged.CheckChangeApplicationDate(command.getStartDate(), command.getEndDate());
	}
}
