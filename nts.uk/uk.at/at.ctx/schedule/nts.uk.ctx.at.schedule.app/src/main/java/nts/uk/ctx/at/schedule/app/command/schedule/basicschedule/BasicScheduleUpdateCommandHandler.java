package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;

@Stateless
public class BasicScheduleUpdateCommandHandler extends CommandHandler<BasicScheduleUpdateCommand> {

	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	@Override
	protected void handle(CommandHandlerContext<BasicScheduleUpdateCommand> context) {
		BasicScheduleUpdateCommand command = context.getCommand();
		List<String> employeeIds = command.getEmployeeIds();
		List<GeneralDate> dates = command.getDates();
		int confirmedAtrValue = command.getConfirmedAtr();
		// boolean checkedHandler = command.isCheckedHandler();

		employeeIds.stream().forEach((employeeId) -> {
			dates.stream().forEach(date -> {
				Optional<BasicSchedule> optional = basicScheduleRepository.find(employeeId, date);
				if (optional.isPresent()) {
					ConfirmedAtr confirmeAtr = ConfirmedAtr.valueOf(confirmedAtrValue);
					BasicSchedule oldBasicSchedule = optional.get();
					BasicSchedule newBasicSchedule = new BasicSchedule(new BasicScheduleGetMemento() {

						@Override
						public String getEmployeeId() {
							return employeeId;
						}

						@Override
						public GeneralDate getDate() {
							return date;
						}

						@Override
						public String getWorkTypeCode() {
							return oldBasicSchedule.getWorkTypeCode();
						}

						@Override
						public String getWorkTimeCode() {
							return oldBasicSchedule.getWorkTimeCode();
						}

						@Override
						public ConfirmedAtr getConfirmedAtr() {
							return confirmeAtr;
						}

						@Override
						public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
							return oldBasicSchedule.getWorkScheduleTimeZones();
						}

						@Override
						public List<WorkScheduleBreak> getWorkScheduleBreaks() {
							return oldBasicSchedule.getWorkScheduleBreaks();
						}

						@Override
						public Optional<WorkScheduleTime> getWorkScheduleTime() {
							return oldBasicSchedule.getWorkScheduleTime();
						}

						@Override
						public List<WorkSchedulePersonFee> getWorkSchedulePersonFees() {
							return oldBasicSchedule.getWorkSchedulePersonFees();
						}

						@Override
						public List<ChildCareSchedule> getChildCareSchedules() {
							return oldBasicSchedule.getChildCareSchedules();
						}

					});
					// TO-DO
					// Checkhandler
					basicScheduleRepository.update(newBasicSchedule);
				}
			});

		});
	}

}
