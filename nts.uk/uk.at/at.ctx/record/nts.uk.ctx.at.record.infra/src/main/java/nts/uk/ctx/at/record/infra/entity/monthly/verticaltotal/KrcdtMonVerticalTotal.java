package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.WorkClockOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays.PayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.SpecificDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AttendanceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayWorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.PredeterminedDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.RecruitmentDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.SpcVacationDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TemporaryWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TwoTimesWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkDaysDetailOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.attdleavegatetime.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutForChildCare;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.holidaytime.HolidayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.Late;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.LeaveEarly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workclock.KrcdtMonWorkClock;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrAbsnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpecDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpvcDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonLeave;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrBnspyTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrDivgTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrGoout;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrPremTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonMedicalTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の縦計
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_VERTICAL_TOTAL")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonVerticalTotal extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 勤務日数 */
	@Column(name = "WORK_DAYS")
	public double workDays;
	/** 勤務回数 */
	@Column(name = "WORK_TIMES")
	public int workTimes;
	/** 二回勤務回数 */
	@Column(name = "TWOTIMES_WORK_TIMES")
	public int twoTimesWorkTimes;
	/** 臨時勤務回数 */
	@Column(name = "TEMPORARY_WORK_TIMES")
	public int temporaryWorkTimes;
	/** 所定日数 */
	@Column(name = "PREDET_DAYS")
	public double predetermineDays;
	/** 休日日数 */
	@Column(name = "HOLIDAY_DAYS")
	public double holidayDays;
	/** 出勤日数 */
	@Column(name = "ATTENDANCE_DAYS")
	public double attendanceDays;
	/** 休出日数 */
	@Column(name = "HOLIDAY_WORK_DAYS")
	public double holidayWorkDays;
	/** 欠勤合計日数 */
	@Column(name = "TOTAL_ABSENCE_DAYS")
	public double totalAbsenceDays;
	/** 欠勤合計時間 */
	@Column(name = "TOTAL_ABSENCE_TIME")
	public int totalAbsenceTime;
	/** 振出日数 */
	@Column(name = "RECRUIT_DAYS")
	public double recruitDays;
	/** 特別休暇合計日数 */
	@Column(name = "TOTAL_SPCVACT_DAYS")
	public double totalSpecialVacationDays;
	/** 特別休暇合計時間 */
	@Column(name = "TOTAL_SPCVACT_TIME")
	public int totalSpecialVacationTime;
	/** 給与出勤日数 */
	@Column(name = "PAY_ATTENDANCE_DAYS")
	public double payAttendanceDays;
	/** 給与欠勤日数 */
	@Column(name = "PAY_ABSENCE_DAYS")
	public double payAbsenceDays;
	
	/** 育児外出回数 */
	@Column(name = "CLDCAR_GOOUT_TIMES")
	public int childcareGoOutTimes;
	/** 育児外出時間 */
	@Column(name = "CLDCAR_GOOUT_TIME")
	public int childcareGoOutTime;
	/** 介護外出回数 */
	@Column(name = "CARE_GOOUT_TIMES")
	public int careGoOutTimes;
	/** 介護外出時間 */
	@Column(name = "CARE_GOOUT_TIME")
	public int careGoOutTime;
	/** 割増深夜時間 */
	@Column(name = "PREM_MIDNIGHT_TIME")
	public int premiumMidnightTime;
	/** 割増法定内時間外時間 */
	@Column(name = "PREM_LGL_OUTWRK_TIME")
	public int premiumLegalOutsideWorkTime;
	/** 割増法定外時間外時間 */
	@Column(name = "PREM_ILG_OUTWRK_TIME")
	public int premiumIllegalOutsideWorkTime;
	/** 割増法定内休出時間 */
	@Column(name = "PREM_LGL_HDWK_TIME")
	public int premiumLegalHolidayWorkTime;
	/** 割増法定外休出時間 */
	@Column(name = "PREM_ILG_HDWK_TIME")
	public int premiumIllegalHolidayWorkTime;
	/** 休憩時間 */
	@Column(name = "BREAK_TIME")
	public int breakTime;
	/** 法定内休日時間 */
	@Column(name = "LEGAL_HOLIDAY_TIME")
	public int legalHolidayTime;
	/** 法定外休日時間 */
	@Column(name = "ILLEGAL_HOLIDAY_TIME")
	public int illegalHolidayTime;
	/** 法定外祝日休日時間 */
	@Column(name = "ILLEGAL_SPCHLD_TIME")
	public int illegalSpecialHolidayTime;
	/** 残業深夜時間 */
	@Column(name = "OVWK_MDNT_TIME")
	public int overWorkMidnightTime;
	/** 計算残業深夜時間 */
	@Column(name = "CALC_OVWK_MDNT_TIME")
	public int calcOverWorkMidnightTime;
	/** 法定内深夜時間 */
	@Column(name = "LGL_MDNT_TIME")
	public int legalMidnightTime;
	/** 計算法定内深夜時間 */
	@Column(name = "CALC_LGL_MDNT_TIME")
	public int calcLegalMidnightTime;
	/** 法定外深夜時間 */
	@Column(name = "ILG_MDNT_TIME")
	public int illegalMidnightTime;
	/** 計算法定外深夜時間 */
	@Column(name = "CALC_ILG_MDNT_TIME")
	public int calcIllegalMidnightTime;
	/** 法定外事前深夜時間 */
	@Column(name = "ILG_BFR_MDNT_TIME")
	public int illegalBeforeMidnightTime;
	/** 法定内休出深夜時間 */
	@Column(name = "LGL_HDWK_MDNT_TIME")
	public int legalHolidayWorkMidnightTime;
	/** 計算法定内休出深夜時間 */
	@Column(name = "CALC_LGL_HDWK_MDNT_TIME")
	public int calcLegalHolidayWorkMidnightTime;
	/** 法定外休出深夜時間 */
	@Column(name = "ILG_HDWK_MDNT_TIME")
	public int illegalHolidayWorkMidnightTime;
	/** 計算法定外休出深夜時間 */
	@Column(name = "CALC_ILG_HDWK_MDNT_TIME")
	public int calcIllegalHolidayWorkMidnightTime;
	/** 祝日休出深夜時間 */
	@Column(name = "SPHD_HDWK_MDNT_TIME")
	public int specialHolidayWorkMidnightTime;
	/** 計算祝日休出深夜時間 */
	@Column(name = "CALC_SPHD_HDWK_MDNT_TIME")
	public int calcSpecialHolidayWorkMidnightTime;
	/** 遅刻回数 */
	@Column(name = "LATE_TIMES")
	public int lateTimes;
	/** 遅刻時間 */
	@Column(name = "LATE_TIME")
	public int lateTime;
	/** 計算遅刻時間 */
	@Column(name = "CALC_LATE_TIME")
	public int calcLateTime;
	/** 早退回数 */
	@Column(name = "LEAVEEARLY_TIMES")
	public int leaveEarlyTimes;
	/** 早退時間 */
	@Column(name = "LEAVEEARLY_TIME")
	public int leaveEarlyTime;
	/** 計算早退時間 */
	@Column(name = "CALC_LEAVEEARLY_TIME")
	public int calcLeaveEarlyTime;
	/** 入退門出勤前時間 */
	@Column(name = "ALGT_BFR_ATND_TIME")
	public int attendanceLeaveGateBeforeAttendanceTime;
	/** 入退門退勤後時間 */
	@Column(name = "ALGT_AFT_LVWK_TIME")
	public int attendanceLeaveGateAfterLeaveWorkTime;
	/** 入退門滞在時間 */
	@Column(name = "ALGT_STAYING_TIME")
	public int attendanceLeaveGateStayingTime;
	/** 入退門不就労時間 */
	@Column(name = "ALGT_UNEMPLOYED_TIME")
	public int attendanceLeaveGateUnemployedTime;
	/** 予実差異時間 */
	@Column(name = "BUDGET_VARIENCE_TIME")
	public int budgetVarienceTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @param krcdtMonLeave 月別実績の休業
	 * @param krcdtMonAggrAbsnDays 集計欠勤日数
	 * @param krcdtMonAggrSpecDays 集計特定日数
	 * @param krcdtMonAggrSpvcDays 集計特別休暇日数
	 * @param krcdtMonAggrBnspyTime 集計加給時間
	 * @param krcdtMonAggrGoout 集計外出
	 * @param krcdtMonAggrPremTime 集計割増時間
	 * @param krcdtMonAggrDivgTime 集計乖離時間
	 * @param krcdtMonMedicalTime 月別実績の医療時間
	 * @param krcdtMonWorkClock 月別実績の勤務時刻
	 * @return 月別実績の縦計
	 */
	public VerticalTotalOfMonthly toDomain(
			KrcdtMonLeave krcdtMonLeave,
			List<KrcdtMonAggrAbsnDays> krcdtMonAggrAbsnDays,
			List<KrcdtMonAggrSpecDays> krcdtMonAggrSpecDays,
			List<KrcdtMonAggrSpvcDays> krcdtMonAggrSpvcDays,
			List<KrcdtMonAggrBnspyTime> krcdtMonAggrBnspyTime,
			List<KrcdtMonAggrGoout> krcdtMonAggrGoout,
			List<KrcdtMonAggrPremTime> krcdtMonAggrPremTime,
			List<KrcdtMonAggrDivgTime> krcdtMonAggrDivgTime,
			List<KrcdtMonMedicalTime> krcdtMonMedicalTime,
			KrcdtMonWorkClock krcdtMonWorkClock){
		
		if (krcdtMonAggrAbsnDays == null) krcdtMonAggrAbsnDays = new ArrayList<>();
		if (krcdtMonAggrSpecDays == null) krcdtMonAggrSpecDays = new ArrayList<>();
		if (krcdtMonAggrSpvcDays == null) krcdtMonAggrSpvcDays = new ArrayList<>();
		if (krcdtMonAggrBnspyTime == null) krcdtMonAggrBnspyTime = new ArrayList<>();
		if (krcdtMonAggrGoout == null) krcdtMonAggrGoout = new ArrayList<>();
		if (krcdtMonAggrPremTime == null) krcdtMonAggrPremTime = new ArrayList<>();
		if (krcdtMonAggrDivgTime == null) krcdtMonAggrDivgTime = new ArrayList<>();
		if (krcdtMonMedicalTime == null) krcdtMonMedicalTime = new ArrayList<>();
		
		// 育児外出
		List<GoOutForChildCare> goOutForChildCares = new ArrayList<>();
		if (this.childcareGoOutTimes != 0 || this.childcareGoOutTime != 0){
			goOutForChildCares.add(GoOutForChildCare.of(
					ChildCareAtr.CHILD_CARE,
					new AttendanceTimesMonth(this.childcareGoOutTimes),
					new AttendanceTimeMonth(this.childcareGoOutTime)));
		}
		if (this.careGoOutTimes != 0 || this.careGoOutTime != 0){
			goOutForChildCares.add(GoOutForChildCare.of(
					ChildCareAtr.CARE,
					new AttendanceTimesMonth(this.careGoOutTimes),
					new AttendanceTimeMonth(this.careGoOutTime)));
		}
		
		// 月別実績の休業
		LeaveOfMonthly leave = new LeaveOfMonthly();
		if (krcdtMonLeave != null) leave = krcdtMonLeave.toDomain();
		
		// 月別実績の勤務日数
		val workDays = WorkDaysOfMonthly.of(
				AttendanceDaysOfMonthly.of(new AttendanceDaysMonth(this.attendanceDays)),
				AbsenceDaysOfMonthly.of(
						new AttendanceDaysMonth(this.totalAbsenceDays),
						new AttendanceTimeMonth(this.totalAbsenceTime),
						krcdtMonAggrAbsnDays.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
				PredeterminedDaysOfMonthly.of(
						new AttendanceDaysMonth(this.predetermineDays)),
				WorkDaysDetailOfMonthly.of(new AttendanceDaysMonth(this.workDays)),
				HolidayDaysOfMonthly.of(new AttendanceDaysMonth(this.holidayDays)),
				SpecificDaysOfMonthly.of(
						krcdtMonAggrSpecDays.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
				HolidayWorkDaysOfMonthly.of(new AttendanceDaysMonth(this.holidayWorkDays)),
				PayDaysOfMonthly.of(
						new AttendanceDaysMonth(this.payAttendanceDays),
						new AttendanceDaysMonth(this.payAbsenceDays)),
				WorkTimesOfMonthly.of(new AttendanceTimesMonth(this.workTimes)),
				TwoTimesWorkTimesOfMonthly.of(new AttendanceTimesMonth(this.twoTimesWorkTimes)),
				TemporaryWorkTimesOfMonthly.of(new AttendanceTimesMonth(this.temporaryWorkTimes)),
				leave,
				RecruitmentDaysOfMonthly.of(new AttendanceDaysMonth(this.recruitDays)),
				SpcVacationDaysOfMonthly.of(
						new AttendanceDaysMonth(this.totalSpecialVacationDays),
						new AttendanceTimeMonth(this.totalSpecialVacationTime),
						krcdtMonAggrSpvcDays.stream().map(c -> c.toDomain()).collect(Collectors.toList())));
		
		// 月別実績の勤務時間
		val workTime = nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.WorkTimeOfMonthlyVT.of(
				BonusPayTimeOfMonthly.of(
						krcdtMonAggrBnspyTime.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
				GoOutOfMonthly.of(
						krcdtMonAggrGoout.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
						goOutForChildCares),
				PremiumTimeOfMonthly.of(
						krcdtMonAggrPremTime.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
						new AttendanceTimeMonth(this.premiumMidnightTime),
						new AttendanceTimeMonth(this.premiumLegalOutsideWorkTime),
						new AttendanceTimeMonth(this.premiumLegalHolidayWorkTime),
						new AttendanceTimeMonth(this.premiumIllegalOutsideWorkTime),
						new AttendanceTimeMonth(this.premiumIllegalHolidayWorkTime)),
				BreakTimeOfMonthly.of(new AttendanceTimeMonth(this.breakTime)),
				HolidayTimeOfMonthly.of(
						new AttendanceTimeMonth(this.legalHolidayTime),
						new AttendanceTimeMonth(this.illegalHolidayTime),
						new AttendanceTimeMonth(this.illegalSpecialHolidayTime)),
				MidnightTimeOfMonthly.of(
						new TimeMonthWithCalculation(
								new AttendanceTimeMonth(this.overWorkMidnightTime),
								new AttendanceTimeMonth(this.calcOverWorkMidnightTime)),
						new TimeMonthWithCalculation(
								new AttendanceTimeMonth(this.legalMidnightTime),
								new AttendanceTimeMonth(this.calcLegalMidnightTime)),
						IllegalMidnightTime.of(
								new TimeMonthWithCalculation(
										new AttendanceTimeMonth(this.illegalMidnightTime),
										new AttendanceTimeMonth(this.calcIllegalMidnightTime)),
								new AttendanceTimeMonth(this.illegalBeforeMidnightTime)),
						new TimeMonthWithCalculation(
								new AttendanceTimeMonth(this.legalHolidayWorkMidnightTime),
								new AttendanceTimeMonth(this.calcLegalHolidayWorkMidnightTime)),
						new TimeMonthWithCalculation(
								new AttendanceTimeMonth(this.illegalHolidayWorkMidnightTime),
								new AttendanceTimeMonth(this.calcIllegalHolidayWorkMidnightTime)),
						new TimeMonthWithCalculation(
								new AttendanceTimeMonth(this.specialHolidayWorkMidnightTime),
								new AttendanceTimeMonth(this.calcSpecialHolidayWorkMidnightTime))),
				LateLeaveEarlyOfMonthly.of(
						LeaveEarly.of(
								new AttendanceTimesMonth(this.leaveEarlyTimes),
								new TimeMonthWithCalculation(
										new AttendanceTimeMonth(this.leaveEarlyTime),
										new AttendanceTimeMonth(this.calcLeaveEarlyTime))),
						Late.of(
								new AttendanceTimesMonth(this.lateTimes),
								new TimeMonthWithCalculation(
										new AttendanceTimeMonth(this.lateTime),
										new AttendanceTimeMonth(this.calcLateTime)))),
				AttendanceLeaveGateTimeOfMonthly.of(
						new AttendanceTimeMonth(this.attendanceLeaveGateBeforeAttendanceTime),
						new AttendanceTimeMonth(this.attendanceLeaveGateAfterLeaveWorkTime),
						new AttendanceTimeMonth(this.attendanceLeaveGateStayingTime),
						new AttendanceTimeMonth(this.attendanceLeaveGateUnemployedTime)),
				BudgetTimeVarienceOfMonthly.of(new AttendanceTimeMonth(this.budgetVarienceTime)),
				DivergenceTimeOfMonthly.of(
						krcdtMonAggrDivgTime.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
				krcdtMonMedicalTime.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
		
		// 月別実績の勤務時刻
		WorkClockOfMonthly workClock = new WorkClockOfMonthly();
		if (krcdtMonWorkClock != null) workClock = krcdtMonWorkClock.toDomain();
		
		return VerticalTotalOfMonthly.of(workDays, workTime, workClock);
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の縦計
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, VerticalTotalOfMonthly domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の縦計
	 */
	public void fromDomainForUpdate(VerticalTotalOfMonthly domain){
		
		val vtWorkDays = domain.getWorkDays();
		val vtWorkTime = domain.getWorkTime();
		
		this.workDays = vtWorkDays.getWorkDays().getDays().v();
		this.workTimes = vtWorkDays.getWorkTimes().getTimes().v();
		this.twoTimesWorkTimes = vtWorkDays.getTwoTimesWorkTimes().getTimes().v();
		this.temporaryWorkTimes = vtWorkDays.getTemporaryWorkTimes().getTimes().v();
		this.predetermineDays = vtWorkDays.getPredetermineDays().getPredeterminedDays().v();
		this.holidayDays = vtWorkDays.getHolidayDays().getDays().v();
		this.attendanceDays = vtWorkDays.getAttendanceDays().getDays().v();
		this.holidayWorkDays = vtWorkDays.getHolidayWorkDays().getDays().v();
		this.totalAbsenceDays = vtWorkDays.getAbsenceDays().getTotalAbsenceDays().v();
		this.totalAbsenceTime = vtWorkDays.getAbsenceDays().getTotalAbsenceTime().v();
		this.recruitDays = vtWorkDays.getRecruitmentDays().getDays().v();
		this.totalSpecialVacationDays = vtWorkDays.getSpecialVacationDays().getTotalSpcVacationDays().v();
		this.totalSpecialVacationTime = vtWorkDays.getSpecialVacationDays().getTotalSpcVacationTime().v();
		this.payAttendanceDays = vtWorkDays.getPayDays().getPayAttendanceDays().v();
		this.payAbsenceDays = vtWorkDays.getPayDays().getPayAbsenceDays().v();
		
		this.childcareGoOutTimes = 0;
		this.childcareGoOutTime = 0;
		this.careGoOutTimes = 0;
		this.careGoOutTime = 0;
		val goOutForChildCares = vtWorkTime.getGoOut().getGoOutForChildCares();
		if (goOutForChildCares.containsKey(ChildCareAtr.CHILD_CARE)){
			val goOutForChildCare = goOutForChildCares.get(ChildCareAtr.CHILD_CARE);
			this.childcareGoOutTimes = goOutForChildCare.getTimes().v();
			this.childcareGoOutTime = goOutForChildCare.getTime().v();
		}
		if (goOutForChildCares.containsKey(ChildCareAtr.CARE)){
			val goOutForCare = goOutForChildCares.get(ChildCareAtr.CARE);
			this.careGoOutTimes = goOutForCare.getTimes().v();
			this.careGoOutTime = goOutForCare.getTime().v();
		}
		
		this.premiumMidnightTime = vtWorkTime.getPremiumTime().getMidnightTime().v();
		this.premiumLegalOutsideWorkTime = vtWorkTime.getPremiumTime().getLegalOutsideWorkTime().v();
		this.premiumIllegalOutsideWorkTime = vtWorkTime.getPremiumTime().getIllegalOutsideWorkTime().v();
		this.premiumLegalHolidayWorkTime = vtWorkTime.getPremiumTime().getLegalHolidayWorkTime().v();
		this.premiumIllegalHolidayWorkTime = vtWorkTime.getPremiumTime().getIllegalHolidayWorkTime().v();
		this.breakTime = vtWorkTime.getBreakTime().getBreakTime().v();
		this.legalHolidayTime = vtWorkTime.getHolidayTime().getLegalHolidayTime().v();
		this.illegalHolidayTime = vtWorkTime.getHolidayTime().getIllegalHolidayTime().v();
		this.illegalSpecialHolidayTime = vtWorkTime.getHolidayTime().getIllegalSpecialHolidayTime().v();
		this.overWorkMidnightTime = vtWorkTime.getMidnightTime().getOverWorkMidnightTime().getTime().v();
		this.calcOverWorkMidnightTime = vtWorkTime.getMidnightTime().getOverWorkMidnightTime().getCalcTime().v();
		this.legalMidnightTime = vtWorkTime.getMidnightTime().getLegalMidnightTime().getTime().v();
		this.calcLegalMidnightTime = vtWorkTime.getMidnightTime().getLegalMidnightTime().getCalcTime().v();
		this.illegalMidnightTime = vtWorkTime.getMidnightTime().getIllegalMidnightTime().getTime().getTime().v();
		this.calcIllegalMidnightTime = vtWorkTime.getMidnightTime().getIllegalMidnightTime().getTime().getCalcTime().v();
		this.illegalBeforeMidnightTime = vtWorkTime.getMidnightTime().getIllegalMidnightTime().getBeforeTime().v();
		this.legalHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getLegalHolidayWorkMidnightTime().getTime().v();
		this.calcLegalHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getLegalHolidayWorkMidnightTime().getCalcTime().v();
		this.illegalHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getIllegalHolidayWorkMidnightTime().getTime().v();
		this.calcIllegalHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getIllegalHolidayWorkMidnightTime().getCalcTime().v();
		this.specialHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getSpecialHolidayWorkMidnightTime().getTime().v();
		this.calcSpecialHolidayWorkMidnightTime = vtWorkTime.getMidnightTime().getSpecialHolidayWorkMidnightTime().getCalcTime().v();
		this.lateTimes = vtWorkTime.getLateLeaveEarly().getLate().getTimes().v();
		this.lateTime = vtWorkTime.getLateLeaveEarly().getLate().getTime().getTime().v();
		this.calcLateTime = vtWorkTime.getLateLeaveEarly().getLate().getTime().getCalcTime().v();
		this.leaveEarlyTimes = vtWorkTime.getLateLeaveEarly().getLeaveEarly().getTimes().v();
		this.leaveEarlyTime = vtWorkTime.getLateLeaveEarly().getLeaveEarly().getTime().getTime().v();
		this.calcLeaveEarlyTime = vtWorkTime.getLateLeaveEarly().getLeaveEarly().getTime().getCalcTime().v();
		this.attendanceLeaveGateBeforeAttendanceTime = vtWorkTime.getAttendanceLeaveGateTime().getTimeBeforeAttendance().v();
		this.attendanceLeaveGateAfterLeaveWorkTime = vtWorkTime.getAttendanceLeaveGateTime().getTimeAfterLeaveWork().v();
		this.attendanceLeaveGateStayingTime = vtWorkTime.getAttendanceLeaveGateTime().getStayingTime().v();
		this.attendanceLeaveGateUnemployedTime = vtWorkTime.getAttendanceLeaveGateTime().getUnemployedTime().v();
		this.budgetVarienceTime = vtWorkTime.getBudgetTimeVarience().getTime().v();
	}
}
