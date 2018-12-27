package nts.uk.ctx.at.record.infra.entity.monthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *  エンティティ：月別実績の勤怠時間(テーブル結合用)
 * @author ken_takasu
 */
@Entity
@Table(name = "KRCDT_MON_TIME")
@NoArgsConstructor
public class KrcdtMonTime extends UkJpaEntity implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonTimePK PK;
	
	/** 開始年月日 */
	@Column(name = "START_YMD")
	public GeneralDate startYmd;

	/** 終了年月日 */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 集計日数 */
	@Column(name = "AGGREGATE_DAYS")
	public Double aggregateDays;
	
	/** 法定労働時間 */
	@Column(name = "STAT_WORKING_TIME")
	public int statutoryWorkingTime;
	
	/** 総労働時間 */
	@Column(name = "TOTAL_WORKING_TIME")
	public int totalWorkingTime;
	
	/*----------------------月別実績の通常変形時間------------------------------*/
	
	/** 週割増合計時間 */
	@Column(name = "WEEK_TOTAL_PREM_TIME")
	public int weeklyTotalPremiumTime;
	
	/** 月割増合計時間 */
	@Column(name = "MON_TOTAL_PREM_TIME")
	public int monthlyTotalPremiumTime;
	
	/** 複数月変形途中時間 */
	@Column(name = "MULTI_MON_IRGMDL_TIME")
	public int multiMonthIrregularMiddleTime;
	
	/** 変形期間繰越時間 */
	@Column(name = "IRGPERIOD_CRYFWD_TIME")
	public int irregularPeriodCarryforwardTime;
	
	/** 変形労働不足時間 */
	@Column(name = "IRG_SHORTAGE_TIME")
	public int irregularWorkingShortageTime;
	
	/** 変形法定内残業時間 */
	@Column(name = "IRG_LEGAL_OVER_TIME")
	public int irregularLegalOverTime;
	
	/** 計算変形法定内残業時間 */
	@Column(name = "CALC_IRG_LGL_OVER_TIME")
	public int calcIrregularLegalOverTime;
	
	/*----------------------月別実績の通常変形時間------------------------------*/
	
	/*----------------------月別実績のフレックス時間------------------------------*/
	
	/** フレックス時間 */
	@Column(name = "FLEX_TIME")
	public int flexTime; 
	
	/** 計算フレックス時間 */
	@Column(name = "CALC_FLEX_TIME")
	public int calcFlexTime; 
	
	/** 事前フレックス時間 */
	@Column(name = "BEFORE_FLEX_TIME")
	public int beforeFlexTime; 
	
	/** 法定内フレックス時間 */
	@Column(name = "LEGAL_FLEX_TIME")
	public int legalFlexTime; 
	
	/** 法定外フレックス時間 */
	@Column(name = "ILLEGAL_FLEX_TIME")
	public int illegalFlexTime; 
	
	/** フレックス超過時間 */
	@Column(name = "FLEX_EXCESS_TIME")
	public int flexExcessTime; 
	
	/** フレックス不足時間 */
	@Column(name = "FLEX_SHORTAGE_TIME")
	public int flexShortageTime; 
	
	/** フレックス繰越時間 */
	@Column(name = "FLEX_CRYFWD_TIME")
	public int flexCarryforwardTime; 
	
	/** フレックス繰越勤務時間 */
	@Column(name = "FLEX_CRYFWD_WORK_TIME")
	public int flexCarryforwardWorkTime; 
	
	/** フレックス繰越不足時間 */
	@Column(name = "FLEX_CRYFWD_SHT_TIME")
	public int flexCarryforwardShortageTime; 
	
	/** 超過フレ区分 */
	@Column(name = "EXCESS_FLEX_ATR")
	public int excessFlexAtr; 
	
	/** 原則時間 */
	@Column(name = "PRINCIPLE_TIME")
	public int principleTime; 
	
	/** 便宜上時間 */
	@Column(name = "CONVENIENCE_TIME")
	public int forConvenienceTime; 

	/** 年休控除日数 */
	@Column(name = "ANNUAL_DEDUCT_DAYS")
	public double annualLeaveDeductDays; 
	
	/** 欠勤控除時間 */
	@Column(name = "ABSENCE_DEDUCT_TIME")
	public int absenceDeductTime; 
	
	/** 控除前のフレックス不足時間 */
	@Column(name = "SHORT_TIME_BFR_DEDUCT")
	public int shotTimeBeforeDeduct; 
	
	/*----------------------月別実績のフレックス時間------------------------------*/
	
	/*----------------------総労働時間：月別実績の休暇使用時間------------------------------*/
	
	/** 年休使用時間 */
	@Column(name = "ANLLEV_USE_TIME")
	public int annualLeaveUseTime;
	
	/** 積立年休使用時間 */
	@Column(name = "RETYEA_USE_TIME")
	public int retentionYearlyUseTime;
	
	/** 特別休暇使用時間 */
	@Column(name = "SPHD_USE_TIME")
	public int specialHolidayUseTime;
	
	/** 代休使用時間 */
	@Column(name = "CMPLEV_USE_TIME")
	public int compensatoryLeaveUseTime;
	
	/*----------------------総労働時間：月別実績の休暇使用時間------------------------------*/
	
	/*----------------------総労働時間：集計総労働時間------------------------------*/
	
	/** 就業時間 */
	@Column(name = "WORK_TIME")
	public int workTime;
	
	/** 実働就業時間 */
	@Column(name = "ACTWORK_TIME")
	public int actualWorkTime;
	
	/** 所定内割増時間 */
	@Column(name = "WITPRS_PREMIUM_TIME")
	public int withinPrescribedPremiumTime;
	
	/** 計画所定労働時間 */
	@Column(name = "SCHE_PRS_WORK_TIME")
	public int schedulePrescribedWorkingTime;
	
	/** 実績所定労働時間 */
	@Column(name = "RECD_PRS_WORK_TIME")
	public int recordPrescribedWorkingTime;
	
	/*----------------------総労働時間：集計総労働時間------------------------------*/
	
	/*----------------------総労働時間：月別実績の残業時間------------------------------*/
	
	/** 残業合計時間 */
	@Column(name = "TOTAL_OVER_TIME")
	public int totalOverTime;
	
	/** 計算残業合計時間 */
	@Column(name = "CALC_TOTAL_OVER_TIME")
	public int calcTotalOverTime;
	
	/** 事前残業時間 */
	@Column(name = "BEFORE_OVER_TIME")
	public int beforeOverTime;
	
	/** 振替残業合計時間 */
	@Column(name = "TOTAL_TRNOVR_TIME")
	public int totalTransferOverTime;
	
	/** 計算振替残業合計時間 */
	@Column(name = "CALC_TOTAL_TRNOVR_TIME")
	public int calcTotalTransferOverTime;
	
	/*----------------------総労働時間：月別実績の残業時間------------------------------*/
	
	
//	/** 総労働時間：残業時間：集計残業時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimes;
	
	
	/*----------------------総労働時間：休出・代休：月別実績の休出時間------------------------------*/
	
	/** 休出合計時間 */
	@Column(name = "TOTAL_HDWK_TIME")
	public int totalHolidayWorkTime;
	
	/** 計算休出合計時間 */
	@Column(name = "CALC_TOTAL_HDWK_TIME")
	public int calcTotalHolidayWorkTime;
	
	/** 事前休出時間 */
	@Column(name = "BEFORE_HDWK_TIME")
	public int beforeHolidayWorkTime;
	
	/** 振替合計時間 */
	@Column(name = "TOTAL_TRN_TIME")
	public int totalTransferTime;
	
	/** 計算振替合計時間 */
	@Column(name = "CALC_TOTAL_TRN_TIME")
	public int calcTotalTransferTime;
	
	/*----------------------総労働時間：休出・代休：月別実績の休出時間------------------------------*/
	
	
//	/** 総労働時間：休出・代休：集計休出時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimes;
	
	
	/*----------------------集計総拘束時間------------------------------*/
	
	/** 拘束残業時間 */
	@Column(name = "SPENT_OVER_TIME")
	public int overTimeSpentAtWork;
	
	/** 拘束深夜時間 */
	@Column(name = "SPENT_MIDNIGHT_TIME")
	public int midnightTimeSpentAtWork;
	
	/** 拘束休出時間 */
	@Column(name = "SPENT_HOLIDAY_TIME")
	public int holidayTimeSpentAtWork;
	
	/** 拘束差異時間 */
	@Column(name = "SPENT_VARIENCE_TIME")
	public int varienceTimeSpentAtWork;
	
	/** 総拘束時間 */
	@Column(name = "TOTAL_SPENT_TIME")
	public int totalTimeSpentAtWork;
	
	/*----------------------集計総拘束時間------------------------------*/
	
	
//	/** 月別実績の時間外超過 */
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public KrcdtMonExcessOutside krcdtMonExcessOutside;
//	
//	/** 時間外超過：時間外超過 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonExcoutTime> krcdtMonExcoutTime;
	
	
	/*----------------------月別実績の36協定時間------------------------------*/
	
	/** 36協定時間 */
	@Column(name = "AGREEMENT_TIME")
	public int agreementTime;
	
	/** 限度エラー時間 */
	@Column(name = "LIMIT_ERROR_TIME")
	public int limitErrorTime;
	
	/** 限度アラーム時間 */
	@Column(name = "LIMIT_ALARM_TIME")
	public int limitAlarmTime;
	
	/** 特例限度エラー時間 */
	@Column(name = "EXCEPT_LIMIT_ERR_TIME")
	public Integer exceptionLimitErrorTime;
	
	/** 特例限度アラーム時間 */
	@Column(name = "EXCEPT_LIMIT_ALM_TIME")
	public Integer exceptionLimitAlarmTime;
	
	/** 状態 */
	@Column(name = "STATUS")
	public int status;
	
	/*----------------------月別実績の36協定時間------------------------------*/
	
	/*----------------------月別実績の縦計------------------------------*/
	
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
	
	/*----------------------月別実績の縦計------------------------------*/
	
	
//	/** 縦計：勤務日数：集計欠勤日数 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrAbsnDays> krcdtMonAggrAbsnDays;
//	
//	/** 縦計：勤務日数：集計特定日数 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrSpecDays> krcdtMonAggrSpecDays;
	
	
	/*----------------------縦計：勤務日数：月別実績の休業 ------------------------------*/
	
	/** 産前休業日数 */
	@Column(name = "PRENATAL_LEAVE_DAYS")
	public double prenatalLeaveDays;
	
	/** 産後休業日数 */
	@Column(name = "POSTPARTUM_LEAVE_DAYS")
	public double postpartumLeaveDays;
	
	/** 育児休業日数 */
	@Column(name = "CHILDCARE_LEAVE_DAYS")
	public double childcareLeaveDays;
	
	/** 介護休業日数 */
	@Column(name = "CARE_LEAVE_DAYS")
	public double careLeaveDays;
	
	/** 傷病休業日数 */
	@Column(name = "INJILN_LEAVE_DAYS")
	public double injuryOrIllnessLeaveDays;
	
	/** 任意休業日数01 */
	@Column(name = "ANY_LEAVE_DAYS_01")
	public double anyLeaveDays01;
	
	/** 任意休業日数02 */
	@Column(name = "ANY_LEAVE_DAYS_02")
	public double anyLeaveDays02;
	
	/** 任意休業日数03 */
	@Column(name = "ANY_LEAVE_DAYS_03")
	public double anyLeaveDays03;
	
	/** 任意休業日数04 */
	@Column(name = "ANY_LEAVE_DAYS_04")
	public double anyLeaveDays04;
	
	/*----------------------縦計：勤務日数：月別実績の休業 ------------------------------*/
	
	
//	/** 縦計：勤務時間：集計加給時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrBnspyTime> krcdtMonAggrBnspyTime;
//	
//	/** 縦計：勤務時間：集計乖離時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrDivgTime> krcdtMonAggrDivgTime;
//	
//	/** 縦計：勤務時間：集計外出 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrGoout> krcdtMonAggrGoout;
//	
//	/** 縦計：勤務時間：集計割増時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonAggrPremTime> krcdtMonAggrPremTime;
//	
//	/** 縦計：勤務時間：月別実績の医療時間 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonMedicalTime> krcdtMonMedicalTime;
//	
//	/** 縦計：勤務時刻 */
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public KrcdtMonWorkClock krcdtMonWorkClock;
//	
//	/** 回数集計 */
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonTime", orphanRemoval = true)
//	public List<KrcdtMonTotalTimes> krcdtMonTotalTimes;
	
	

	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	
	/**
	 * ドメインに変換
	 * @return 月別実績の勤怠時間
	 */
	public AttendanceTimeOfMonthly toDomain(){
		
//		/*----------------------月別実績の通常変形時間------------------------------*/
//		
//		// 月別実績の変形労働時間
//		val irregularWorkingTime = IrregularWorkingTimeOfMonthly.of(
//				new AttendanceTimeMonthWithMinus(this.multiMonthIrregularMiddleTime),
//				new AttendanceTimeMonthWithMinus(this.irregularPeriodCarryforwardTime),
//				new AttendanceTimeMonth(this.irregularWorkingShortageTime),
//				new TimeMonthWithCalculation(
//						new AttendanceTimeMonth(this.irregularLegalOverTime),
//						new AttendanceTimeMonth(this.calcIrregularLegalOverTime)));
//		
//		RegularAndIrregularTimeOfMonthly regAndIrgTime = RegularAndIrregularTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.monthlyTotalPremiumTime),
//				new AttendanceTimeMonth(this.weeklyTotalPremiumTime),
//				irregularWorkingTime);
//		
//		/*----------------------月別実績の通常変形時間------------------------------*/
//		
//		/*----------------------月別実績のフレックス時間------------------------------*/
//		
//		// 月別実績のフレックス時間
//		FlexTimeOfMonthly flexTime = FlexTimeOfMonthly.of(
//				FlexTime.of(
//						new TimeMonthWithCalculationAndMinus(
//								new AttendanceTimeMonthWithMinus(this.flexTime),
//								new AttendanceTimeMonthWithMinus(this.calcFlexTime)),
//						new AttendanceTimeMonth(this.beforeFlexTime),
//						new AttendanceTimeMonthWithMinus(this.legalFlexTime),
//						new AttendanceTimeMonthWithMinus(this.illegalFlexTime)),
//				new AttendanceTimeMonth(this.flexExcessTime),
//				new AttendanceTimeMonth(this.flexShortageTime),
//				FlexCarryforwardTime.of(
//						new AttendanceTimeMonth(this.flexCarryforwardWorkTime),
//						new AttendanceTimeMonth(this.flexCarryforwardTime),
//						new AttendanceTimeMonth(this.flexCarryforwardShortageTime)),
//				FlexTimeOfExcessOutsideTime.of(
//						EnumAdaptor.valueOf(this.excessFlexAtr, ExcessFlexAtr.class),
//						new AttendanceTimeMonth(this.principleTime),
//						new AttendanceTimeMonth(this.forConvenienceTime)),
//				FlexShortDeductTime.of(
//						new AttendanceDaysMonth(this.annualLeaveDeductDays),
//						new AttendanceTimeMonth(this.absenceDeductTime),
//						new AttendanceTimeMonth(this.shotTimeBeforeDeduct)));
//		
//		/*----------------------月別実績のフレックス時間------------------------------*/	
//				
//		
//		/*----------------------集計総労働時間------------------------------*/
//		
//		// 月別実績の就業時間
//		val workTime = WorkTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.workTime),
//				new AttendanceTimeMonth(this.withinPrescribedPremiumTime),
//				new AttendanceTimeMonth(this.actualWorkTime));
//		
//		//月別実績の残業時間
//		List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimeList = this.krcdtMonAggrOverTimes;
//		if(krcdtMonAggrOverTimeList == null) krcdtMonAggrOverTimeList = new ArrayList<>();
//		
//		OverTimeOfMonthly overTime = OverTimeOfMonthly.of(
//				new TimeMonthWithCalculation(
//						new AttendanceTimeMonth(this.totalOverTime),
//						new AttendanceTimeMonth(this.calcTotalOverTime)),
//				new AttendanceTimeMonth(this.beforeOverTime),
//				new TimeMonthWithCalculation(
//						new AttendanceTimeMonth(this.totalTransferOverTime),
//						new AttendanceTimeMonth(this.calcTotalTransferOverTime)),
//				krcdtMonAggrOverTimeList.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
//		
//		// 月別実績の休出時間
//		List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimeList = this.krcdtMonAggrHdwkTimes;
//		if(krcdtMonAggrHdwkTimeList == null) krcdtMonAggrHdwkTimeList = new ArrayList<>();
//		HolidayWorkTimeOfMonthly holidayWorkTime = HolidayWorkTimeOfMonthly.of(
//				new TimeMonthWithCalculation(
//						new AttendanceTimeMonth(this.totalHolidayWorkTime),
//						new AttendanceTimeMonth(this.calcTotalHolidayWorkTime)),
//				new AttendanceTimeMonth(this.beforeHolidayWorkTime),
//				new TimeMonthWithCalculation(
//						new AttendanceTimeMonth(this.totalTransferTime),
//						new AttendanceTimeMonth(this.calcTotalTransferTime)),
//				krcdtMonAggrHdwkTimeList.stream()
//					.map(c -> c.toDomain()).collect(Collectors.toList()));
//		
//		//月別実績の休暇使用時間
//		VacationUseTimeOfMonthly vacationUseTime = VacationUseTimeOfMonthly.of(
//				AnnualLeaveUseTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.annualLeaveUseTime)),
//				RetentionYearlyUseTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.retentionYearlyUseTime)),
//				SpecialHolidayUseTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.specialHolidayUseTime)),
//				CompensatoryLeaveUseTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.compensatoryLeaveUseTime)));
//		
//		// 月別実績の所定労働時間
//		val prescribedWorkingTime = PrescribedWorkingTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.schedulePrescribedWorkingTime),
//				new AttendanceTimeMonth(this.recordPrescribedWorkingTime));
//		
//		// 集計総労働時間
//		AggregateTotalWorkingTime aggregateTotalWorkingTime = AggregateTotalWorkingTime.of(
//																workTime,
//																overTime,
//																holidayWorkTime,
//																vacationUseTime,
//																prescribedWorkingTime);
//		
//		/*----------------------集計総労働時間------------------------------*/
//		
//		/*----------------------集計総拘束時間------------------------------*/
//		
//		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpent = AggregateTotalTimeSpentAtWork.of(
//																 new AttendanceTimeMonth(this.overTimeSpentAtWork),
//																 new AttendanceTimeMonth(this.midnightTimeSpentAtWork),
//																 new AttendanceTimeMonth(this.holidayTimeSpentAtWork),
//																 new AttendanceTimeMonth(this.varienceTimeSpentAtWork),
//																 new AttendanceTimeMonth(this.totalTimeSpentAtWork));
//		
//		/*----------------------集計総拘束時間------------------------------*/
//		
//		
//		/*----------------------月別実績の36協定時間------------------------------*/
//		
//		AgreementTimeOfMonthly agreementTime = AgreementTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.agreementTime),
//				new LimitOneMonth(this.limitErrorTime),
//				new LimitOneMonth(this.limitAlarmTime),
//				(this.exceptionLimitErrorTime == null ?
//						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitErrorTime))),
//				(this.exceptionLimitAlarmTime == null ?
//						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitAlarmTime))),
//				EnumAdaptor.valueOf(this.status, AgreementTimeStatusOfMonthly.class));
//		
//		/*----------------------月別実績の36協定時間------------------------------*/
//		
//		
//		// 月別実績の月の計算
//		val monthlyCalculation = MonthlyCalculation.of(
//				regAndIrgTime,
//				flexTime,
//				new AttendanceTimeMonth(this.statutoryWorkingTime),
//				aggregateTotalWorkingTime,
//				new AttendanceTimeMonth(this.totalWorkingTime),
//				aggregateTotalTimeSpent,
//				agreementTime);		
//		
//		// 月別実績の時間外超過
//		ExcessOutsideWorkOfMonthly excessOutsideWork = new ExcessOutsideWorkOfMonthly();
//		if (this.krcdtMonExcessOutside != null){
//			excessOutsideWork = this.krcdtMonExcessOutside.toDomain(this.krcdtMonExcoutTime);
//		}		
//		
//		
//		/*----------------------月別実績の縦計------------------------------*/
//		
//		List<KrcdtMonAggrAbsnDays> krcdtMonAggrAbsnDaysList = this.krcdtMonAggrAbsnDays;
//		List<KrcdtMonAggrSpecDays> krcdtMonAggrSpecDaysList = this.krcdtMonAggrSpecDays;
//		List<KrcdtMonAggrBnspyTime> krcdtMonAggrBnspyTimeList = this.krcdtMonAggrBnspyTime;
//		List<KrcdtMonAggrGoout> krcdtMonAggrGooutList = this.krcdtMonAggrGoout;
//		List<KrcdtMonAggrPremTime> krcdtMonAggrPremTimeList = this.krcdtMonAggrPremTime;
//		List<KrcdtMonAggrDivgTime> krcdtMonAggrDivgTimeList = this.krcdtMonAggrDivgTime;
//		List<KrcdtMonMedicalTime> krcdtMonMedicalTimeList = this.krcdtMonMedicalTime;
//		
//		
//		if (krcdtMonAggrAbsnDaysList == null) krcdtMonAggrAbsnDaysList = new ArrayList<>();
//		if (krcdtMonAggrSpecDaysList == null) krcdtMonAggrSpecDaysList = new ArrayList<>();
//		if (krcdtMonAggrBnspyTimeList == null) krcdtMonAggrBnspyTimeList = new ArrayList<>();
//		if (krcdtMonAggrGooutList == null) krcdtMonAggrGooutList = new ArrayList<>();
//		if (krcdtMonAggrPremTimeList == null) krcdtMonAggrPremTimeList = new ArrayList<>();
//		if (krcdtMonAggrDivgTimeList == null) krcdtMonAggrDivgTimeList = new ArrayList<>();
//		if (krcdtMonMedicalTimeList == null) krcdtMonMedicalTimeList = new ArrayList<>();
//		
//		// 育児外出
//		List<GoOutForChildCare> goOutForChildCares = new ArrayList<>();
//		if (this.childcareGoOutTimes != 0 || this.childcareGoOutTime != 0){
//			goOutForChildCares.add(GoOutForChildCare.of(
//					ChildCareAtr.CHILD_CARE,
//					new AttendanceTimesMonth(this.childcareGoOutTimes),
//					new AttendanceTimeMonth(this.childcareGoOutTime)));
//		}
//		if (this.careGoOutTimes != 0 || this.careGoOutTime != 0){
//			goOutForChildCares.add(GoOutForChildCare.of(
//					ChildCareAtr.CARE,
//					new AttendanceTimesMonth(this.careGoOutTimes),
//					new AttendanceTimeMonth(this.careGoOutTime)));
//		}
//		
//		// 月別実績の休業
//		List<AggregateLeaveDays> fixLeaveDaysList = new ArrayList<>();
//		List<AnyLeave> anyLeaveDaysList = new ArrayList<>();
//		
//		if (this.prenatalLeaveDays != 0.0){
//			fixLeaveDaysList.add(AggregateLeaveDays.of(
//					CloseAtr.PRENATAL, new AttendanceDaysMonth(this.prenatalLeaveDays)));
//		}
//		if (this.postpartumLeaveDays != 0.0){
//			fixLeaveDaysList.add(AggregateLeaveDays.of(
//					CloseAtr.POSTPARTUM, new AttendanceDaysMonth(this.postpartumLeaveDays)));
//		}
//		if (this.childcareLeaveDays != 0.0){
//			fixLeaveDaysList.add(AggregateLeaveDays.of(
//					CloseAtr.CHILD_CARE, new AttendanceDaysMonth(this.childcareLeaveDays)));
//		}
//		if (this.careLeaveDays != 0.0){
//			fixLeaveDaysList.add(AggregateLeaveDays.of(
//					CloseAtr.CARE, new AttendanceDaysMonth(this.careLeaveDays)));
//		}
//		if (this.injuryOrIllnessLeaveDays != 0.0){
//			fixLeaveDaysList.add(AggregateLeaveDays.of(
//					CloseAtr.INJURY_OR_ILLNESS, new AttendanceDaysMonth(this.injuryOrIllnessLeaveDays)));
//		}
//		if (this.anyLeaveDays01 != 0.0){
//			anyLeaveDaysList.add(AnyLeave.of(1, new AttendanceDaysMonth(this.anyLeaveDays01)));
//		}
//		if (this.anyLeaveDays02 != 0.0){
//			anyLeaveDaysList.add(AnyLeave.of(2, new AttendanceDaysMonth(this.anyLeaveDays02)));
//		}
//		if (this.anyLeaveDays03 != 0.0){
//			anyLeaveDaysList.add(AnyLeave.of(3, new AttendanceDaysMonth(this.anyLeaveDays03)));
//		}
//		if (this.anyLeaveDays04 != 0.0){
//			anyLeaveDaysList.add(AnyLeave.of(4, new AttendanceDaysMonth(this.anyLeaveDays04)));
//		}
//		
//		LeaveOfMonthly leave = LeaveOfMonthly.of(fixLeaveDaysList, anyLeaveDaysList);
//		
//		
//		// 月別実績の勤務日数
//		val workDays = WorkDaysOfMonthly.of(
//				AttendanceDaysOfMonthly.of(new AttendanceDaysMonth(this.attendanceDays)),
//				AbsenceDaysOfMonthly.of(
//						new AttendanceDaysMonth(this.totalAbsenceDays),
//						new AttendanceTimeMonth(this.totalAbsenceTime),
//						krcdtMonAggrAbsnDaysList.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
//				PredeterminedDaysOfMonthly.of(
//						new AttendanceDaysMonth(this.predetermineDays)),
//				WorkDaysDetailOfMonthly.of(new AttendanceDaysMonth(this.workDays)),
//				HolidayDaysOfMonthly.of(new AttendanceDaysMonth(this.holidayDays)),
//				SpecificDaysOfMonthly.of(
//						krcdtMonAggrSpecDaysList.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
//				HolidayWorkDaysOfMonthly.of(new AttendanceDaysMonth(this.holidayWorkDays)),
//				PayDaysOfMonthly.of(
//						new AttendanceDaysMonth(this.payAttendanceDays),
//						new AttendanceDaysMonth(this.payAbsenceDays)),
//				WorkTimesOfMonthly.of(new AttendanceTimesMonth(this.workTimes)),
//				TwoTimesWorkTimesOfMonthly.of(new AttendanceTimesMonth(this.twoTimesWorkTimes)),
//				TemporaryWorkTimesOfMonthly.of(new AttendanceTimesMonth(this.temporaryWorkTimes)),
//				leave);
//		
//		// 月別実績の勤務時間
//		val monthlyWorkTime = nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.WorkTimeOfMonthly.of(
//				BonusPayTimeOfMonthly.of(
//						krcdtMonAggrBnspyTimeList.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
//				GoOutOfMonthly.of(
//						krcdtMonAggrGooutList.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
//						goOutForChildCares),
//				PremiumTimeOfMonthly.of(
//						krcdtMonAggrPremTimeList.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
//						new AttendanceTimeMonth(this.premiumMidnightTime),
//						new AttendanceTimeMonth(this.premiumLegalOutsideWorkTime),
//						new AttendanceTimeMonth(this.premiumLegalHolidayWorkTime),
//						new AttendanceTimeMonth(this.premiumIllegalOutsideWorkTime),
//						new AttendanceTimeMonth(this.premiumIllegalHolidayWorkTime)),
//				BreakTimeOfMonthly.of(new AttendanceTimeMonth(this.breakTime)),
//				HolidayTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.legalHolidayTime),
//						new AttendanceTimeMonth(this.illegalHolidayTime),
//						new AttendanceTimeMonth(this.illegalSpecialHolidayTime)),
//				MidnightTimeOfMonthly.of(
//						new TimeMonthWithCalculation(
//								new AttendanceTimeMonth(this.overWorkMidnightTime),
//								new AttendanceTimeMonth(this.calcOverWorkMidnightTime)),
//						new TimeMonthWithCalculation(
//								new AttendanceTimeMonth(this.legalMidnightTime),
//								new AttendanceTimeMonth(this.calcLegalMidnightTime)),
//						IllegalMidnightTime.of(
//								new TimeMonthWithCalculation(
//										new AttendanceTimeMonth(this.illegalMidnightTime),
//										new AttendanceTimeMonth(this.calcIllegalMidnightTime)),
//								new AttendanceTimeMonth(this.illegalBeforeMidnightTime)),
//						new TimeMonthWithCalculation(
//								new AttendanceTimeMonth(this.legalHolidayWorkMidnightTime),
//								new AttendanceTimeMonth(this.calcLegalHolidayWorkMidnightTime)),
//						new TimeMonthWithCalculation(
//								new AttendanceTimeMonth(this.illegalHolidayWorkMidnightTime),
//								new AttendanceTimeMonth(this.calcIllegalHolidayWorkMidnightTime)),
//						new TimeMonthWithCalculation(
//								new AttendanceTimeMonth(this.specialHolidayWorkMidnightTime),
//								new AttendanceTimeMonth(this.calcSpecialHolidayWorkMidnightTime))),
//				LateLeaveEarlyOfMonthly.of(
//						LeaveEarly.of(
//								new AttendanceTimesMonth(this.leaveEarlyTimes),
//								new TimeMonthWithCalculation(
//										new AttendanceTimeMonth(this.leaveEarlyTime),
//										new AttendanceTimeMonth(this.calcLeaveEarlyTime))),
//						Late.of(
//								new AttendanceTimesMonth(this.lateTimes),
//								new TimeMonthWithCalculation(
//										new AttendanceTimeMonth(this.lateTime),
//										new AttendanceTimeMonth(this.calcLateTime)))),
//				AttendanceLeaveGateTimeOfMonthly.of(
//						new AttendanceTimeMonth(this.attendanceLeaveGateBeforeAttendanceTime),
//						new AttendanceTimeMonth(this.attendanceLeaveGateAfterLeaveWorkTime),
//						new AttendanceTimeMonth(this.attendanceLeaveGateStayingTime),
//						new AttendanceTimeMonth(this.attendanceLeaveGateUnemployedTime)),
//				BudgetTimeVarienceOfMonthly.of(new AttendanceTimeMonth(this.budgetVarienceTime)),
//				DivergenceTimeOfMonthly.of(
//						krcdtMonAggrDivgTimeList.stream().map(c -> c.toDomain()).collect(Collectors.toList())),
//				krcdtMonMedicalTimeList.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
//		
//		// 月別実績の勤務時刻
//		WorkClockOfMonthly workClock = new WorkClockOfMonthly();
//		if (krcdtMonWorkClock != null) workClock = krcdtMonWorkClock.toDomain();
//				
//		//月別実績の縦計
//		VerticalTotalOfMonthly verticalTotal = VerticalTotalOfMonthly.of(workDays, monthlyWorkTime, workClock);
//		
//		/*----------------------月別実績の縦計------------------------------*/
//		
//		
//		// 期間別の回数集計
//		TotalCountByPeriod totalCount = new TotalCountByPeriod();
//		if (this.krcdtMonTotalTimes != null){
//			totalCount = TotalCountByPeriod.of(
//					this.krcdtMonTotalTimes.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
//		}		
//		
//		return AttendanceTimeOfMonthly.of(
//				this.PK.employeeId,
//				new YearMonth(this.PK.yearMonth),
//				ClosureId.valueOf(this.PK.closureId),
//				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay != 0)),
//				new DatePeriod(this.startYmd, this.endYmd),
//				monthlyCalculation,
//				excessOutsideWork,
//				verticalTotal,
//				totalCount,
//				new AttendanceDaysMonth(this.aggregateDays));		
		
		//テーブル結合前に戻す場合に利用
		return null;
	}
	
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 月別実績の勤怠時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthly domain){
		
		this.PK = new KrcdtMonTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の勤怠時間
	 */
	public void fromDomainForUpdate(AttendanceTimeOfMonthly domain){

		val monthlyCalculation = domain.getMonthlyCalculation();
		
		this.startYmd = domain.getDatePeriod().start();
		this.endYmd = domain.getDatePeriod().end();
		this.aggregateDays = domain.getAggregateDays().v();
		this.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		this.totalWorkingTime = monthlyCalculation.getTotalWorkingTime().v();
		
		//各テーブルのエンティティに記載されていた fromDomainForUpdate 中身を以下に移植
			
		/*----------------------月別実績の通常変形時間------------------------------*/
		
		RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly = domain.getMonthlyCalculation().getActualWorkingTime();
		
		val irregularWorkingTime = regularAndIrregularTimeOfMonthly.getIrregularWorkingTime();
		
		this.weeklyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getWeeklyTotalPremiumTime().v();
		this.monthlyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getMonthlyTotalPremiumTime().v();
		this.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		this.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		this.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		this.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		this.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalcTime().v();
		
		/*----------------------月別実績の通常変形時間------------------------------*/
		
		/*----------------------月別実績のフレックス時間------------------------------*/
		
		FlexTimeOfMonthly flexTimeOfMonthly = domain.getMonthlyCalculation().getFlexTime();
		
		val flexTime = flexTimeOfMonthly.getFlexTime();
		val flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		val flexTimeOfExcessOutsideTime = flexTimeOfMonthly.getFlexTimeOfExcessOutsideTime();
		val flexShortDeductTime = flexTimeOfMonthly.getFlexShortDeductTime();
		
		this.flexTime = flexTime.getFlexTime().getTime().v();
		this.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		this.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		this.legalFlexTime = flexTime.getLegalFlexTime().v();
		this.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		this.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		this.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		this.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		this.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		this.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		this.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		this.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		this.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		this.annualLeaveDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays().v();
		this.absenceDeductTime = flexShortDeductTime.getAbsenceDeductTime().v();
		this.shotTimeBeforeDeduct = flexShortDeductTime.getFlexShortTimeBeforeDeduct().v();
			
		/*----------------------月別実績のフレックス時間------------------------------*/
		
		/*----------------------総労働時間：月別実績の休暇使用時間------------------------------*/
		
		VacationUseTimeOfMonthly vacationUseTimeOfMonthly = domain.getMonthlyCalculation().getAggregateTime().getVacationUseTime();
		
		this.annualLeaveUseTime = vacationUseTimeOfMonthly.getAnnualLeave().getUseTime().v();
		this.retentionYearlyUseTime = vacationUseTimeOfMonthly.getRetentionYearly().getUseTime().v();
		this.specialHolidayUseTime = vacationUseTimeOfMonthly.getSpecialHoliday().getUseTime().v();
		this.compensatoryLeaveUseTime = vacationUseTimeOfMonthly.getCompensatoryLeave().getUseTime().v();
		
		/*----------------------総労働時間：月別実績の休暇使用時間------------------------------*/
		
		/*----------------------総労働時間：集計総労働時間------------------------------*/
		
		AggregateTotalWorkingTime aggregateTotalWorkingTime = domain.getMonthlyCalculation().getAggregateTime();
		
		this.workTime = aggregateTotalWorkingTime.getWorkTime().getWorkTime().v();
		this.actualWorkTime = aggregateTotalWorkingTime.getWorkTime().getActualWorkTime().v();
		this.withinPrescribedPremiumTime = aggregateTotalWorkingTime.getWorkTime().getWithinPrescribedPremiumTime().v();
		this.schedulePrescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime().getSchedulePrescribedWorkingTime().v();
		this.recordPrescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime().getRecordPrescribedWorkingTime().v();
		
		/*----------------------総労働時間：集計総労働時間------------------------------*/
		
		/*----------------------総労働時間：月別実績の残業時間------------------------------*/
		
		OverTimeOfMonthly overTimeOfMonthly = domain.getMonthlyCalculation().getAggregateTime().getOverTime();
		
		this.totalOverTime = overTimeOfMonthly.getTotalOverTime().getTime().v();
		this.calcTotalOverTime = overTimeOfMonthly.getTotalOverTime().getCalcTime().v();
		this.beforeOverTime = overTimeOfMonthly.getBeforeOverTime().v();
		this.totalTransferOverTime = overTimeOfMonthly.getTotalTransferOverTime().getTime().v();
		this.calcTotalTransferOverTime = overTimeOfMonthly.getTotalTransferOverTime().getCalcTime().v();
		
		/*----------------------総労働時間：月別実績の残業時間------------------------------*/
		
		/*----------------------総労働時間：休出・代休：月別実績の休出時間------------------------------*/
		
		HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly = domain.getMonthlyCalculation().getAggregateTime().getHolidayWorkTime();
		
		this.totalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getTime().v();
		this.calcTotalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime = holidayWorkTimeOfMonthly.getBeforeHolidayWorkTime().v();
		this.totalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getTime().v();
		this.calcTotalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getCalcTime().v();
		
		/*----------------------総労働時間：休出・代休：月別実績の休出時間------------------------------*/
		
		/*----------------------集計総拘束時間------------------------------*/
		
		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork = domain.getMonthlyCalculation().getTotalTimeSpentAtWork();
		
		this.overTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getOverTimeSpentAtWork().v();
		this.midnightTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getMidnightTimeSpentAtWork().v();
		this.holidayTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getHolidayTimeSpentAtWork().v();
		this.varienceTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getVarienceTimeSpentAtWork().v();
		this.totalTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getTotalTimeSpentAtWork().v();
		
		/*----------------------集計総拘束時間------------------------------*/
		
		/*----------------------月別実績の36協定時間------------------------------*/
		
		AgreementTimeOfMonthly agreementTimeOfMonthly = domain.getMonthlyCalculation().getAgreementTime();
		
		this.agreementTime = agreementTimeOfMonthly.getAgreementTime().v();
		this.limitErrorTime = agreementTimeOfMonthly.getLimitErrorTime().v();
		this.limitAlarmTime = agreementTimeOfMonthly.getLimitAlarmTime().v();
		this.exceptionLimitErrorTime = (agreementTimeOfMonthly.getExceptionLimitErrorTime().isPresent() ?
				agreementTimeOfMonthly.getExceptionLimitErrorTime().get().v() : null); 
		this.exceptionLimitAlarmTime = (agreementTimeOfMonthly.getExceptionLimitAlarmTime().isPresent() ?
				agreementTimeOfMonthly.getExceptionLimitAlarmTime().get().v() : null);
		this.status = agreementTimeOfMonthly.getStatus().value;
		
		/*----------------------月別実績の36協定時間------------------------------*/
		
		/*----------------------月別実績の縦計------------------------------*/
		
		VerticalTotalOfMonthly verticalTotalOfMonthly = domain.getVerticalTotal();
		
		val vtWorkDays = verticalTotalOfMonthly.getWorkDays();
		val vtWorkTime = verticalTotalOfMonthly.getWorkTime();
		
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
		
		/*----------------------月別実績の縦計------------------------------*/
		
		/*----------------------縦計：勤務日数：月別実績の休業 ------------------------------*/
		
		LeaveOfMonthly leaveOfMonthly = domain.getVerticalTotal().getWorkDays().getLeave();
		
		this.prenatalLeaveDays = 0.0;
		this.postpartumLeaveDays = 0.0;
		this.childcareLeaveDays = 0.0;
		this.careLeaveDays = 0.0;
		this.injuryOrIllnessLeaveDays = 0.0;
		this.anyLeaveDays01 = 0.0;
		this.anyLeaveDays02 = 0.0;
		this.anyLeaveDays03 = 0.0;
		this.anyLeaveDays04 = 0.0;
		val fixLeaveDaysMap = leaveOfMonthly.getFixLeaveDays();
		if (fixLeaveDaysMap.containsKey(CloseAtr.PRENATAL)){
			this.prenatalLeaveDays = fixLeaveDaysMap.get(CloseAtr.PRENATAL).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.POSTPARTUM)){
			this.postpartumLeaveDays = fixLeaveDaysMap.get(CloseAtr.POSTPARTUM).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CHILD_CARE)){
			this.childcareLeaveDays = fixLeaveDaysMap.get(CloseAtr.CHILD_CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CARE)){
			this.careLeaveDays = fixLeaveDaysMap.get(CloseAtr.CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.INJURY_OR_ILLNESS)){
			this.injuryOrIllnessLeaveDays = fixLeaveDaysMap.get(CloseAtr.INJURY_OR_ILLNESS).getDays().v();
		}
		val anyLeaveDaysMap = leaveOfMonthly.getAnyLeaveDays();
		if (anyLeaveDaysMap.containsKey(1)){
			this.anyLeaveDays01 = anyLeaveDaysMap.get(1).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(2)){
			this.anyLeaveDays02 = anyLeaveDaysMap.get(2).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(3)){
			this.anyLeaveDays03 = anyLeaveDaysMap.get(3).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(4)){
			this.anyLeaveDays04 = anyLeaveDaysMap.get(4).getDays().v();
		}
		
		/*----------------------縦計：勤務日数：月別実績の休業 ------------------------------*/
			
	}
	
	
}
