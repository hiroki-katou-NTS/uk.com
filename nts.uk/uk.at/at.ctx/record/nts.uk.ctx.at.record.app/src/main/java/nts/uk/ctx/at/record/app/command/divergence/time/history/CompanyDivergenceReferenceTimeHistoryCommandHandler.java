package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.util.UUID;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class CompanyDivergenceReferenceTimeHistoryCommandHandler extends CommandHandler<CompanyDivergenceReferenceTimeHistoryCommand>{

	@Override
	protected void handle(CommandHandlerContext<CompanyDivergenceReferenceTimeHistoryCommand> context) {
		String historyId = UUID.randomUUID().toString();
		
	}

}
