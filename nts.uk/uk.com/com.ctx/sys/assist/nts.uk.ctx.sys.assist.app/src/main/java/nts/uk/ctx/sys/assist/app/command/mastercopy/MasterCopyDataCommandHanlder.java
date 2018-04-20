package nts.uk.ctx.sys.assist.app.command.mastercopy;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

public class MasterCopyDataCommandHanlder extends AsyncCommandHandler<MasterCopyDataCommand>{

	@Override
	protected void handle(CommandHandlerContext<MasterCopyDataCommand> context) {
		context.asAsync();
	}

}
