package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
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

	@Override
	protected List<String> handle(CommandHandlerContext<DataRegisterBasicSchedule> context) {
		String companyId = AppContexts.user().companyId();
		DataRegisterBasicSchedule command = context.getCommand();
		int modeDisplay = command.getModeDisplay();
		List<RegisterBasicScheduleCommand> listRegisterBasicScheduleCommand = command.getListRegisterBasicSchedule();
		List<BasicSchedule> listBScheduleCommand = listRegisterBasicScheduleCommand.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		return basicScheduleService.register(companyId, Integer.valueOf(modeDisplay), listBScheduleCommand);
	}
}
