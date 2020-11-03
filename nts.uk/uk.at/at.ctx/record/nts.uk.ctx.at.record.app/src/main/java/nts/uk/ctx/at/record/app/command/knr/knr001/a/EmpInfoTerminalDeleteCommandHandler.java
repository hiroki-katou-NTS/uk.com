package nts.uk.ctx.at.record.app.command.knr.knr001.a;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;

public class EmpInfoTerminalDeleteCommandHandler extends CommandHandler<EmpInfoTerminalDeleteCommand> {

	@Inject
	private EmpInfoTerminalRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpInfoTerminalDeleteCommand> context) {
		
		EmpInfoTerminalDeleteCommand command = context.getCommand();
		
	}

}
