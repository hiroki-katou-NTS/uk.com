package nts.uk.ctx.sys.env.app.command.mailnoticeset;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class MailNoticeSetSaveCommandHandler extends CommandHandler<MailNoticeSetSaveCommand> {

	@Override
	protected void handle(CommandHandlerContext<MailNoticeSetSaveCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
