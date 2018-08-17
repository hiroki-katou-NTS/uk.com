/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;

/**
 * The Class BasicSchedule. 勤務予定基本情報
 */
@Getter
@Setter
@AllArgsConstructor
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
	// 予定確定区分
	private ConfirmedAtr confirmedAtr;

	/** The work schedule time zones. */
	// 勤務予定時間帯
	private List<WorkScheduleTimeZone> workScheduleTimeZones = new ArrayList<>();

	/** The work schedule breaks. */
	// 勤務予定休憩
	private List<WorkScheduleBreak> workScheduleBreaks  = new ArrayList<>();

	/** The work schedule time. */
	// 勤務予定時間
	private Optional<WorkScheduleTime> workScheduleTime = Optional.empty();

	/** The work schedule person fees. */
	// 勤務予定人件費
	private List<WorkSchedulePersonFee> workSchedulePersonFees  = new ArrayList<>();

	/** The child care schedules. */
	// 勤務予定育児介護時間帯
	private List<ChildCareSchedule> childCareSchedules = new ArrayList<>();
	
	/** 勤務予定マスタ情報 **/
	private ScheMasterInfo workScheduleMaster;
	
	private List<WorkScheduleState> workScheduleStates  = new ArrayList<>();

	/**
	 * Instantiates a new basic schedule.
	 *
	 * @param memento
	 */
	public BasicSchedule(BasicScheduleGetMemento memento) {
		this.employeeId = memento.getEmployeeId();
		this.date = memento.getDate();
		this.workTypeCode = memento.getWorkTypeCode();
		this.workTimeCode = StringUtil.isNullOrEmpty(memento.getWorkTimeCode(), true) ? null : memento.getWorkTimeCode();
		this.confirmedAtr = memento.getConfirmedAtr();
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
	 */
	public BasicSchedule(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			ConfirmedAtr confirmedAtr) {
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
	}
	
	

	/**
	 * Creates the from java type.
	 *
	 * @param sId
	 *            the s id
	 * @param date
	 *            the date
	 * @param workTypeCode
	 *            the work type code
	 * @param workTimeCode
	 *            the work time code
	 * @param confirmedAtr
	 *            the confirmed atr
	 * @return the basic schedule
	 */
	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCode,
			String workTimeCode, int confirmedAtr) {
		return new BasicSchedule(sId, date, workTypeCode, workTimeCode,
				EnumAdaptor.valueOf(confirmedAtr, ConfirmedAtr.class));
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(BasicScheduleSetMemento memento) {
		memento.setEmployeeId(this.employeeId);
		memento.setDate(this.date);
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setConfirmedAtr(this.confirmedAtr);
		memento.setWorkScheduleTimeZones(this.workScheduleTimeZones);
		memento.setWorkScheduleBreaks(this.workScheduleBreaks);
		memento.setWorkScheduleTime(this.workScheduleTime);
		memento.setWorkSchedulePersonFees(this.workSchedulePersonFees);
		memento.setChildCareSchedules(this.childCareSchedules);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childCareSchedules == null) ? 0 : childCareSchedules.hashCode());
		result = prime * result + ((confirmedAtr == null) ? 0 : confirmedAtr.hashCode());
		result = prime * result + ((workScheduleBreaks == null) ? 0 : workScheduleBreaks.hashCode());
		result = prime * result + ((workSchedulePersonFees == null) ? 0 : workSchedulePersonFees.hashCode());
		result = prime * result + ((workScheduleTime == null) ? 0 : workScheduleTime.hashCode());
		result = prime * result + ((workScheduleTimeZones == null) ? 0 : workScheduleTimeZones.hashCode());
		result = prime * result + ((workTimeCode == null) ? 0 : workTimeCode.hashCode());
		result = prime * result + ((workTypeCode == null) ? 0 : workTypeCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicSchedule other = (BasicSchedule) obj;
		if (childCareSchedules == null) {
			if (other.childCareSchedules != null)
				return false;
		} else if (!childCareSchedules.equals(other.childCareSchedules))
			return false;
		if (confirmedAtr != other.confirmedAtr)
			return false;
		if (workScheduleBreaks == null) {
			if (other.workScheduleBreaks != null)
				return false;
		} else if (!workScheduleBreaks.equals(other.workScheduleBreaks))
			return false;
		if (workSchedulePersonFees == null) {
			if (other.workSchedulePersonFees != null)
				return false;
		} else if (!workSchedulePersonFees.equals(other.workSchedulePersonFees))
			return false;
		if (workScheduleTime == null) {
			if (other.workScheduleTime != null)
				return false;
		} else if (!workScheduleTime.equals(other.workScheduleTime))
			return false;
		if (workScheduleTimeZones == null) {
			if (other.workScheduleTimeZones != null)
				return false;
		} else if (!workScheduleTimeZones.equals(other.workScheduleTimeZones))
			return false;
		if (workTimeCode == null) {
			if (other.workTimeCode != null)
				return false;
		} else if (!workTimeCode.equals(other.workTimeCode))
			return false;
		if (workTypeCode == null) {
			if (other.workTypeCode != null)
				return false;
		} else if (!workTypeCode.equals(other.workTypeCode))
			return false;
		return true;
	}
	
	public boolean diffWorkTypeCode(String workTypeCd){
		return !workTypeCode.equals(workTypeCd);
	}
	
	public boolean diffWorkTimeCode(String workTimeCd){
		if(workTimeCode == null && workTimeCd == null){
			return false;
		}
		if(workTimeCode == null && workTimeCd != null){
			return true;
		}
		return !workTimeCode.equals(workTimeCd);
	}
	
	public boolean diffConfirmedAtr(ConfirmedAtr cfAtr){
		return confirmedAtr.value != cfAtr.value;
	}

	public void setWorkScheduleTimeZones(List<WorkScheduleTimeZone> workScheduleTimeZones) {
		this.workScheduleTimeZones = workScheduleTimeZones;
	}
	
	public void setChildCareSchedules(List<ChildCareSchedule> childCareSchedules) {
		this.childCareSchedules = childCareSchedules;
	}
	
	public void setWorkScheduleMaster(ScheMasterInfo scheduleMaster) {
		this.workScheduleMaster = scheduleMaster;
	}
	
	public void setWorkScheduleTime(WorkScheduleTime scheduleTime) {
		this.workScheduleTime = Optional.ofNullable(scheduleTime);
	}
	
	public void setWorkScheduleState(List<WorkScheduleState> scheduleState) {
		this.workScheduleStates = scheduleState;
	}
	
	public void setWorkScheduleBreaks(List<WorkScheduleBreak> scheduleBreaks) {
		this.workScheduleBreaks = scheduleBreaks;
	}
	
	public BasicSchedule(String workTypeCode, ScheMasterInfo workScheduleMaster) {
		super();
		this.workTypeCode = workTypeCode;
		this.workScheduleMaster = workScheduleMaster;
	}
	
	public BasicSchedule(String sId, GeneralDate date, String workTypeCode, String workTimeCode, ConfirmedAtr confirmedAtr, ScheMasterInfo workScheduleMaster) {
		super();
		this.employeeId = sId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.confirmedAtr = confirmedAtr;
		this.workScheduleMaster = workScheduleMaster;
	}
}
