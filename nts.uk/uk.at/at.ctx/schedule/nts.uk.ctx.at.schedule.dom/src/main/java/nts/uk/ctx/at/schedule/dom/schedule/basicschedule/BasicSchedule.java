/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * The Class BasicSchedule.
 */
// 勤務予定基本情報
@Getter
public class BasicSchedule extends AggregateRoot {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	/** The work type code. */
	// 勤務種類
	private String workTypeCode;

	/** The work time code. */
	// 就業時間帯
	private String workTimeCode;

	/** The confirmed atr. */
	// 確定区分
	private ConfirmedAtr confirmedAtr;

	/** The work day atr. */
	// 稼働日区分
	private WorkdayDivision workDayAtr;

	/** The work schedule time zones. */
	// 勤務予定時間帯
	private List<WorkScheduleTimeZone> workScheduleTimeZones;

	/** The work schedule breaks. */
	// 勤務予定休憩
	private List<WorkScheduleBreak> workScheduleBreaks;

	/** The work schedule time. */
	// 勤務予定時間
	private Optional<WorkScheduleTime> workScheduleTime;

	/** The work schedule person fees. */
	// 勤務予定人件費
	private List<WorkSchedulePersonFee> workSchedulePersonFees;
	
	/** The child care schedules. */
	// 勤務予定育児介護時間帯
	private List<ChildCareSchedule> childCareSchedules;

	/**
	 * Instantiates a new basic schedule.
	 *
	 * @param memento
	 */
	public BasicSchedule(BasicScheduleGetMemento memento) {
		this.employeeId = memento.getEmployeeId();
		this.date = memento.getDate();
		this.workTypeCode = memento.getWorkTypeCode();
		this.workTimeCode = memento.getWorkTimeCode();
		this.confirmedAtr = memento.getConfirmedAtr();
		this.workDayAtr = memento.getWorkDayAtr();
		this.workScheduleTimeZones = memento.getWorkScheduleTimeZones();
		this.workScheduleBreaks = memento.getWorkScheduleBreaks();
		this.workScheduleTime = memento.getWorkScheduleTime();
		this.workSchedulePersonFees = memento.getWorkSchedulePersonFees();
		this.childCareSchedules = memento.getChildCareSchedules();
	}

	/**
	 * Constructor custom
	 * 
	 * @param employeeId
	 * @param date
	 * @param workTypeCode
	 * @param workTimeCode
	 * @param confirmedAtr
	 * @param workDayAtr
	 */
	public BasicSchedule(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			ConfirmedAtr confirmedAtr, WorkdayDivision workDayAtr) {
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
		this.workDayAtr = workDayAtr;
	}

	/**
	 * Creates the from java type.
	 *
	 * @param sId the s id
	 * @param date the date
	 * @param workTypeCode the work type code
	 * @param workTimeCode the work time code
	 * @param confirmedAtr the confirmed atr
	 * @param workDayAtr the work day atr
	 * @return the basic schedule
	 */
	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCode,
			String workTimeCode, int confirmedAtr, int workDayAtr) {
		return new BasicSchedule(sId, date, workTypeCode, workTimeCode,
				EnumAdaptor.valueOf(confirmedAtr, ConfirmedAtr.class),
				EnumAdaptor.valueOf(workDayAtr, WorkdayDivision.class));
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(BasicScheduleSetMemento memento) {
		memento.setEmployeeId(this.employeeId);
		memento.setDate(this.date);
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setConfirmedAtr(this.confirmedAtr);
		memento.setWorkDayAtr(this.workDayAtr);
		memento.setWorkScheduleTimeZones(this.workScheduleTimeZones);
		memento.setWorkScheduleBreaks(this.workScheduleBreaks);
		memento.setWorkScheduleTime(this.workScheduleTime);
		memento.setWorkSchedulePersonFees(this.workSchedulePersonFees);
		memento.setChildCareSchedules(this.childCareSchedules);
	}
}
