/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class RegisterBasicScheduleCommand {
	private String employeeId;
	private GeneralDate date;
	private String workTypeCode;
	private String workTimeCode;
	private int confirmedAtr;
	private List<WorkScheduleTimeZoneSaveCommand> workScheduleTimeZoneSaveCommands;
	private List<WorkScheduleStateCommands> workScheduleStateCommands;

	/**
	 * To domain.
	 *
	 * @return the basic schedule
	 */
	public BasicSchedule toDomain() {
		return new BasicSchedule(new BasicScheduleGetMemento() {

			@Override
			public String getWorkTypeCode() {
				return workTypeCode;
			}

			@Override
			public String getWorkTimeCode() {
				return workTimeCode;
			}

			@Override
			public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
				List<WorkScheduleTimeZone> timeZones = new ArrayList<WorkScheduleTimeZone>();
				if (workScheduleTimeZoneSaveCommands != null) {
					workScheduleTimeZoneSaveCommands.stream().forEach(command -> {
						WorkScheduleTimeZone scheduleTimeZone = new WorkScheduleTimeZone(command);
						timeZones.add(scheduleTimeZone);
					});
				}
				return timeZones;
			}

			@Override
			public Optional<WorkScheduleTime> getWorkScheduleTime() {
				return Optional.empty();
			}

			@Override
			public List<WorkSchedulePersonFee> getWorkSchedulePersonFees() {
				return new ArrayList<>();
			}

			@Override
			public List<WorkScheduleBreak> getWorkScheduleBreaks() {
				return new ArrayList<>();
			}

			@Override
			public String getEmployeeId() {
				return employeeId;
			}

			@Override
			public GeneralDate getDate() {
				return date;
			}

			@Override
			public ConfirmedAtr getConfirmedAtr() {
				return ConfirmedAtr.valueOf(confirmedAtr);
			}

			@Override
			public List<ChildCareSchedule> getChildCareSchedules() {
				return new ArrayList<>();
			}
		});
	}
}
