package nts.uk.ctx.at.record.app.command.dailyperform.editstate;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;

@Stateless
public class EditStateOfDailyPerformCommandHandler extends CommandFacade<EditStateOfDailyPerformCommand> {

	@Inject
	private EditStateOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<EditStateOfDailyPerformCommand> context) {
		EditStateOfDailyPerformCommand command = context.getCommand();
		repo.add(toDomain(command.getEmployeeId(), command.getWorkDate(), command.getData()));
	}

	private List<EditStateOfDailyPerformance> toDomain(String employeeId, GeneralDate date,
			List<EditStateOfDailyPerformanceDto> dto) {
		return ConvertHelper.mapTo(dto, (c) -> new EditStateOfDailyPerformance(employeeId, c.getAttendanceItemId(),
				date, EnumAdaptor.valueOf(c.getEditStateSetting(), EditStateSetting.class)));
	}

}
