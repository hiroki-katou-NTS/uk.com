package nts.uk.ctx.at.function.app.command.alarm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteAlarmPatternSettingCommandHandler extends CommandHandler<DeleteAlarmPatternSettingCommand> {
	
	@Inject
	private AlarmPatternSettingRepository  repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteAlarmPatternSettingCommand> context) {
		DeleteAlarmPatternSettingCommand c = context.getCommand();
		repo.delete(AppContexts.user().companyId(), c.getAlarmPatternCD());
	}
	
	
}
