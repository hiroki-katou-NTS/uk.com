package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;

@Stateless
public class BasicScheduleUpdateCommandHandle extends CommandHandler<BasicScheduleUpdateCommand> {

	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	@Override
	protected void handle(CommandHandlerContext<BasicScheduleUpdateCommand> context) {
		// TODO Auto-generated method stub
		BasicScheduleUpdateCommand command = context.getCommand();
		List<String> employeeIds = command.getEmployeeIds();
		List<GeneralDate> dates = command.getDates();
		int confirmedAtrValue = command.getConfirmedAtr();
		boolean checkedHandler = command.isCheckedHandler();

		employeeIds.stream().forEach((employeeId) -> {
			dates.stream().forEach(date -> {
				Optional<BasicSchedule> optional = basicScheduleRepository.find(employeeId, date);
				if (optional.isPresent()) {
					ConfirmedAtr confirmeAtr = ConfirmedAtr.valueOf(confirmedAtrValue);
					BasicSchedule oldBasicSchedule = optional.get();
					BasicSchedule newBasicSchedule = new BasicSchedule(oldBasicSchedule.getEmployeeId(),
							oldBasicSchedule.getDate(), oldBasicSchedule.getWorkTypeCode(),
							oldBasicSchedule.getWorkTimeCode(), confirmeAtr, oldBasicSchedule.getWorkDayAtr());
					// TODO Checkhandler
					basicScheduleRepository.update(newBasicSchedule);
				}
			});

		});
	}

}
