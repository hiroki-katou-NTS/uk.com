package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.app.command.schedule.workschedule.RegisWorkScheduleCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.workschedule.ResultRegisWorkSchedule;
import nts.uk.ctx.at.schedule.app.command.schedule.workschedule.WorkInformationDto;
import nts.uk.ctx.at.schedule.app.command.schedule.workschedule.WorkScheduleSaveCommand;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.RegisterWorkScheduleInputCommand;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class RegisterWorkSceduleCommandHandler extends CommandHandlerWithResult<RegisterWorkScheduleInputCommand, ResultRegisWorkSchedule> {

	@Inject
	private RegisWorkScheduleCommandHandler regisWorkSchedule;

	public ResultRegisWorkSchedule add(RegisterWorkScheduleInputCommand param) {
		String sid = param.sid;

		List<WorkScheduleSaveCommand> commands = param.registerDates.stream().map(m -> {
			Map<Integer, TimeWithDayAttr> map = new HashMap<Integer, TimeWithDayAttr>();
			map.put(31, new TimeWithDayAttr(m.getStart()));
			map.put(34, new TimeWithDayAttr(m.getEnd()));
			
			WorkInformationDto wif = new WorkInformationDto(m.getWorkTypeCd(), m.getWorkTimeCd());

			return new WorkScheduleSaveCommand(sid, m.getDate(), wif, map, new ArrayList<>());
		}).collect(Collectors.toList());
		
		return this.regisWorkSchedule.handle(commands);
	}

	@Override
	protected ResultRegisWorkSchedule handle(CommandHandlerContext<RegisterWorkScheduleInputCommand> context) {
		RegisterWorkScheduleInputCommand param = context.getCommand();
		String sid = param.sid;

		List<WorkScheduleSaveCommand> commands = param.registerDates.stream().map(m -> {
			Map<Integer, TimeWithDayAttr> map = new HashMap<Integer, TimeWithDayAttr>();
			map.put(31, new TimeWithDayAttr(m.getStart()));
			map.put(34, new TimeWithDayAttr(m.getEnd()));
			
			WorkInformationDto wif = new WorkInformationDto(m.getWorkTypeCd(), m.getWorkTimeCd());

			return new WorkScheduleSaveCommand(sid, m.getDate(), wif, map, new ArrayList<>());
		}).collect(Collectors.toList());
		
		return this.regisWorkSchedule.handle(commands);
	}
}
