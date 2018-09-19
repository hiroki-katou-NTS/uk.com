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
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;

/**
 * The Class BasicScheduleSaveCommand.
 */

@Getter
@Setter
public class BasicScheduleSaveCommand {

	/** The employee id. */
	private String employeeId;
	
	/** The ymd. */
	private GeneralDate ymd;
	
	/** The confirmed atr. */
	private int confirmedAtr;
	
	/** The worktype code. */
	private String worktypeCode;
	
	/** The worktime code. */
	private String worktimeCode;
	
	/** The work schedule time zones. */
    private List<WorkScheduleTimeZoneSaveCommand> workScheduleTimeZones = new ArrayList<>();
	
	/** The work schedule breaks. */
	private List<WorkScheduleBreakSaveCommand> workScheduleBreaks = new ArrayList<>();
	
	private Optional<WorkScheduleTime> workScheduleTime = Optional.empty();
	
	/** The child care schedules. */
	private List<ChildCareScheduleSaveCommand> childCareSchedules = new ArrayList<>();
	
	private ScheMasterInfo workScheduleMaster;
	
	/**
	 * Update work schedule time zones.
	 *
	 * @param workTimeSet the work time set
	 */
	public void updateWorkScheduleTimeZones(PrescribedTimezoneSetting workTimeSet) {
		this.workScheduleTimeZones = workTimeSet.getLstTimezone().stream().filter(timezone -> timezone.isUsed())
				.map(timezone -> this.convertTimeZoneToScheduleTimeZone(timezone)).collect(Collectors.toList());
	}
	
	/**
	 * Update work schedule time zones.
	 *
	 * @param workTimeSet the work time set
	 */
	public void updateWorkScheduleTimeZonesKeepBounceAtr(PrescribedTimezoneSetting workTimeSet, WorkType workType) {
		List<WorkScheduleTimeZoneSaveCommand> lstTimeZone = new ArrayList<>();
		workTimeSet.getLstTimezone().stream().filter(timezone -> timezone.isUsed()).forEach(timezone -> {
			if (timezone.getStart() != null && timezone.getEnd() != null) {
				lstTimeZone.add(this.convertTimeZoneToScheduleTimeZoneKeepBounceAtr(timezone, workType));
			}
		});
		this.workScheduleTimeZones = lstTimeZone;
	}
	
	/**
	 * Add schedule bounce atr.
	 * 
	 * @param workType
	 * @return
	 */
	private BounceAtr addScheduleBounce(WorkType workType) {
		List<WorkTypeSet> workTypeSetList = workType.getWorkTypeSetList();
		if (AttendanceHolidayAttr.FULL_TIME == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet = workTypeSetList.get(0);
			return getBounceAtr(workTypeSet);
		} else if (AttendanceHolidayAttr.AFTERNOON == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet1 = workTypeSetList.stream()
					.filter(x -> WorkAtr.Afternoon.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet1);
		} else if (AttendanceHolidayAttr.MORNING == workType.getAttendanceHolidayAttr()) {
			WorkTypeSet workTypeSet2 = workTypeSetList.stream()
					.filter(x -> WorkAtr.Monring.value == x.getWorkAtr().value).findFirst().get();
			return getBounceAtr(workTypeSet2);
		}

		return BounceAtr.NO_DIRECT_BOUNCE;
	}
	
	/**
	 * @param workTypeSet
	 * @return
	 */
	private BounceAtr getBounceAtr(WorkTypeSet workTypeSet) {
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.NO_DIRECT_BOUNCE;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.DIRECTLY_ONLY;
		} else if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.BOUNCE_ONLY;
		}

		return BounceAtr.DIRECT_BOUNCE;
	}
	
	/**
	 * Convert time zone to schedule time zone.
	 *
	 * @param timezone the timezone
	 * @return the work schedule time zone save command
	 */
	// 勤務予定時間帯
	private WorkScheduleTimeZoneSaveCommand convertTimeZoneToScheduleTimeZone(TimezoneUse timezone) {
		WorkScheduleTimeZoneSaveCommand command = new WorkScheduleTimeZoneSaveCommand();

		// 予定勤務回数 = 取得した勤務予定時間帯. 勤務NO
		command.setScheduleCnt(timezone.getWorkNo());

		// 予定開始時刻 = 取得した勤務予定時間帯. 開始
		command.setScheduleStartClock(timezone.getStart() != null ? timezone.getStart().valueAsMinutes() : null);

		// 予定終了時刻 = 取得した勤務予定時間帯. 終了
		command.setScheduleEndClock(timezone.getEnd() != null ? timezone.getEnd().valueAsMinutes() : null);

		return command;
	}
	
	/**
	 * Convert time zone to schedule time zone.
	 *
	 * @param timezone the timezone
	 * @return the work schedule time zone save command
	 */
	// 勤務予定時間帯
	private WorkScheduleTimeZoneSaveCommand convertTimeZoneToScheduleTimeZoneKeepBounceAtr(TimezoneUse timezone, WorkType workType) {
		WorkScheduleTimeZoneSaveCommand command = new WorkScheduleTimeZoneSaveCommand();

		command.setBounceAtr(this.addScheduleBounce(workType).value);
		
		// 予定勤務回数 = 取得した勤務予定時間帯. 勤務NO
		command.setScheduleCnt(timezone.getWorkNo());

		// 予定開始時刻 = 取得した勤務予定時間帯. 開始
		command.setScheduleStartClock(timezone.getStart() != null ? timezone.getStart().valueAsMinutes() : null);

		// 予定終了時刻 = 取得した勤務予定時間帯. 終了
		command.setScheduleEndClock(timezone.getEnd() != null ? timezone.getEnd().valueAsMinutes() : null);

		return command;
	}
	
	/**
	 * To domain.
	 *
	 * @return the basic schedule
	 */
	public BasicSchedule toDomain(){
		BasicSchedule result = new BasicSchedule(new BasicScheduleSaveCommandGetMementoImpl());
		result.setWorkScheduleMaster(this.workScheduleMaster);
		return result;
	}
	
	/**
	 * The Class BasicScheduleSaveCommandGetMementoImpl.
	 */
	class BasicScheduleSaveCommandGetMementoImpl implements BasicScheduleGetMemento{

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getEmployeeId()
		 */
		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getDate()
		 */
		@Override
		public GeneralDate getDate() {
			return ymd;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkTypeCode()
		 */
		@Override
		public String getWorkTypeCode() {
			return worktypeCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkTimeCode()
		 */
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
			return ConfirmedAtr.valueOf(confirmedAtr);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkScheduleTimeZones()
		 */
		@Override
		public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
			if (CollectionUtil.isEmpty(workScheduleTimeZones)) {
				return new ArrayList<>();
			}
			return workScheduleTimeZones.stream().map(command -> new WorkScheduleTimeZone(command))
					.collect(Collectors.toList());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkScheduleBreaks()
		 */
		@Override
		public List<WorkScheduleBreak> getWorkScheduleBreaks() {
			if (CollectionUtil.isEmpty(workScheduleBreaks)) {
				return new ArrayList<>();
			}
			return workScheduleBreaks.stream().map(command -> new WorkScheduleBreak(command))
					.collect(Collectors.toList());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkScheduleTime()
		 */
		@Override
		public Optional<WorkScheduleTime> getWorkScheduleTime() {
			return workScheduleTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getWorkSchedulePersonFees()
		 */
		@Override
		public List<WorkSchedulePersonFee> getWorkSchedulePersonFees() {
			return new ArrayList<>();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
		 * BasicScheduleGetMemento#getChildCareSchedules()
		 */
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
