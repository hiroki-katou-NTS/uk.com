package nts.uk.ctx.sys.shared.app.toppagealarmset.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarmRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateTopPageAlarmCommandHandler extends CommandHandler<TopPageAlarmCommand>{
	@Inject
	private TopPageAlarmRepository topPageAlarmRepository;

	@Override
	protected void handle(CommandHandlerContext<TopPageAlarmCommand> context) {
		String companyId = AppContexts.user().companyId();
		TopPageAlarmCommand cmd = context.getCommand();
		topPageAlarmRepository.updateRoger(cmd.getExecutionLogId(), cmd.getRogerFlag());
	}
	
	
}
