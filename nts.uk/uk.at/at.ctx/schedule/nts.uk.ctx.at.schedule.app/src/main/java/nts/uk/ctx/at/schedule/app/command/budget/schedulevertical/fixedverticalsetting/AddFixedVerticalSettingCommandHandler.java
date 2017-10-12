package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class AddFixedVerticalSettingCommandHandler extends CommandHandler<AddFixedVerticalSettingCommand> {
	
	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddFixedVerticalSettingCommand> context) {
		AddFixedVerticalSettingCommand addFixedVerticalSettingCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		FixedVertical fixedVertical = addFixedVerticalSettingCommand.toDomain(companyId);
		
		repository.addFixedVertical(fixedVertical);
	}

}
