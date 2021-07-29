/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkDayAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkByIndividualWorkDay;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkTypeByIndividualWorkDay;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * The Class KshmtWorkcondHistItem.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKCOND_HIST_ITEM")
public class KshmtWorkcondHistItem extends ContractCompanyUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;
	
	@Id
	@Column(name = "HIST_ID")
	private String historyId;

	@Column(name = "SID")
	private String sid;
	
	/** The hourly pay atr */
	@Column(name = "HOURLY_PAY_ATR")
	private Integer hourlyPayAtr;

	/** The sche management atr. */
	@Column(name = "SCHE_MANAGEMENT_ATR")
	private int scheManagementAtr;

	/** The auto stamp set atr. */
	@Column(name = "AUTO_STAMP_SET_ATR")
	private int autoStampSetAtr;

	/** The auto interval set atr. */
	@Column(name = "AUTO_INTERVAL_SET_ATR")
	private int autoIntervalSetAtr;

	/** The vacation add time atr. */
	@Column(name = "VACATION_ADD_TIME_ATR")
	private int vacationAddTimeAtr;

	/** The contract time. */
	@Column(name = "CONTRACT_TIME")
	private int contractTime;

	/** The labor sys. */
	@Column(name = "LABOR_SYS")
	private int laborSys;

	/** The hd add time one day. */
	@Column(name = "HD_ADD_TIME_ONE_DAY")
	private Integer hdAddTimeOneDay;

	/** The hd add time morning. */
	@Column(name = "HD_ADD_TIME_MORNING")
	private Integer hdAddTimeMorning;

	/** The hd add time afternoon. */
	@Column(name = "HD_ADD_TIME_AFTERNOON")
	private Integer hdAddTimeAfternoon;
	
	/** The time apply. */
	@Column(name = "TIME_APPLY")
	private String timeApply;
	
	/** The monthly pattern. */
	@Column(name = "MONTHLY_PATTERN")
	private String monthlyPattern;

	/** The kshmt working cond item. */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID") })
	private KshmtWorkcondHist kshmtWorkingCond;

	/** The kshmt working cond items. */
	@JoinColumns({@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable=false, updatable=false) })
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private KshmtWorkcondScheMeth kshmtScheduleMethod;
	
	
	/**Kshmt Workcond WorkInfo */
	@JoinColumns({
		@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable=false, updatable=false),
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable=false, updatable=false)})
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private KshmtWorkcondWorkInfo kshmtWorkcondWorkInfo;
	
	/**  */
	@JoinColumns({
		@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable=false, updatable=false),
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable=false, updatable=false)})
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtWorkcondWorkTs> listKshmtWorkcondWorkTs;
	
	/**
	 * Instantiates a new kshmt working cond item.
	 */
	public KshmtWorkcondHistItem() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (historyId != null ? historyId.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkcondHistItem)) {
			return false;
		}
		KshmtWorkcondHistItem other = (KshmtWorkcondHistItem) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.historyId;
	}

	public KshmtWorkcondHistItem(int exclusVer, String historyId, String sid, Integer hourlyPayAtr,
			int scheManagementAtr, int autoStampSetAtr, int autoIntervalSetAtr, int vacationAddTimeAtr,
			int contractTime, int laborSys, Integer hdAddTimeOneDay, Integer hdAddTimeMorning,
			Integer hdAddTimeAfternoon, String timeApply, String monthlyPattern) {
		super();
		this.exclusVer = exclusVer;
		this.historyId = historyId;
		this.sid = sid;
		this.hourlyPayAtr = hourlyPayAtr;
		this.scheManagementAtr = scheManagementAtr;
		this.autoStampSetAtr = autoStampSetAtr;
		this.autoIntervalSetAtr = autoIntervalSetAtr;
		this.vacationAddTimeAtr = vacationAddTimeAtr;
		this.contractTime = contractTime;
		this.laborSys = laborSys;
		this.hdAddTimeOneDay = hdAddTimeOneDay;
		this.hdAddTimeMorning = hdAddTimeMorning;
		this.hdAddTimeAfternoon = hdAddTimeAfternoon;
		this.timeApply = timeApply;
		this.monthlyPattern = monthlyPattern;
	}
	
	
	
	public WorkingConditionItem toDomain() {
		BreakdownTimeDay breakdownTimeDay = null;
		if(this.hdAddTimeOneDay !=null && this.hdAddTimeMorning !=null && this.hdAddTimeAfternoon !=null ) {
			breakdownTimeDay = new BreakdownTimeDay(new AttendanceTime(this.hdAddTimeOneDay), new AttendanceTime(this.hdAddTimeMorning), new AttendanceTime(this.hdAddTimeAfternoon));
		}
		// set worktime
		Optional<SingleDaySchedule> weekdays = Optional.empty();
		Optional<SingleDaySchedule> holidayWork = Optional.empty();
		Optional<SingleDaySchedule> monday = Optional.empty();
		Optional<SingleDaySchedule> tuesday = Optional.empty();
		Optional<SingleDaySchedule> wednesday = Optional.empty();
		Optional<SingleDaySchedule> thursday = Optional.empty();
		Optional<SingleDaySchedule> friday = Optional.empty();
		Optional<SingleDaySchedule> saturday = Optional.empty();
		Optional<SingleDaySchedule> sunday = Optional.empty();
		
		for(KshmtWorkcondWorkTs entity : this.listKshmtWorkcondWorkTs) {
			switch(entity.pk.getPerWorkDayAtr()) {
			case 0:
				weekdays = Optional.of(entity.toDomain());
				break;
			case 1:
				holidayWork = Optional.of(entity.toDomain());
				break;
			case 2:
				monday = Optional.of(entity.toDomain());
				break;
			case 3:
				tuesday = Optional.of(entity.toDomain());
				break;
			case 4:
				wednesday = Optional.of(entity.toDomain());
				break;
			case 5:
				thursday = Optional.of(entity.toDomain());
				break;
			case 6:
				friday = Optional.of(entity.toDomain());
				break;
			case 7:
				saturday = Optional.of(entity.toDomain());
				break;
			case 8:
				sunday = Optional.of(entity.toDomain());
				break;
			default:
				break;
			}
		}
		
		// set worktype
		WorkTypeCode weekdayTimeWTypeCode = new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getWorkingDayWorktype());
		WorkTypeCode holidayWorkWTypeCode = new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getHolidayWorkWorktype());
		WorkTypeCode holidayTimeWTypeCode = new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getHolidayWorktype());
		Optional<WorkTypeCode> inLawBreakTimeWTypeCode = Optional.of(new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getLegalHolidayWorkWorktype()));
		Optional<WorkTypeCode> outsideLawBreakTimeWTypeCode = Optional.of(new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getILegalHolidayWorkWorktype()));
		Optional<WorkTypeCode> holidayAttendanceTimeWTypeCode = Optional.of(new WorkTypeCode(this.kshmtWorkcondWorkInfo == null ? null : this.kshmtWorkcondWorkInfo.getPublicHolidayWorkWorktype()));
		
		//set 曜日別勤務
		PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
		
		//set 区分別勤務
		PersonalWorkCategory workTime = new PersonalWorkCategory(weekdays.orElse(null), holidayWork.orElse(null), workDayOfWeek);
		WorkTypeByIndividualWorkDay workType = new WorkTypeByIndividualWorkDay(weekdayTimeWTypeCode, holidayWorkWTypeCode, holidayTimeWTypeCode, inLawBreakTimeWTypeCode, outsideLawBreakTimeWTypeCode, holidayAttendanceTimeWTypeCode);
		WorkByIndividualWorkDay workCategory = new WorkByIndividualWorkDay(workTime, workType);

		//
		ScheduleMethod scheduleMethod = (this.kshmtScheduleMethod ==null?null:this.kshmtScheduleMethod.toDomain());
		Integer hourlyPaymentAtr = this.hourlyPayAtr ==null?null:this.hourlyPayAtr;
		BonusPaySettingCode timeApply = this.timeApply==null?null:new BonusPaySettingCode(this.timeApply);
		MonthlyPatternCode monthlyPattern = this.monthlyPattern==null?null:new MonthlyPatternCode(this.monthlyPattern);
		return new WorkingConditionItem(
				this.historyId, 
				ManageAtr.valueOf(this.scheManagementAtr),
				workCategory, 
				NotUseAtr.valueOf(this.autoStampSetAtr),
				NotUseAtr.valueOf(this.autoIntervalSetAtr),
				this.sid, 
				NotUseAtr.valueOf(this.vacationAddTimeAtr),
				new LaborContractTime(this.contractTime), 
				WorkingSystem.valueOf(this.laborSys), 
				breakdownTimeDay,
				scheduleMethod, 
				hourlyPaymentAtr, 
				timeApply, 
				monthlyPattern);
	}
	
	public static KshmtWorkcondHistItem toEntity(WorkingConditionItem domain) {
		Integer hdAddTimeOneDay = null;
		Integer hdAddTimeMorning = null;
		Integer hdAddTimeAfternoon = null;
		if(domain.getHolidayAddTimeSet().isPresent()) {
			if(domain.getHolidayAddTimeSet().get().getOneDay()!=null) {
				hdAddTimeOneDay = domain.getHolidayAddTimeSet().get().getOneDay().v();
			}
			if(domain.getHolidayAddTimeSet().get().getMorning()!=null) {
				hdAddTimeMorning = domain.getHolidayAddTimeSet().get().getMorning().v();
			}
			if(domain.getHolidayAddTimeSet().get().getAfternoon()!=null) {
				hdAddTimeAfternoon = domain.getHolidayAddTimeSet().get().getAfternoon().v();
			}
		}
		
		// set workTIme
		PersonalWorkCategory workTime = domain.getWorkCategory().getWorkTime();
		List<KshmtWorkcondWorkTs> listKshmtWorkcondWorkTs = new ArrayList<>();
		
		// weekdayTime - 平日時
		if (domain.getWorkCategory().getWorkTime().getWeekdayTime() != null) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(
					domain.getWorkCategory().getWorkTime().getWeekdayTime(), domain.getHistoryId(),
					domain.getEmployeeId(), PersonalWorkDayAtr.WeekdayTime.value)); 
		}

		// holidayWork - 休日出勤時
		if (domain.getWorkCategory().getWorkTime().getHolidayWork() != null) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(
					domain.getWorkCategory().getWorkTime().getHolidayWork(), domain.getHistoryId(),
					domain.getEmployeeId(),PersonalWorkDayAtr.HolidayWork.value)); // 月曜日
		}
		
		//monday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getMonday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getMonday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Monday.value)); // 月曜日
		}
		//tuesday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getTuesday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getTuesday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Tuesday.value)); // 火曜日
		}
		//wednesday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getWednesday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getWednesday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Wednesday.value)); // 水曜日
		}
		//thursday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getThursday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getThursday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Thursday.value)); // 木曜日
		}
		//friday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getFriday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getFriday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Friday.value)); // 金曜日
		}
		//saturday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getSaturday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getSaturday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Saturday.value)); // 土曜日
		}
		//sunday
		if (domain.getWorkCategory().getWorkTime().getDayOfWeek().getSunday().isPresent()) {
			listKshmtWorkcondWorkTs.add(KshmtWorkcondWorkTs.toEntity(domain.getWorkCategory().getWorkTime().getDayOfWeek().getSunday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), PersonalWorkDayAtr.Sunday.value)); // 月曜日
		}
		
		// set workType
		WorkTypeByIndividualWorkDay workType = domain.getWorkCategory().getWorkType();
		KshmtWorkcondWorkInfo kshmtWorkcondWorkInfo = new KshmtWorkcondWorkInfo();
		kshmtWorkcondWorkInfo.setPk(new KshmtWorkcondWorkInfoPK(domain.getEmployeeId(), domain.getHistoryId()));
		if(workType != null){
			kshmtWorkcondWorkInfo.setWorkingDayWorktype(workType.getWeekdayTimeWTypeCode() == null? null : workType.getWeekdayTimeWTypeCode().v());
			kshmtWorkcondWorkInfo.setHolidayWorkWorktype(workType.getHolidayWorkWTypeCode() == null? null : workType.getHolidayWorkWTypeCode().v());
			kshmtWorkcondWorkInfo.setHolidayWorktype(workType.getHolidayTimeWTypeCode() == null? null : workType.getHolidayTimeWTypeCode().v());
			kshmtWorkcondWorkInfo.setLegalHolidayWorkWorktype(workType.getInLawBreakTimeWTypeCode().isPresent() ? workType.getInLawBreakTimeWTypeCode().get().v() : null);
			kshmtWorkcondWorkInfo.setILegalHolidayWorkWorktype(workType.getOutsideLawBreakTimeWTypeCode().isPresent() ? workType.getOutsideLawBreakTimeWTypeCode().get().v() : null);
			kshmtWorkcondWorkInfo.setPublicHolidayWorkWorktype(workType.getHolidayAttendanceTimeWTypeCode().isPresent() ? workType.getHolidayAttendanceTimeWTypeCode().get().v() : null);
		}

		if (workTime != null) {
			if (workTime.getWeekdayTime() != null) {
				kshmtWorkcondWorkInfo.setWeekdaysWorktime(workTime.getWeekdayTime().getWorkTimeCode().isPresent() ? workTime.getWeekdayTime().getWorkTimeCode().get().v() : null);
			}

			if (workTime.getHolidayWork() != null) {
				kshmtWorkcondWorkInfo.setHolidayWorkWorktime(workTime.getHolidayWork().getWorkTimeCode().isPresent() ? workTime.getHolidayWork().getWorkTimeCode().get().v() : null);
			}

			if (workTime.getDayOfWeek() != null) {
				kshmtWorkcondWorkInfo.setMondayWorkTime(workTime.getDayOfWeek().getMonday().isPresent() && workTime.getDayOfWeek().getMonday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getMonday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setTuesdayWorkTime(workTime.getDayOfWeek().getTuesday().isPresent() && workTime.getDayOfWeek().getTuesday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getTuesday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setWednesdayWorkTime(workTime.getDayOfWeek().getWednesday().isPresent() && workTime.getDayOfWeek().getWednesday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getWednesday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setThursdayWorkTime(workTime.getDayOfWeek().getThursday().isPresent() && workTime.getDayOfWeek().getThursday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getThursday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setFridayWorkTime(workTime.getDayOfWeek().getFriday().isPresent() && workTime.getDayOfWeek().getFriday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getFriday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setSaturdayWorkTime(workTime.getDayOfWeek().getSaturday().isPresent() && workTime.getDayOfWeek().getSaturday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getSaturday().get().getWorkTimeCode().get().v() : null);
				kshmtWorkcondWorkInfo.setSundayWorkTime(workTime.getDayOfWeek().getSunday().isPresent() && workTime.getDayOfWeek().getSunday().get().getWorkTimeCode().isPresent() ? workTime.getDayOfWeek().getSunday().get().getWorkTimeCode().get().v() : null);
			}
		}
		
		return new KshmtWorkcondHistItem(
				domain.getHistoryId(), 
				domain.getEmployeeId(),
				domain.getHourlyPaymentAtr().value, 
				domain.getScheduleManagementAtr().value, 
				domain.getAutoStampSetAtr().value, 
				domain.getAutoIntervalSetAtr().value,
				domain.getVacationAddedTimeAtr().value,
				domain.getContractTime().v(),
				domain.getLaborSystem().value,
				hdAddTimeOneDay, hdAddTimeMorning, hdAddTimeAfternoon,
				domain.getTimeApply().isPresent()?domain.getTimeApply().get().v():null,
				domain.getMonthlyPattern().isPresent()?domain.getMonthlyPattern().get().v():null,
				domain.getScheduleMethod().isPresent()?KshmtWorkcondScheMeth.toEntity(domain.getScheduleMethod().get(), domain.getEmployeeId(), domain.getHistoryId()):null,
				kshmtWorkcondWorkInfo, 
				listKshmtWorkcondWorkTs);
	}

	public KshmtWorkcondHistItem(String historyId, String sid, Integer hourlyPayAtr,
			int scheManagementAtr, int autoStampSetAtr, int autoIntervalSetAtr, int vacationAddTimeAtr,
			int contractTime, int laborSys, Integer hdAddTimeOneDay, Integer hdAddTimeMorning,
			Integer hdAddTimeAfternoon, String timeApply, String monthlyPattern,
			KshmtWorkcondScheMeth kshmtScheduleMethod, 
			KshmtWorkcondWorkInfo kshmtWorkcondWorkInfo,
			List<KshmtWorkcondWorkTs> listKshmtWorkcondWorkTs) {
		super();
		this.historyId = historyId;
		this.sid = sid;
		this.hourlyPayAtr = hourlyPayAtr;
		this.scheManagementAtr = scheManagementAtr;
		this.autoStampSetAtr = autoStampSetAtr;
		this.autoIntervalSetAtr = autoIntervalSetAtr;
		this.vacationAddTimeAtr = vacationAddTimeAtr;
		this.contractTime = contractTime;
		this.laborSys = laborSys;
		this.hdAddTimeOneDay = hdAddTimeOneDay;
		this.hdAddTimeMorning = hdAddTimeMorning;
		this.hdAddTimeAfternoon = hdAddTimeAfternoon;
		this.timeApply = timeApply;
		this.monthlyPattern = monthlyPattern;
		this.kshmtScheduleMethod = kshmtScheduleMethod;
		this.kshmtWorkcondWorkInfo = kshmtWorkcondWorkInfo;
		this.listKshmtWorkcondWorkTs = listKshmtWorkcondWorkTs;
	}

}
