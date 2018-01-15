package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Transactional
@Stateless
public class AddVerticalTimeSettingCommandHandler extends CommandHandler<AddVerticalTimeSettingCommand> {
	
	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddVerticalTimeSettingCommand> context) {
		AddVerticalTimeSettingCommand command = context.getCommand();
		List<VerticalTimeSettingCommand> verticalTimeList = command.getVerticalTimes();
		String companyId = AppContexts.user().companyId();
		repository.deleteVerticalTime(companyId, command.getFixedItemAtr());
		
		for (VerticalTimeSettingCommand item : verticalTimeList) {
			VerticalTime verticalTime = item.toDomain(companyId, command.getFixedItemAtr());
			verticalTime.validate();
			//Add Vertical Time
			repository.addVerticalTime(verticalTime);
		}
	}

}
