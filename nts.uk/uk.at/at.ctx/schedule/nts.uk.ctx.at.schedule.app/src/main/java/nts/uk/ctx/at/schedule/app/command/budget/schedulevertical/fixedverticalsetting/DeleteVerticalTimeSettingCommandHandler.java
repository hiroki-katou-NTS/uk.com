package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteVerticalTimeSettingCommandHandler extends CommandHandler<DeleteVerticalTimeSettingCommand>{
 
	@Inject
	private FixedVerticalSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteVerticalTimeSettingCommand> context) {
		DeleteVerticalTimeSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		//Delete Vertical Time
		repository.deleteVerticalTime(companyId, command.getFixedItemAtr());
	}

}
