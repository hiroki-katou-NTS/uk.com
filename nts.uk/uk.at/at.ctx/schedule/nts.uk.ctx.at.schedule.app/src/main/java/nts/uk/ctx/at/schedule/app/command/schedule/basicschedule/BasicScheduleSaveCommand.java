/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * The Class BasicScheduleSaveCommand.
 */

@Getter
@Setter
public class BasicScheduleSaveCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The ymd. */
	// 年月日
	private GeneralDate ymd;
	
	/** The confirmed atr. */
	// 予定確定区分
	private int confirmedAtr;
	
	/** The worktype code. */
	// 勤務種類 
	private String worktypeCode;
	
	/** The worktime code. */
	// 就業時間帯
	private String worktimeCode;
	
	/** The work schedule time zones. */
	// 勤務予定時間帯
	private List<WorkScheduleTimeZoneSaveCommand> workScheduleTimeZones;
	
	/** The work schedule breaks. */
	// 勤務予定休憩
	private List<WorkScheduleBreakSaveCommand> workScheduleBreaks;
	
	/** The child care schedules. */
	// 勤務予定育児介護時間帯
	private List<ChildCareScheduleSaveCommand> childCareSchedules;
	
	/**
	 * To domain.
	 *
	 * @return the basic schedule
	 */
	public BasicSchedule toDomain(){
		return new BasicSchedule(new BasicScheduleSaveCommandGetMementoImpl());
	}
	/**
	 * The Class BasicScheduleSaveCommandGetMementoImpl.
	 */
	class BasicScheduleSaveCommandGetMementoImpl implements BasicScheduleGetMemento{

		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		@Override
		public GeneralDate getDate() {
			return ymd;
		}

		@Override
		public String getWorkTypeCode() {
			return worktypeCode;
		}

		@Override
		public String getWorkTimeCode() {
			return worktimeCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getConfirmedAtr()
		 */
		@Override
		public ConfirmedAtr getConfirmedAtr() {
			return ConfirmedAtr.CONFIRMED;
		}

		@Override
		public WorkdayDivision getWorkDayAtr() {
			return WorkdayDivision.WORKINGDAYS;
		}

		@Override
		public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
			if(CollectionUtil.isEmpty(workScheduleTimeZones)){
				return new ArrayList<>();
			}
			return workScheduleTimeZones.stream().map(command -> new WorkScheduleTimeZone(command))
					.collect(Collectors.toList());
		}

		@Override
		public List<WorkScheduleBreak> getWorkScheduleBreaks() {
			if(CollectionUtil.isEmpty(workScheduleBreaks)){
				return new ArrayList<>();
			}
			return workScheduleBreaks.stream().map(command -> new WorkScheduleBreak(command))
					.collect(Collectors.toList());
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
		public List<ChildCareSchedule> getChildCareSchedules() {
			if (CollectionUtil.isEmpty(childCareSchedules)) {
				return new ArrayList<>();
			}
			return childCareSchedules.stream().map(command -> new ChildCareSchedule(command))
					.collect(Collectors.toList());
		}
		
	}
}
