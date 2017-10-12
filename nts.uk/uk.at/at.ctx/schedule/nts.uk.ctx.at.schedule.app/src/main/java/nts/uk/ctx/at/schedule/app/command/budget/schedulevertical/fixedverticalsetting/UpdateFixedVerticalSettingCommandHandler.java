package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Transactional
@Stateless
public class UpdateFixedVerticalSettingCommandHandler extends CommandHandlerWithResult<AddFixedVerticalSettingCommand, List<String>> {
	
	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddFixedVerticalSettingCommand> context) {
		List<String> errList = new ArrayList<String>();
		AddFixedVerticalSettingCommand addFixedVerticalSettingCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		FixedVertical fixedVertical = addFixedVerticalSettingCommand.toDomain(companyId);
		
		repository.updateFixedVertical(fixedVertical);
		return errList;
	}
}
