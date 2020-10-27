/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workingcondition.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.HourlyPaymentAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondHistItem;

/**
 * The Class JpaWorkingConditionItemGetMemento.
 */
public class JpaWorkingConditionItemGetMemento implements WorkingConditionItemGetMemento {

	/** The entity. */
	private KshmtWorkcondHistItem entity;
	/** The entity. */
	private List<KshmtWorkcondCtg> perWorkCat;
	/** The entity. */
	private List<KshmtWorkcondWeek> perDayWeek;
	/** The entity. */
	private KshmtWorkcondScheMeth method;

	/**
	 * Instantiates a new jpa working condition item get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkingConditionItemGetMemento(KshmtWorkcondHistItem entity) {
		super();
		this.entity = entity;
		this.perDayWeek = entity.getKshmtWorkcondWeeks();
		this.perWorkCat = entity.getKshmtWorkcondCtgs();
		this.method = entity.getKshmtWorkcondScheMeth();
	}
	

	/**
	 * Instantiates a new jpa working condition item get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkingConditionItemGetMemento(KshmtWorkcondHistItem entity, Map<String, Object> enums) {
		super();
		this.entity = entity;
		//就業区分 - 労働制
		enums.put("IS00252", this.entity.getLaborSys());
		//休暇時の加算時間 - 休暇加算時間利用区分
		enums.put("IS00248", this.entity.getVacationAddTimeAtr());
		//休暇時の就業時間帯の自動設定 - 就業時間帯の自動設定区分
		enums.put("IS00247", this.entity.getAutoIntervalSetAtr());
		//勤務時間の自動設定 - 自動打刻セット区分
		enums.put("IS00258", this.entity.getAutoStampSetAtr());		
		//時給者区分 - 時給者区分
		enums.put("IS00259", this.entity.getHourlyPayAtr());
		//スケジュール管理 - 予定管理区分
		enums.put("IS00121", this.entity.getScheManagementAtr());
		
		//スケジュール作成方法 - 予定作成方法.基本作成方法
		enums.put("IS00123", this.entity.getKshmtWorkcondScheMeth().getBasicCreateMethod());
		//カレンダーの参照先 - 予定作成方法.営業日カレンダーによる勤務予定作成.営業日カレンダーの参照先
		enums.put("IS00124", this.entity.getKshmtWorkcondScheMeth().getRefBusinessDayCalendar());	
		//基本勤務の参照先 - 予定作成方法.営業日カレンダーによる勤務予定作成.基本勤務の参照先
		enums.put("IS00125", this.entity.getKshmtWorkcondScheMeth().getRefBasicWork());
		//就業時間帯の参照先 - 予定作成方法.月間パターンによる勤務予定作成.勤務種類と就業時間帯の参照先（基本作成方法＝月間パターンの場合）
		enums.put("IS00126", this.entity.getKshmtWorkcondScheMeth().getRefWorkingHours());
	}
	
	public JpaWorkingConditionItemGetMemento(KshmtWorkcondHistItem entity, List<KshmtWorkcondCtg> perWorkCat, 
			List<KshmtWorkcondWeek> perDayWeek, KshmtWorkcondScheMeth method) {
		super();
		this.entity = entity;
		this.perWorkCat = perWorkCat;
		this.perDayWeek = perDayWeek;
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getScheduleManagementAtr()
	 */
	@Override
	public ManageAtr getScheduleManagementAtr() {
		try {
			return ManageAtr.valueOf(this.entity.getScheManagementAtr());
		}catch(Exception e) {
			return ManageAtr.NOTUSE;
		}

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getVacationAddedTimeAtr()
	 */
	@Override
	public NotUseAtr getVacationAddedTimeAtr() {
		try {
			return NotUseAtr.valueOf(this.entity.getVacationAddTimeAtr());
		} catch (Exception e) {
			return NotUseAtr.NOTUSE;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getLaborSystem()
	 */
	@Override
	public WorkingSystem getLaborSystem() {
		try {
			return WorkingSystem.valueOf(this.entity.getLaborSys());
		} catch (Exception e) {
			return WorkingSystem.REGULAR_WORK;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getWorkCategory()
	 */
	@Override
	public PersonalWorkCategory getWorkCategory() {
		if(perWorkCat == null){
			perWorkCat = this.entity.getKshmtWorkcondCtgs();
		}
		
		return new PersonalWorkCategory(new JpaPerWorkCatGetMemento(perWorkCat));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getContractTime()
	 */
	@Override
	public LaborContractTime getContractTime() {
		return new LaborContractTime(this.entity.getContractTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getAutoIntervalSetAtr()
	 */
	@Override
	public NotUseAtr getAutoIntervalSetAtr() {
		try {
			return NotUseAtr.valueOf(this.entity.getAutoIntervalSetAtr());
		}catch(Exception e) {
			return NotUseAtr.NOTUSE;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getHistoryId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getWorkDayOfWeek()
	 */
	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		if(perDayWeek == null){
			perDayWeek = this.entity.getKshmtWorkcondWeeks();
		}
		
		return new PersonalDayOfWeek(new JpaPerDayOfWeekGetMemento(perDayWeek));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getAutoStampSetAtr()
	 */
	@Override
	public NotUseAtr getAutoStampSetAtr() {
		return NotUseAtr.valueOf(this.entity.getAutoStampSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getScheduleMethod()
	 */
	@Override
	public Optional<ScheduleMethod> getScheduleMethod() {
		if(method == null){
			method = this.entity.getKshmtWorkcondScheMeth();
		}
		
		return method == null ? Optional.empty() : 
			Optional.of(new ScheduleMethod(new JpaScheduleMethodGetMemento(method)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getHolidayAddTimeSet()
	 */
	@Override
	public Optional<BreakdownTimeDay> getHolidayAddTimeSet() {
		Integer hdAddTimeOneDay = this.entity.getHdAddTimeOneDay();
		return hdAddTimeOneDay != null
				? Optional.of(
						new BreakdownTimeDay(this.entity.getHdAddTimeOneDay() != null? new AttendanceTime(this.entity.getHdAddTimeOneDay()): null,
								this.entity.getHdAddTimeMorning() != null ? new AttendanceTime(this.entity.getHdAddTimeMorning()) : null,
										this.entity.getHdAddTimeAfternoon() != null ? new AttendanceTime(this.entity.getHdAddTimeAfternoon()): null))
				: Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getHourlyPaymentAtr()
	 */
	@Override
	public HourlyPaymentAtr getHourlyPaymentAtr() {
		try {
			return HourlyPaymentAtr.valueOf(this.entity.getHourlyPayAtr());
		} catch (Exception e) {
			return HourlyPaymentAtr.OOUTSIDE_TIME_PAY;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getTimeApply()
	 */
	@Override
	public Optional<BonusPaySettingCode> getTimeApply() {
		return Optional.of(new BonusPaySettingCode(this.entity.getTimeApply()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getMonthlyPattern()
	 */
	@Override
	public Optional<MonthlyPatternCode> getMonthlyPattern() {
		return Optional.of(new MonthlyPatternCode(this.entity.getMonthlyPattern()));
	}

}
