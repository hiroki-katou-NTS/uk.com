package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

public class UpdateBusinessTypeDailyCommandHandler extends CommandHandler<UpdateBusinessTypeDailyCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessTypeDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusinessTypeDailyCommand command = context.getCommand();
		
	}

}
