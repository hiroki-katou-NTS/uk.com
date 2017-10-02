package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.shr.com.context.AppContexts;

public class UpdateVerticalTimeSettingCommandHandler extends CommandHandlerWithResult<AddVerticalTimeSettingCommand, List<String>> {
	
	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddVerticalTimeSettingCommand> context) {
		List<String> errList = new ArrayList<String>();
		AddVerticalTimeSettingCommand addVerticalTimeSettingCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		VerticalTime verticalTime = addVerticalTimeSettingCommand.toDomain(companyId);

		repository.updateVerticalTime(verticalTime);
		return errList;
	}

}
