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
import nts.uk.ctx.at.shared.dom.workingcondition.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWorkingCondItem.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKING_COND_ITEM")
public class KshmtWorkingCondItem extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The history id. */
	@Id
	@Column(name = "HIST_ID")
	private String historyId;

	/** The sid. */
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
	@PrimaryKeyJoinColumns({
			@PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID") })
	private KshmtWorkingCond kshmtWorkingCond;

	/** The kshmt working cond items. */
	@JoinColumns({
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private KshmtScheduleMethod kshmtScheduleMethod;

	/** The kshmt per work cats. */
	@JoinColumns({
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtPerWorkCat> kshmtPerWorkCats;

	/** The kshmt personal day of weeks. */
	@JoinColumns({
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KshmtPersonalDayOfWeek> kshmtPersonalDayOfWeeks;

	/**
	 * Instantiates a new kshmt working cond item.
	 */
	public KshmtWorkingCondItem() {
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
		if (!(object instanceof KshmtWorkingCondItem)) {
			return false;
		}
		KshmtWorkingCondItem other = (KshmtWorkingCondItem) object;
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

	public KshmtWorkingCondItem(int exclusVer, String historyId, String sid, Integer hourlyPayAtr,
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
		
		
		Optional<SingleDaySchedule> monday = Optional.empty();
		Optional<SingleDaySchedule> tuesday = Optional.empty();
		Optional<SingleDaySchedule> wednesday = Optional.empty();
		Optional<SingleDaySchedule> thursday = Optional.empty();
		Optional<SingleDaySchedule> friday = Optional.empty();
		Optional<SingleDaySchedule> saturday = Optional.empty();
		Optional<SingleDaySchedule> sunday = Optional.empty();
		for(KshmtPersonalDayOfWeek entity : this.kshmtPersonalDayOfWeeks) {
			switch(entity.kshmtPersonalDayOfWeekPK.getPerWorkDayOffAtr()) {
			case 0:
				monday = Optional.of(entity.toDomain());
				break;
			case 1:
				tuesday = Optional.of(entity.toDomain());
				break;
			case 2:
				wednesday = Optional.of(entity.toDomain());
				break;
			case 3:
				thursday = Optional.of(entity.toDomain());
				break;
			case 4:
				friday = Optional.of(entity.toDomain());
				break;
			case 5:
				saturday = Optional.of(entity.toDomain());
				break;
			case 6:
				sunday = Optional.of(entity.toDomain());
				break;
			default:
				break;
			}
		}
		//set 曜日別勤務
		PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
		
		
		SingleDaySchedule weekdayTime = null;
		SingleDaySchedule holidayWork = null;
		SingleDaySchedule holidayTime = null;
		Optional<SingleDaySchedule> inLawBreakTime = Optional.empty();
		Optional<SingleDaySchedule> outsideLawBreakTime = Optional.empty();
		Optional<SingleDaySchedule> holidayAttendanceTime = Optional.empty();
		Optional<SingleDaySchedule> publicHolidayWork = Optional.empty();
		for(KshmtPerWorkCat entity:this.kshmtPerWorkCats) {
			switch(entity.kshmtPerWorkCatPK.getPerWorkCatAtr()) {
			case 0: //平日時
				weekdayTime = entity.toDomain();
				break;
			case 1://休日出勤時
				holidayWork = entity.toDomain();
				break;
			case 2: //法内休出時
				inLawBreakTime = Optional.of(entity.toDomain());
				break;
			case 3://法外休出時
				outsideLawBreakTime = Optional.of(entity.toDomain());
				break;
			case 4://祝日出勤時
				holidayAttendanceTime = Optional.of(entity.toDomain());
				break;
			case 5://公休出勤時
				publicHolidayWork = Optional.of(entity.toDomain());
				break;
			case 6://休日時
				holidayTime = entity.toDomain();
				break;
			default:
				break;
			}
		}
		//set 区分別勤務
		PersonalWorkCategory workCategory = new PersonalWorkCategory(weekdayTime, holidayWork, holidayTime, inLawBreakTime, outsideLawBreakTime, holidayAttendanceTime, publicHolidayWork);
		
		//
		ScheduleMethod scheduleMethod = (this.kshmtScheduleMethod ==null?null:this.kshmtScheduleMethod.toDomain());
		Integer hourlyPaymentAtr = this.hourlyPayAtr ==null?null:this.hourlyPayAtr;
		BonusPaySettingCode timeApply = this.timeApply==null?null:new BonusPaySettingCode(this.timeApply);
		MonthlyPatternCode monthlyPattern = this.monthlyPattern==null?null:new MonthlyPatternCode(this.monthlyPattern);
		return new WorkingConditionItem(
				this.historyId, 
				ManageAtr.valueOf(this.scheManagementAtr),
				workDayOfWeek,
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
	
	public static KshmtWorkingCondItem toEntity(WorkingConditionItem domain) {
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
		
		//
		List<KshmtPersonalDayOfWeek> kshmtPersonalDayOfWeeks = new ArrayList<>();
		//monday
		if (domain.getWorkDayOfWeek().getMonday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getMonday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 0)); // 月曜日
		}
		//tuesday
		if (domain.getWorkDayOfWeek().getTuesday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getTuesday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 1)); // 火曜日
		}
		//wednesday
		if (domain.getWorkDayOfWeek().getWednesday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getWednesday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 2)); // 水曜日
		}
		//thursday
		if (domain.getWorkDayOfWeek().getThursday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getThursday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 3)); // 木曜日
		}
		//friday
		if (domain.getWorkDayOfWeek().getFriday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getFriday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 4)); // 金曜日
		}
		//saturday
		if (domain.getWorkDayOfWeek().getSaturday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getSaturday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 5)); // 土曜日
		}
		//sunday
		if (domain.getWorkDayOfWeek().getSunday().isPresent()) {
			kshmtPersonalDayOfWeeks.add(KshmtPersonalDayOfWeek.toEntity(domain.getWorkDayOfWeek().getSunday().get(),
					domain.getHistoryId(), domain.getEmployeeId(), 6)); // 月曜日
		}
		
		List<KshmtPerWorkCat> kshmtPerWorkCats = new ArrayList<>();
		//weekdayTime
		kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getWeekdayTime(), domain.getHistoryId(), domain.getEmployeeId(), 0));//平日時
		//holidayWork
		kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getHolidayWork(), domain.getHistoryId(), domain.getEmployeeId(), 1));//休日出勤時
		//holidayTime
		kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getHolidayTime(), domain.getHistoryId(), domain.getEmployeeId(), 6));//平日時
		//inLawBreakTime
		if(domain.getWorkCategory().getInLawBreakTime().isPresent()) {
			kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getInLawBreakTime().get(), domain.getHistoryId(), domain.getEmployeeId(), 2));//法内休出時
		}
		//outsideLawBreakTime
		if(domain.getWorkCategory().getOutsideLawBreakTime().isPresent()) {
			kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getOutsideLawBreakTime().get(), domain.getHistoryId(), domain.getEmployeeId(), 3));//法外休出時
		}
		//holidayAttendanceTime
		if(domain.getWorkCategory().getHolidayAttendanceTime().isPresent()) {
			kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getHolidayAttendanceTime().get(), domain.getHistoryId(), domain.getEmployeeId(), 4));//祝日出勤時
		}
		//publicHolidayWork
		if(domain.getWorkCategory().getPublicHolidayWork().isPresent()) {
			kshmtPerWorkCats.add(KshmtPerWorkCat.toEntity(domain.getWorkCategory().getPublicHolidayWork().get(), domain.getHistoryId(), domain.getEmployeeId(), 2));//公休出勤時
		}
		
		return new KshmtWorkingCondItem(
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
				domain.getScheduleMethod().isPresent()?KshmtScheduleMethod.toEntity(domain.getScheduleMethod().get(), domain.getEmployeeId(), domain.getHistoryId()):null,
				kshmtPerWorkCats, kshmtPersonalDayOfWeeks);
	}

	public KshmtWorkingCondItem(String historyId, String sid, Integer hourlyPayAtr,
			int scheManagementAtr, int autoStampSetAtr, int autoIntervalSetAtr, int vacationAddTimeAtr,
			int contractTime, int laborSys, Integer hdAddTimeOneDay, Integer hdAddTimeMorning,
			Integer hdAddTimeAfternoon, String timeApply, String monthlyPattern,
			KshmtScheduleMethod kshmtScheduleMethod, List<KshmtPerWorkCat> kshmtPerWorkCats,
			List<KshmtPersonalDayOfWeek> kshmtPersonalDayOfWeeks) {
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
		this.kshmtPerWorkCats = kshmtPerWorkCats;
		this.kshmtPersonalDayOfWeeks = kshmtPersonalDayOfWeeks;
	}

}
