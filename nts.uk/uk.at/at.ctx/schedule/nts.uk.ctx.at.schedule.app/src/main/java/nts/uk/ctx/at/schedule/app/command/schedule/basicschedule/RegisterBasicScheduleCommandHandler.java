package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheCorrectCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheCorrectCommandHandler;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.RegisterBasicScheduleService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class RegisterBasicScheduleCommandHandler
		extends CommandHandlerWithResult<DataRegisterBasicSchedule, List<String>> {
	@Inject
	private RegisterBasicScheduleService basicScheduleService;
	@Inject
	private BasicScheCorrectCommandHandler basicScheCorrectCommandHandler;

	@Override
	protected List<String> handle(CommandHandlerContext<DataRegisterBasicSchedule> context) {
		// list listBasicScheduleBefore is data from DB
		List<BasicSchedule> listBasicScheduleBefore = new ArrayList<BasicSchedule>();
		String companyId = AppContexts.user().companyId();
		DataRegisterBasicSchedule command = context.getCommand();
		int modeDisplay = command.getModeDisplay();
		boolean isInsertMode = true;
		List<RegisterBasicScheduleCommand> listRegisterBasicScheduleCommand = command.getListRegisterBasicSchedule();
		// list listBasicScheduleAfter is data from screen
		List<BasicSchedule> listBasicScheduleAfter = listRegisterBasicScheduleCommand.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		
		List<String> errorList = basicScheduleService.register(companyId, Integer.valueOf(modeDisplay), listBasicScheduleAfter, listBasicScheduleBefore, isInsertMode);

		// <<Public>> データ修正記録を登録する(đăng ký record chỉnh sử data)
		this.basicScheCorrectCommandHandler.handle(new BasicScheCorrectCommand(listBasicScheduleBefore, listBasicScheduleAfter, isInsertMode));
		
		return errorList;
	}
}
