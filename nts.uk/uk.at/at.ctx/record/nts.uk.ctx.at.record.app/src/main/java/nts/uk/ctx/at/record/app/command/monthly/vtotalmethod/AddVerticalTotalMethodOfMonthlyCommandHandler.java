package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

public class AddVerticalTotalMethodOfMonthlyCommandHandler extends CommandHandler<AddVerticalTotalMethodOfMonthlyCommand> {
	@Inject
	VerticalTotalMethodOfMonthlyRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddVerticalTotalMethodOfMonthlyCommand> context) {
		AddVerticalTotalMethodOfMonthlyCommand command = context.getCommand();
		
		String companyId = AppContexts.user().companyId();
		
		
	}
}
