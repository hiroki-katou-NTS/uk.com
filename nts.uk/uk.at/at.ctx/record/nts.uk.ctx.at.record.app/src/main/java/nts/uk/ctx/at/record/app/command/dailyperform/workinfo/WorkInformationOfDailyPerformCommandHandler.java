package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;

@Stateless
public class WorkInformationOfDailyPerformCommandHandler extends CommandFacade<WorkInformationOfDailyPerformCommand> {

	@Inject
	private WorkInformationRepository repo;

	@Override
	protected void handle(CommandHandlerContext<WorkInformationOfDailyPerformCommand> context) {
		WorkInformationOfDailyPerformCommand command = context.getCommand();
		repo.insert(toDomain(command.getEmployeeId(), command.getWorkDate(), command.getData()));
	}

	private WorkInfoOfDailyPerformance toDomain(String employeeId, GeneralDate date, WorkInformationOfDailyDto dto) {
		return new WorkInfoOfDailyPerformance(employeeId,
				new WorkInformation(dto.getActualWorkInfo().getWorkTimeCode(),
						dto.getActualWorkInfo().getWorkTypeCode()),
				new WorkInformation(dto.getPlanWorkInfo().getWorkTimeCode(), dto.getPlanWorkInfo().getWorkTypeCode()),
				EnumAdaptor.valueOf(dto.getCalculationState(), CalculationState.class),
				EnumAdaptor.valueOf(dto.getGoStraightAtr(), NotUseAttribute.class),
				EnumAdaptor.valueOf(dto.getBackStraightAtr(), NotUseAttribute.class), date,
				ConvertHelper.mapTo(dto.getScheduleTimeZone(),
						(c) -> new ScheduleTimeSheet(c.getWorkNo(), c.getWorking(), c.getLeave())));
	}

}
