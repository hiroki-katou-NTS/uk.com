package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class TopPageSelfSettingCommandHandler extends CommandHandler<TopPageSelfSettingCommand> {

	@Override
	protected void handle(CommandHandlerContext<TopPageSelfSettingCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
