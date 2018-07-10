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
 * Insert or Update data to DB BASIC_SCHEDULE. If error exist return error
 * 
 * @author sonnh1
 */
@Stateless
public class RegisterBasicScheduleCommandHandler
		extends CommandHandlerWithResult<List<RegisterBasicScheduleCommand>, List<String>> {
	@Inject
	private RegisterBasicScheduleService basicScheduleService;

	@Override
	protected List<String> handle(CommandHandlerContext<List<RegisterBasicScheduleCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<BasicSchedule> bScheduleCommand = context.getCommand().stream().map(x -> x.toDomain()).collect(Collectors.toList());

		return basicScheduleService.register(companyId, bScheduleCommand);
	}
}
