package nts.uk.ctx.at.record.infra.entity.monthly.mergetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.ExcessFlexAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutSideWorkEachBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.OuenTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 残数系以外
 * @author lanlt
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MON_MERGE")
public class KrcdtMonMerge extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KrcdtMonMerge> MAPPER = new JpaEntityMapper<>(KrcdtMonMerge.class);

	@EmbeddedId
	public KrcdtMonMergePk krcdtMonMergePk;
	
	@Version
	@Column(name = "EXCLUS_VER")
	public long version;

	/** 開始年月日 */
	@Column(name = "START_YMD")
	public GeneralDate startYmd;

	/** 終了年月日 */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 集計日数 */
	@Column(name = "AGGREGATE_DAYS")
	public double aggregateDays;
	
	/* KRCDT_MON_AFFILIATION - 月別実績の所属情報．月末の情報 */
	/** 月初雇用コード */
	@Column(name = "FIRST_EMP_CD")
	public String firstEmploymentCd;

	/** 月初職場ID */
	@Column(name = "FIRST_WKP_ID")
	public String firstWorkplaceId;

	/** 月初職位ID */
	@Column(name = "FIRST_JOB_ID")
	public String firstJobTitleId;

	/** 月初分類コード */
	@Column(name = "FIRST_CLS_CD")
	public String firstClassCd;

	/** 月初勤務種別コード */
	@Column(name = "FIRST_BUS_CD")
	public String firstBusinessTypeCd;

	/** 月末雇用コード */
	@Column(name = "LAST_EMP_CD")
	public String lastEmploymentCd;

	/** 月末職場ID */
	@Column(name = "LAST_WKP_ID")
	public String lastWorkplaceId;

	/** 月末職位ID */
	@Column(name = "LAST_JOB_ID")
	public String lastJobTitleId;

	/** 月末分類コード */
	@Column(name = "LAST_CLS_CD")
	public String lastClassCd;

	/** 月末勤務種別コード */
	@Column(name = "LAST_BUS_CD")
	public String lastBusinessTypeCd;
	
	/* KRCDT_MON_AGGR_TOTAL_WRK */
	/** 所定内割増時間 */
	@Column(name = "WITPRS_PREMIUM_TIME")
	public int withinPrescribedPremiumTime;

	/** 計画所定労働時間 */
	@Column(name = "SCHE_PRS_WORK_TIME")
	public int schedulePrescribedWorkingTime;

	/** 実績所定労働時間 */
	@Column(name = "RECD_PRS_WORK_TIME")
	public int recordPrescribedWorkingTime;

	/* KRCDT_MON_ATTENDANCE_TIME */

	/** 法定労働時間 */
	@Column(name = "STAT_WORKING_TIME")
	public int statutoryWorkingTime;

	/** 総労働時間 */
	@Column(name = "TOTAL_WORKING_TIME")
	public int totalWorkingTime;
	
	/** 就業時間 */
	@Column(name = "WORK_TIME")
	public int workTime;

	/** 実働就業時間 */
	@Column(name = "ACTWORK_TIME")
	public int actualWorkTime;
	
	/** KRCDT_MON_AGGR_OVER_TIME 10 **/
	/** 残業時間 - OVER_TIME_1 */
	@Column(name = "OVER_TIME_1")
	public int overTime1;

	@Column(name = "OVER_TIME_2")
	public int overTime2;

	@Column(name = "OVER_TIME_3")
	public int overTime3;

	@Column(name = "OVER_TIME_4")
	public int overTime4;

	@Column(name = "OVER_TIME_5")
	public int overTime5;

	@Column(name = "OVER_TIME_6")
	public int overTime6;

	@Column(name = "OVER_TIME_7")
	public int overTime7;

	@Column(name = "OVER_TIME_8")
	public int overTime8;

	@Column(name = "OVER_TIME_9")
	public int overTime9;

	@Column(name = "OVER_TIME_10")
	public int overTime10;

	/** 計算残業時間 - CALC_OVER_TIME_1 */
	@Column(name = "CALC_OVER_TIME_1")
	public int calcOverTime1;

	@Column(name = "CALC_OVER_TIME_2")
	public int calcOverTime2;

	@Column(name = "CALC_OVER_TIME_3")
	public int calcOverTime3;

	@Column(name = "CALC_OVER_TIME_4")
	public int calcOverTime4;

	@Column(name = "CALC_OVER_TIME_5")
	public int calcOverTime5;

	@Column(name = "CALC_OVER_TIME_6")
	public int calcOverTime6;

	@Column(name = "CALC_OVER_TIME_7")
	public int calcOverTime7;

	@Column(name = "CALC_OVER_TIME_8")
	public int calcOverTime8;

	@Column(name = "CALC_OVER_TIME_9")
	public int calcOverTime9;

	@Column(name = "CALC_OVER_TIME_10")
	public int calcOverTime10;

	/** 事前残業時間 - BEFORE_OVER_TIME_1 */
	@Column(name = "BEFORE_OVER_TIME_1")
	public int beforeOverTime1;

	@Column(name = "BEFORE_OVER_TIME_2")
	public int beforeOverTime2;

	@Column(name = "BEFORE_OVER_TIME_3")
	public int beforeOverTime3;

	@Column(name = "BEFORE_OVER_TIME_4")
	public int beforeOverTime4;

	@Column(name = "BEFORE_OVER_TIME_5")
	public int beforeOverTime5;

	@Column(name = "BEFORE_OVER_TIME_6")
	public int beforeOverTime6;

	@Column(name = "BEFORE_OVER_TIME_7")
	public int beforeOverTime7;

	@Column(name = "BEFORE_OVER_TIME_8")
	public int beforeOverTime8;

	@Column(name = "BEFORE_OVER_TIME_9")
	public int beforeOverTime9;

	@Column(name = "BEFORE_OVER_TIME_10")
	public int beforeOverTime10;

	/** 振替残業時間 - TRNOVR_TIME_1 */
	@Column(name = "TRNOVR_TIME_1")
	public int transferOverTime1;

	@Column(name = "TRNOVR_TIME_2")
	public int transferOverTime2;

	@Column(name = "TRNOVR_TIME_3")
	public int transferOverTime3;

	@Column(name = "TRNOVR_TIME_4")
	public int transferOverTime4;

	@Column(name = "TRNOVR_TIME_5")
	public int transferOverTime5;

	@Column(name = "TRNOVR_TIME_6")
	public int transferOverTime6;

	@Column(name = "TRNOVR_TIME_7")
	public int transferOverTime7;

	@Column(name = "TRNOVR_TIME_8")
	public int transferOverTime8;

	@Column(name = "TRNOVR_TIME_9")
	public int transferOverTime9;

	@Column(name = "TRNOVR_TIME_10")
	public int transferOverTime10;

	/** 計算振替残業時間 - CALC_TRNOVR_TIME_1 */
	@Column(name = "CALC_TRNOVR_TIME_1")
	public int calcTransferOverTime1;

	@Column(name = "CALC_TRNOVR_TIME_2")
	public int calcTransferOverTime2;

	@Column(name = "CALC_TRNOVR_TIME_3")
	public int calcTransferOverTime3;

	@Column(name = "CALC_TRNOVR_TIME_4")
	public int calcTransferOverTime4;

	@Column(name = "CALC_TRNOVR_TIME_5")
	public int calcTransferOverTime5;

	@Column(name = "CALC_TRNOVR_TIME_6")
	public int calcTransferOverTime6;

	@Column(name = "CALC_TRNOVR_TIME_7")
	public int calcTransferOverTime7;

	@Column(name = "CALC_TRNOVR_TIME_8")
	public int calcTransferOverTime8;

	@Column(name = "CALC_TRNOVR_TIME_9")
	public int calcTransferOverTime9;

	@Column(name = "CALC_TRNOVR_TIME_10")
	public int calcTransferOverTime10;

	/** 法定内残業時間 -LEGAL_OVER_TIME_1 */
	@Column(name = "LEGAL_OVER_TIME_1")
	public int legalOverTime1;

	@Column(name = "LEGAL_OVER_TIME_2")
	public int legalOverTime2;

	@Column(name = "LEGAL_OVER_TIME_3")
	public int legalOverTime3;

	@Column(name = "LEGAL_OVER_TIME_4")
	public int legalOverTime4;

	@Column(name = "LEGAL_OVER_TIME_5")
	public int legalOverTime5;

	@Column(name = "LEGAL_OVER_TIME_6")
	public int legalOverTime6;

	@Column(name = "LEGAL_OVER_TIME_7")
	public int legalOverTime7;

	@Column(name = "LEGAL_OVER_TIME_8")
	public int legalOverTime8;

	@Column(name = "LEGAL_OVER_TIME_9")
	public int legalOverTime9;

	@Column(name = "LEGAL_OVER_TIME_10")
	public int legalOverTime10;

	/** 法定内振替残業時間 - LEGAL_TRNOVR_TIME_1 */
	@Column(name = "LEGAL_TRNOVR_TIME_1")
	public int legalTransferOverTime1;

	@Column(name = "LEGAL_TRNOVR_TIME_2")
	public int legalTransferOverTime2;

	@Column(name = "LEGAL_TRNOVR_TIME_3")
	public int legalTransferOverTime3;

	@Column(name = "LEGAL_TRNOVR_TIME_4")
	public int legalTransferOverTime4;

	@Column(name = "LEGAL_TRNOVR_TIME_5")
	public int legalTransferOverTime5;

	@Column(name = "LEGAL_TRNOVR_TIME_6")
	public int legalTransferOverTime6;

	@Column(name = "LEGAL_TRNOVR_TIME_7")
	public int legalTransferOverTime7;

	@Column(name = "LEGAL_TRNOVR_TIME_8")
	public int legalTransferOverTime8;

	@Column(name = "LEGAL_TRNOVR_TIME_9")
	public int legalTransferOverTime9;

	@Column(name = "LEGAL_TRNOVR_TIME_10")
	public int legalTransferOverTime10;

	/* KRCDT_MON_AGGR_HDWK_TIME 10 */
	/** 休出時間 - HDWK_TIME_1 */
	@Column(name = "HDWK_TIME_1")
	public int holidayWorkTime1;

	@Column(name = "HDWK_TIME_2")
	public int holidayWorkTime2;

	@Column(name = "HDWK_TIME_3")
	public int holidayWorkTime3;

	@Column(name = "HDWK_TIME_4")
	public int holidayWorkTime4;

	@Column(name = "HDWK_TIME_5")
	public int holidayWorkTime5;

	@Column(name = "HDWK_TIME_6")
	public int holidayWorkTime6;

	@Column(name = "HDWK_TIME_7")
	public int holidayWorkTime7;

	@Column(name = "HDWK_TIME_8")
	public int holidayWorkTime8;

	@Column(name = "HDWK_TIME_9")
	public int holidayWorkTime9;

	@Column(name = "HDWK_TIME_10")
	public int holidayWorkTime10;

	/** 計算休出時間 - CALC_HDWK_TIME_1 */
	@Column(name = "CALC_HDWK_TIME_1")
	public int calcHolidayWorkTime1;

	@Column(name = "CALC_HDWK_TIME_2")
	public int calcHolidayWorkTime2;

	@Column(name = "CALC_HDWK_TIME_3")
	public int calcHolidayWorkTime3;

	@Column(name = "CALC_HDWK_TIME_4")
	public int calcHolidayWorkTime4;

	@Column(name = "CALC_HDWK_TIME_5")
	public int calcHolidayWorkTime5;

	@Column(name = "CALC_HDWK_TIME_6")
	public int calcHolidayWorkTime6;

	@Column(name = "CALC_HDWK_TIME_7")
	public int calcHolidayWorkTime7;

	@Column(name = "CALC_HDWK_TIME_8")
	public int calcHolidayWorkTime8;

	@Column(name = "CALC_HDWK_TIME_9")
	public int calcHolidayWorkTime9;

	@Column(name = "CALC_HDWK_TIME_10")
	public int calcHolidayWorkTime10;

	/** 事前休出時間 - BEFORE_HDWK_TIME_1 */
	@Column(name = "BEFORE_HDWK_TIME_1")
	public int beforeHolidayWorkTime1;

	@Column(name = "BEFORE_HDWK_TIME_2")
	public int beforeHolidayWorkTime2;

	@Column(name = "BEFORE_HDWK_TIME_3")
	public int beforeHolidayWorkTime3;

	@Column(name = "BEFORE_HDWK_TIME_4")
	public int beforeHolidayWorkTime4;

	@Column(name = "BEFORE_HDWK_TIME_5")
	public int beforeHolidayWorkTime5;

	@Column(name = "BEFORE_HDWK_TIME_6")
	public int beforeHolidayWorkTime6;

	@Column(name = "BEFORE_HDWK_TIME_7")
	public int beforeHolidayWorkTime7;

	@Column(name = "BEFORE_HDWK_TIME_8")
	public int beforeHolidayWorkTime8;

	@Column(name = "BEFORE_HDWK_TIME_9")
	public int beforeHolidayWorkTime9;

	@Column(name = "BEFORE_HDWK_TIME_10")
	public int beforeHolidayWorkTime10;

	/** 振替時間 - TRN_TIME_1 */
	@Column(name = "TRN_TIME_1")
	public int transferTime1;

	@Column(name = "TRN_TIME_2")
	public int transferTime2;

	@Column(name = "TRN_TIME_3")
	public int transferTime3;

	@Column(name = "TRN_TIME_4")
	public int transferTime4;

	@Column(name = "TRN_TIME_5")
	public int transferTime5;

	@Column(name = "TRN_TIME_6")
	public int transferTime6;

	@Column(name = "TRN_TIME_7")
	public int transferTime7;

	@Column(name = "TRN_TIME_8")
	public int transferTime8;

	@Column(name = "TRN_TIME_9")
	public int transferTime9;

	@Column(name = "TRN_TIME_10")
	public int transferTime10;

	/** 計算振替時間 - CALC_TRN_TIME_1 */
	@Column(name = "CALC_TRN_TIME_1")
	public int calcTransferTime1;

	@Column(name = "CALC_TRN_TIME_2")
	public int calcTransferTime2;

	@Column(name = "CALC_TRN_TIME_3")
	public int calcTransferTime3;

	@Column(name = "CALC_TRN_TIME_4")
	public int calcTransferTime4;

	@Column(name = "CALC_TRN_TIME_5")
	public int calcTransferTime5;

	@Column(name = "CALC_TRN_TIME_6")
	public int calcTransferTime6;

	@Column(name = "CALC_TRN_TIME_7")
	public int calcTransferTime7;

	@Column(name = "CALC_TRN_TIME_8")
	public int calcTransferTime8;

	@Column(name = "CALC_TRN_TIME_9")
	public int calcTransferTime9;

	@Column(name = "CALC_TRN_TIME_10")
	public int calcTransferTime10;

	/** 振替時間 - LEGAL_HDWK_TIME_1 */
	@Column(name = "LEGAL_HDWK_TIME_1")
	public int legalHolidayWorkTime1;

	@Column(name = "LEGAL_HDWK_TIME_2")
	public int legalHolidayWorkTime2;

	@Column(name = "LEGAL_HDWK_TIME_3")
	public int legalHolidayWorkTime3;

	@Column(name = "LEGAL_HDWK_TIME_4")
	public int legalHolidayWorkTime4;

	@Column(name = "LEGAL_HDWK_TIME_5")
	public int legalHolidayWorkTime5;

	@Column(name = "LEGAL_HDWK_TIME_6")
	public int legalHolidayWorkTime6;

	@Column(name = "LEGAL_HDWK_TIME_7")
	public int legalHolidayWorkTime7;

	@Column(name = "LEGAL_HDWK_TIME_8")
	public int legalHolidayWorkTime8;

	@Column(name = "LEGAL_HDWK_TIME_9")
	public int legalHolidayWorkTime9;

	@Column(name = "LEGAL_HDWK_TIME_10")
	public int legalHolidayWorkTime10;

	/** 法定内振替休出時間 - LEGAL_TRN_HDWK_TIME_1 */
	@Column(name = "LEGAL_TRN_HDWK_TIME_1")
	public int legalTransferHolidayWorkTime1;

	@Column(name = "LEGAL_TRN_HDWK_TIME_2")
	public int legalTransferHolidayWorkTime2;

	@Column(name = "LEGAL_TRN_HDWK_TIME_3")
	public int legalTransferHolidayWorkTime3;

	@Column(name = "LEGAL_TRN_HDWK_TIME_4")
	public int legalTransferHolidayWorkTime4;

	@Column(name = "LEGAL_TRN_HDWK_TIME_5")
	public int legalTransferHolidayWorkTime5;

	@Column(name = "LEGAL_TRN_HDWK_TIME_6")
	public int legalTransferHolidayWorkTime6;

	@Column(name = "LEGAL_TRN_HDWK_TIME_7")
	public int legalTransferHolidayWorkTime7;

	@Column(name = "LEGAL_TRN_HDWK_TIME_8")
	public int legalTransferHolidayWorkTime8;

	@Column(name = "LEGAL_TRN_HDWK_TIME_9")
	public int legalTransferHolidayWorkTime9;

	@Column(name = "LEGAL_TRN_HDWK_TIME_10")
	public int legalTransferHolidayWorkTime10;
	
	/* KRCDT_MON_HDWK_TIME */
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
	
	/* KRCDT_MON_OVER_TIME */
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

	/** KRCDT_MON_AGGR_TOTAL_SPT **/
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
	
	/* KRCDT_MON_FLEX_TIME */
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
	
	//月別実績の勤怠時間．月の計算．36協定上限時間
	/** 36協定上限時間 */
	@Column(name = "AGREEMENT_REG_TIME")
	public int agreementRegTime;

	/** フレックス繰越不可時間 */
	@Column(name = "FLEX_NOT_CRYFWD_TIME")
	public int flexNotCarryforwardTime;
	
	/** 当月精算フレックス時間 */
	@Column(name = "FLEX_SETTLE_TIME")
	public int flexSettleTime;
	
	/** フレックス時間：当月フレックス時間：フレックス時間 */
	@Column(name = "FLEX_TIME_CUR")
	public int flexTimeCurrent;
	/** フレックス時間：当月フレックス時間：基準時間 */
	@Column(name = "STD_TIME_CUR")
	public int standardTimeCurrent;
	/** フレックス時間：当月フレックス時間：週平均超過時間 */
	@Column(name = "EXC_WA_TIME_CUR")
	public int excessWeekAveTimeCurrent;
	
	/** 時間外超過：当月フレックス時間：フレックス時間 */
	@Column(name = "FLEX_TIME_CUR_OT")
	public int flexTimeCurrentOT;
	/** 時間外超過：当月フレックス時間：基準時間 */
	@Column(name = "STD_TIME_CUR_OT")
	public int standardTimeCurrentOT;
	/** 時間外超過：当月フレックス時間：週平均超過時間 */
	@Column(name = "EXC_WA_TIME_CUR_OT")
	public int excessWeekAveTimeCurrentOT;

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

	/* KRCDT_MON_REG_IRREG_TIME */
	/** 月別実績の勤怠時間．時間外超過- 週割増合計時間 - 時間外超過用*/
	@Column(name = "WEEK_TOTAL_PREM_TIME")
	public int weeklyTotalPremiumTime;

	/** 月別実績の勤怠時間．時間外超過 - 月割増合計時間 - 時間外超過用*/
	@Column(name = "MON_TOTAL_PREM_TIME")
	public int monthlyTotalPremiumTime;

	/**月別実績の勤怠時間．時間外超過 - 変形繰越時間 - 時間外超過用変形繰越時間*/
	@Column(name = "MULTI_MON_IRGMDL_TIME")
	public int multiMonthIrregularMiddleTime;

	/** 月別実績の勤怠時間．時間外超過  - 変形繰越時間 - 時間外超過用変形繰越時間 */
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

	/* KRCDT_MON_VACT_USE_TIME */

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

	/* KRCDT_MON_EXCESS_OUTSIDE  */
	@Column(name = "EXCESS_WEEK_TOTAL_PREM_TIME")
	public int totalWeeklyPremiumTime1;

	@Column(name = "EXCESS_MON_TOTAL_PREM_TIME")
	public int totalMonthlyPremiumTime1;

	@Column(name = "EXCESS_MULTI_MON_IRGMDL_TIME")
	public int multiMonIrgmdlTime;

	/* KRCDT_MON_EXCOUT_TIME 50*/
	/** 月別実績の勤怠時間．時間外超過．時間 - 超過時間 - 超過時間 - EXCESS_TIME_1_1*/
	@Column(name = "EXCESS_TIME_1_1")
	public int excessTime_1_1;

	@Column(name = "EXCESS_TIME_1_2")
	public int excessTime_1_2;

	@Column(name = "EXCESS_TIME_1_3")
	public int excessTime_1_3;

	@Column(name = "EXCESS_TIME_1_4")
	public int excessTime_1_4;

	@Column(name = "EXCESS_TIME_1_5")
	public int excessTime_1_5;

	@Column(name = "EXCESS_TIME_1_6")
	public int excessTime_1_6;

	@Column(name = "EXCESS_TIME_1_7")
	public int excessTime_1_7;

	@Column(name = "EXCESS_TIME_1_8")
	public int excessTime_1_8;

	@Column(name = "EXCESS_TIME_1_9")
	public int excessTime_1_9;

	@Column(name = "EXCESS_TIME_1_10")
	public int excessTime_1_10;

	@Column(name = "EXCESS_TIME_2_1")
	public int excessTime_2_1;

	@Column(name = "EXCESS_TIME_2_2")
	public int excessTime_2_2;

	@Column(name = "EXCESS_TIME_2_3")
	public int excessTime_2_3;

	@Column(name = "EXCESS_TIME_2_4")
	public int excessTime_2_4;

	@Column(name = "EXCESS_TIME_2_5")
	public int excessTime_2_5;

	@Column(name = "EXCESS_TIME_2_6")
	public int excessTime_2_6;

	@Column(name = "EXCESS_TIME_2_7")
	public int excessTime_2_7;

	@Column(name = "EXCESS_TIME_2_8")
	public int excessTime_2_8;

	@Column(name = "EXCESS_TIME_2_9")
	public int excessTime_2_9;

	@Column(name = "EXCESS_TIME_2_10")
	public int excessTime_2_10;

	@Column(name = "EXCESS_TIME_3_1")
	public int excessTime_3_1;

	@Column(name = "EXCESS_TIME_3_2")
	public int excessTime_3_2;

	@Column(name = "EXCESS_TIME_3_3")
	public int excessTime_3_3;

	@Column(name = "EXCESS_TIME_3_4")
	public int excessTime_3_4;

	@Column(name = "EXCESS_TIME_3_5")
	public int excessTime_3_5;

	@Column(name = "EXCESS_TIME_3_6")
	public int excessTime_3_6;

	@Column(name = "EXCESS_TIME_3_7")
	public int excessTime_3_7;
	
	@Column(name = "EXCESS_TIME_3_8")
	public int excessTime_3_8;

	@Column(name = "EXCESS_TIME_3_9")
	public int excessTime_3_9;

	@Column(name = "EXCESS_TIME_3_10")
	public int excessTime_3_10;

	@Column(name = "EXCESS_TIME_4_1")
	public int excessTime_4_1;

	@Column(name = "EXCESS_TIME_4_2")
	public int excessTime_4_2;

	@Column(name = "EXCESS_TIME_4_3")
	public int excessTime_4_3;

	@Column(name = "EXCESS_TIME_4_4")
	public int excessTime_4_4;

	@Column(name = "EXCESS_TIME_4_5")
	public int excessTime_4_5;

	@Column(name = "EXCESS_TIME_4_6")
	public int excessTime_4_6;

	@Column(name = "EXCESS_TIME_4_7")
	public int excessTime_4_7;

	@Column(name = "EXCESS_TIME_4_8")
	public int excessTime_4_8;

	@Column(name = "EXCESS_TIME_4_9")
	public int excessTime_4_9;

	@Column(name = "EXCESS_TIME_4_10")
	public int excessTime_4_10;

	@Column(name = "EXCESS_TIME_5_1")
	public int excessTime_5_1;

	@Column(name = "EXCESS_TIME_5_2")
	public int excessTime_5_2;

	@Column(name = "EXCESS_TIME_5_3")
	public int excessTime_5_3;

	@Column(name = "EXCESS_TIME_5_4")
	public int excessTime_5_4;

	@Column(name = "EXCESS_TIME_5_5")
	public int excessTime_5_5;

	@Column(name = "EXCESS_TIME_5_6")
	public int excessTime_5_6;

	@Column(name = "EXCESS_TIME_5_7")
	public int excessTime_5_7;

	@Column(name = "EXCESS_TIME_5_8")
	public int excessTime_5_8;

	@Column(name = "EXCESS_TIME_5_9")
	public int excessTime_5_9;

	@Column(name = "EXCESS_TIME_5_10")
	public int excessTime_5_10;

	/* KRCDT_MON_AGREEMENT_TIME */
	/** 月別実績の勤怠時間．月の計算．36協定時間 - 36協定時間 -36協定時間  -AGREEMENT_TIME*/
	@Column(name = "AGREEMENT_TIME")
	public int agreementTime;

	/**月別実績の勤怠時間．月の計算．36協定時間 - 限度アラーム時間 - 限度エラー時間 - LIMIT_ERROR_TIME*/
	@Column(name = "LIMIT_ERROR_TIME")
	public int limitErrorTime;

	/**月別実績の勤怠時間．月の計算．36協定時間 - 特例限度エラー時間 - 限度アラーム時間 - LIMIT_ALARM_TIME*/
	@Column(name = "LIMIT_ALARM_TIME")
	public int limitAlarmTime;

	/** 月別実績の勤怠時間．月の計算．36協定時間 - 特例限度エラー時間 - 特例限度エラー時間 - EXCEPT_LIMIT_ERR_TIME*/
	@Column(name = "EXCEPT_LIMIT_ERR_TIME")
	public Integer exceptionLimitErrorTime;

	/** 月別実績の勤怠時間．月の計算．36協定時間 - 特例限度アラーム時間 - 特例限度アラーム時間 - EXCEPT_LIMIT_ALM_TIME */
	@Column(name = "EXCEPT_LIMIT_ALM_TIME")
	public Integer exceptionLimitAlarmTime;

	/**月別実績の勤怠時間．月の計算．36協定時間 - 状態- 状態  - AGREEMENT_STATUS*/
	@Column(name = "AGREEMENT_STATUS")
	public int status;

	//月別実績の勤怠時間．回数集計．回数集計
	/** 回数集計時間1 */
	@Column(name = "TOTAL_COUNT_TIME_1")
	public int totalCountTime1;

	@Column(name = "TOTAL_COUNT_TIME_2")
	public int totalCountTime2;

	@Column(name = "TOTAL_COUNT_TIME_3")
	public int totalCountTime3;

	@Column(name = "TOTAL_COUNT_TIME_4")
	public int totalCountTime4;

	@Column(name = "TOTAL_COUNT_TIME_5")
	public int totalCountTime5;

	@Column(name = "TOTAL_COUNT_TIME_6")
	public int totalCountTime6;

	@Column(name = "TOTAL_COUNT_TIME_7")
	public int totalCountTime7;

	@Column(name = "TOTAL_COUNT_TIME_8")
	public int totalCountTime8;

	@Column(name = "TOTAL_COUNT_TIME_9")
	public int totalCountTime9;

	@Column(name = "TOTAL_COUNT_TIME_10")
	public int totalCountTime10;

	@Column(name = "TOTAL_COUNT_TIME_11")
	public int totalCountTime11;

	@Column(name = "TOTAL_COUNT_TIME_12")
	public int totalCountTime12;

	@Column(name = "TOTAL_COUNT_TIME_13")
	public int totalCountTime13;

	@Column(name = "TOTAL_COUNT_TIME_14")
	public int totalCountTime14;

	@Column(name = "TOTAL_COUNT_TIME_15")
	public int totalCountTime15;

	@Column(name = "TOTAL_COUNT_TIME_16")
	public int totalCountTime16;

	@Column(name = "TOTAL_COUNT_TIME_17")
	public int totalCountTime17;

	@Column(name = "TOTAL_COUNT_TIME_18")
	public int totalCountTime18;

	@Column(name = "TOTAL_COUNT_TIME_19")
	public int totalCountTime19;

	@Column(name = "TOTAL_COUNT_TIME_20")
	public int totalCountTime20;

	@Column(name = "TOTAL_COUNT_TIME_21")
	public int totalCountTime21;

	@Column(name = "TOTAL_COUNT_TIME_22")
	public int totalCountTime22;

	@Column(name = "TOTAL_COUNT_TIME_23")
	public int totalCountTime23;

	@Column(name = "TOTAL_COUNT_TIME_24")
	public int totalCountTime24;

	@Column(name = "TOTAL_COUNT_TIME_25")
	public int totalCountTime25;

	@Column(name = "TOTAL_COUNT_TIME_26")
	public int totalCountTime26;

	@Column(name = "TOTAL_COUNT_TIME_27")
	public int totalCountTime27;

	@Column(name = "TOTAL_COUNT_TIME_28")
	public int totalCountTime28;

	@Column(name = "TOTAL_COUNT_TIME_29")
	public int totalCountTime29;

	@Column(name = "TOTAL_COUNT_TIME_30")
	public int totalCountTime30;
	
	//月別実績の勤怠時間．回数集計．回数集計
	/** 回数 - 回数集計回数1*/
	@Column(name = "TOTAL_COUNT_DAYS1")
	public double totalCountDays1;

	@Column(name = "TOTAL_COUNT_DAYS2")
	public double totalCountDays2;

	@Column(name = "TOTAL_COUNT_DAYS3")
	public double totalCountDays3;

	@Column(name = "TOTAL_COUNT_DAYS4")
	public double totalCountDays4;

	@Column(name = "TOTAL_COUNT_DAYS5")
	public double totalCountDays5;

	@Column(name = "TOTAL_COUNT_DAYS6")
	public double totalCountDays6;

	@Column(name = "TOTAL_COUNT_DAYS7")
	public double totalCountDays7;

	@Column(name = "TOTAL_COUNT_DAYS8")
	public double totalCountDays8;

	@Column(name = "TOTAL_COUNT_DAYS9")
	public double totalCountDays9;

	@Column(name = "TOTAL_COUNT_DAYS10")
	public double totalCountDays10;

	@Column(name = "TOTAL_COUNT_DAYS11")
	public double totalCountDays11;

	@Column(name = "TOTAL_COUNT_DAYS12")
	public double totalCountDays12;

	@Column(name = "TOTAL_COUNT_DAYS13")
	public double totalCountDays13;

	@Column(name = "TOTAL_COUNT_DAYS14")
	public double totalCountDays14;

	@Column(name = "TOTAL_COUNT_DAYS15")
	public double totalCountDays15;

	@Column(name = "TOTAL_COUNT_DAYS16")
	public double totalCountDays16;

	@Column(name = "TOTAL_COUNT_DAYS17")
	public double totalCountDays17;

	@Column(name = "TOTAL_COUNT_DAYS18")
	public double totalCountDays18;

	@Column(name = "TOTAL_COUNT_DAYS19")
	public double totalCountDays19;

	@Column(name = "TOTAL_COUNT_DAYS20")
	public double totalCountDays20;

	@Column(name = "TOTAL_COUNT_DAYS21")
	public double totalCountDays21;

	@Column(name = "TOTAL_COUNT_DAYS22")
	public double totalCountDays22;

	@Column(name = "TOTAL_COUNT_DAYS23")
	public double totalCountDays23;

	@Column(name = "TOTAL_COUNT_DAYS24")
	public double totalCountDays24;

	@Column(name = "TOTAL_COUNT_DAYS25")
	public double totalCountDays25;

	@Column(name = "TOTAL_COUNT_DAYS26")
	public double totalCountDays26;

	@Column(name = "TOTAL_COUNT_DAYS27")
	public double totalCountDays27;

	@Column(name = "TOTAL_COUNT_DAYS28")
	public double totalCountDays28;

	@Column(name = "TOTAL_COUNT_DAYS29")
	public double totalCountDays29;

	@Column(name = "TOTAL_COUNT_DAYS30")
	public double totalCountDays30;
	
	@Override
	protected Object getKey() {
		return this.krcdtMonMergePk;
	}
	
	public 	MonthMergeKey toDomainKey() {
		MonthMergeKey key = new MonthMergeKey();
		key.setEmployeeId(this.krcdtMonMergePk.getEmployeeId());
		key.setYearMonth(new YearMonth(this.krcdtMonMergePk.getYearMonth()));
		key.setClosureId(EnumAdaptor.valueOf(this.krcdtMonMergePk.getClosureId(), ClosureId.class));
		key.setClosureDate(new ClosureDate(this.krcdtMonMergePk.getClosureDay(),
			(this.krcdtMonMergePk.getIsLastDay() == 1)));
		return key;
	}

	/** KRCDT_MON_AGGR_OVER_TIME 10 **/
	private void toEntityOverTime(Map<OverTimeFrameNo, AggregateOverTime> aggregateOverTimeMap) {
		for (int i = 1; i <= 10; i++){
			OverTimeFrameNo frameNo = new OverTimeFrameNo(i);
			AggregateOverTime aggrOverTime = new AggregateOverTime(frameNo);
			if (aggregateOverTimeMap.containsKey(frameNo)){
				aggrOverTime = aggregateOverTimeMap.get(frameNo);
			}
			switch (i) {
			case 1:
				toEntityOverTime1(aggrOverTime);
				break;
			case 2:
				toEntityOverTime2(aggrOverTime);
				break;
			case 3:
				toEntityOverTime3(aggrOverTime);
				break;
			case 4:
				toEntityOverTime4(aggrOverTime);
				break;
			case 5:
				toEntityOverTime5(aggrOverTime);
				break;
			case 6:
				toEntityOverTime6(aggrOverTime);
				break;
			case 7:
				toEntityOverTime7(aggrOverTime);
				break;
			case 8:
				toEntityOverTime8(aggrOverTime);
				break;
			case 9:
				toEntityOverTime9(aggrOverTime);
				break;
			case 10:
				toEntityOverTime10(aggrOverTime);
				break;
			}
		}
	}

	/** KRCDT_MON_EXCOUT_TIME 50 **/
	private void toEntityExcessOutsideWorkMerge(ExcessOutsideWorkOfMonthly excessOutsideWork) {
		
		// 時間外超過
		toEntityExcessOutsideWorkOfMonthly(excessOutsideWork);
		
		// 時間外超過：時間
		Map<Integer, ExcessOutSideWorkEachBreakdown> excessOutsideTimeMap = excessOutsideWork == null ? new HashMap<>() : excessOutsideWork.getTime();
		
		toEntityExcessOutsideWork1_1(getExcessTime(excessOutsideTimeMap, 1, 1));
		toEntityExcessOutsideWork1_2(getExcessTime(excessOutsideTimeMap, 2, 1));
		toEntityExcessOutsideWork1_3(getExcessTime(excessOutsideTimeMap, 3, 1));
		toEntityExcessOutsideWork1_4(getExcessTime(excessOutsideTimeMap, 4, 1));
		toEntityExcessOutsideWork1_5(getExcessTime(excessOutsideTimeMap, 5, 1));
		toEntityExcessOutsideWork1_6(getExcessTime(excessOutsideTimeMap, 6, 1));
		toEntityExcessOutsideWork1_7(getExcessTime(excessOutsideTimeMap, 7, 1));
		toEntityExcessOutsideWork1_8(getExcessTime(excessOutsideTimeMap, 8, 1));
		toEntityExcessOutsideWork1_9(getExcessTime(excessOutsideTimeMap, 9, 1));
		toEntityExcessOutsideWork1_10(getExcessTime(excessOutsideTimeMap, 10, 1));
		toEntityExcessOutsideWork2_1(getExcessTime(excessOutsideTimeMap, 1, 2));
		toEntityExcessOutsideWork2_2(getExcessTime(excessOutsideTimeMap, 2, 2));
		toEntityExcessOutsideWork2_3(getExcessTime(excessOutsideTimeMap, 3, 2));
		toEntityExcessOutsideWork2_4(getExcessTime(excessOutsideTimeMap, 4, 2));
		toEntityExcessOutsideWork2_5(getExcessTime(excessOutsideTimeMap, 5, 2));
		toEntityExcessOutsideWork2_6(getExcessTime(excessOutsideTimeMap, 6, 2));
		toEntityExcessOutsideWork2_7(getExcessTime(excessOutsideTimeMap, 7, 2));
		toEntityExcessOutsideWork2_8(getExcessTime(excessOutsideTimeMap, 8, 2));
		toEntityExcessOutsideWork2_9(getExcessTime(excessOutsideTimeMap, 9, 2));
		toEntityExcessOutsideWork2_10(getExcessTime(excessOutsideTimeMap, 10, 2));
		toEntityExcessOutsideWork3_1(getExcessTime(excessOutsideTimeMap, 1, 3));
		toEntityExcessOutsideWork3_2(getExcessTime(excessOutsideTimeMap, 2, 3));
		toEntityExcessOutsideWork3_3(getExcessTime(excessOutsideTimeMap, 3, 3));
		toEntityExcessOutsideWork3_4(getExcessTime(excessOutsideTimeMap, 4, 3));
		toEntityExcessOutsideWork3_5(getExcessTime(excessOutsideTimeMap, 5, 3));
		toEntityExcessOutsideWork3_6(getExcessTime(excessOutsideTimeMap, 6, 3));
		toEntityExcessOutsideWork3_7(getExcessTime(excessOutsideTimeMap, 7, 3));
		toEntityExcessOutsideWork3_8(getExcessTime(excessOutsideTimeMap, 8, 3));
		toEntityExcessOutsideWork3_9(getExcessTime(excessOutsideTimeMap, 9, 3));
		toEntityExcessOutsideWork3_10(getExcessTime(excessOutsideTimeMap, 10, 3));
		toEntityExcessOutsideWork4_1(getExcessTime(excessOutsideTimeMap, 1, 4));
		toEntityExcessOutsideWork4_2(getExcessTime(excessOutsideTimeMap, 2, 4));
		toEntityExcessOutsideWork4_3(getExcessTime(excessOutsideTimeMap, 3, 4));
		toEntityExcessOutsideWork4_4(getExcessTime(excessOutsideTimeMap, 4, 4));
		toEntityExcessOutsideWork4_5(getExcessTime(excessOutsideTimeMap, 5, 4));
		toEntityExcessOutsideWork4_6(getExcessTime(excessOutsideTimeMap, 6, 4));
		toEntityExcessOutsideWork4_7(getExcessTime(excessOutsideTimeMap, 7, 4));
		toEntityExcessOutsideWork4_8(getExcessTime(excessOutsideTimeMap, 8, 4));
		toEntityExcessOutsideWork4_9(getExcessTime(excessOutsideTimeMap, 9, 4));
		toEntityExcessOutsideWork4_10(getExcessTime(excessOutsideTimeMap, 10, 4));
		toEntityExcessOutsideWork5_1(getExcessTime(excessOutsideTimeMap, 1, 5));
		toEntityExcessOutsideWork5_2(getExcessTime(excessOutsideTimeMap, 2, 5));
		toEntityExcessOutsideWork5_3(getExcessTime(excessOutsideTimeMap, 3, 5));
		toEntityExcessOutsideWork5_4(getExcessTime(excessOutsideTimeMap, 4, 5));
		toEntityExcessOutsideWork5_5(getExcessTime(excessOutsideTimeMap, 5, 5));
		toEntityExcessOutsideWork5_6(getExcessTime(excessOutsideTimeMap, 6, 5));
		toEntityExcessOutsideWork5_7(getExcessTime(excessOutsideTimeMap, 7, 5));
		toEntityExcessOutsideWork5_8(getExcessTime(excessOutsideTimeMap, 8, 5));
		toEntityExcessOutsideWork5_9(getExcessTime(excessOutsideTimeMap, 9, 5));
		toEntityExcessOutsideWork5_10(getExcessTime(excessOutsideTimeMap, 10, 5));
		
	}

	private ExcessOutsideWork getExcessTime(Map<Integer, ExcessOutSideWorkEachBreakdown> excessOutsideTimeMap,
			int breakdownNo, int excessNo) {
		ExcessOutsideWork excessOutsideTime = new ExcessOutsideWork(breakdownNo, excessNo);
		if (excessOutsideTimeMap.containsKey(breakdownNo)){
			Map<Integer, ExcessOutsideWork> breakdown = excessOutsideTimeMap.get(breakdownNo).getBreakdown();
			if (breakdown.containsKey(excessNo)){
				excessOutsideTime = breakdown.get(excessNo);
			}
		}
		return excessOutsideTime;
	}


	/* KRCDT_MON_AGGR_HDWK_TIME 10 */
	private void toEntityHolidayWorkTime1(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime1 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime1 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime1 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime1 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime1 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime1 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime1 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime2(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime2 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime2 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime2 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime2 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime2 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime2 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime2 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime3(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime3 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime3 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime3 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime3 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime3 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime3 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime3 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime4(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime4 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime4 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime4 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime4 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime4 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime4 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime4 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime5(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime5 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime5 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime5 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime5 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime5 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime5 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime5 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime6(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime6 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime6 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime6 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime6 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime6 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime6 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime6 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime7(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime7 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime7 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime7 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime7 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime7 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime7 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime7 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime8(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime8 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime8 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime8 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime8 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime8 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime8 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime8 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime9(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime9 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime9 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime9 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime9 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime9 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime9 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime9 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	private void toEntityHolidayWorkTime10(AggregateHolidayWorkTime domain) {
		this.holidayWorkTime10 = domain.getHolidayWorkTime() == null ? 0 : domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime10 = domain.getHolidayWorkTime() == null ? 0
				: domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime10 = domain.getBeforeHolidayWorkTime().v();
		this.transferTime10 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getTime().v();
		this.calcTransferTime10 = domain.getTransferTime() == null ? 0 : domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime10 = domain.getLegalHolidayWorkTime() == null ? 0
				: domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime10 = domain.getLegalTransferHolidayWorkTime() == null ? 0
				: domain.getLegalTransferHolidayWorkTime().v();
	}

	/* KRCDT_MON_AGGR_OVER_TIME 10 */
	private void toEntityOverTime1(AggregateOverTime domain) {
		this.overTime1 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime1 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime1 = domain.getBeforeOverTime().v();
		this.transferOverTime1 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime1 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime1 = domain.getLegalOverTime().v();
		this.legalTransferOverTime1 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime2(AggregateOverTime domain) {
		this.overTime2 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime2 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime2 = domain.getBeforeOverTime().v();
		this.transferOverTime2 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime2 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime2 = domain.getLegalOverTime().v();
		this.legalTransferOverTime2 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime3(AggregateOverTime domain) {
		this.overTime3 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime3 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime3 = domain.getBeforeOverTime().v();
		this.transferOverTime3 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime3 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime3 = domain.getLegalOverTime().v();
		this.legalTransferOverTime3 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime4(AggregateOverTime domain) {
		this.overTime4 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime4 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime4 = domain.getBeforeOverTime().v();
		this.transferOverTime4 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime4 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime4 = domain.getLegalOverTime().v();
		this.legalTransferOverTime4 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime5(AggregateOverTime domain) {
		this.overTime5 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime5 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime5 = domain.getBeforeOverTime().v();
		this.transferOverTime5 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime5 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime5 = domain.getLegalOverTime().v();
		this.legalTransferOverTime5 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime6(AggregateOverTime domain) {
		this.overTime6 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime6 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime6 = domain.getBeforeOverTime().v();
		this.transferOverTime6 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime6 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime6 = domain.getLegalOverTime().v();
		this.legalTransferOverTime6 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime7(AggregateOverTime domain) {
		this.overTime7 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime7 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime7 = domain.getBeforeOverTime().v();
		this.transferOverTime7 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime7 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime7 = domain.getLegalOverTime().v();
		this.legalTransferOverTime7 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime8(AggregateOverTime domain) {
		this.overTime8 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime8 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime8 = domain.getBeforeOverTime().v();
		this.transferOverTime8 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime8 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime8 = domain.getLegalOverTime().v();
		this.legalTransferOverTime8 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime9(AggregateOverTime domain) {
		this.overTime9 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime9 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime9 = domain.getBeforeOverTime().v();
		this.transferOverTime9 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime9 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime9 = domain.getLegalOverTime().v();
		this.legalTransferOverTime9 = domain.getLegalTransferOverTime().v();
	}

	private void toEntityOverTime10(AggregateOverTime domain) {
		this.overTime10 = domain.getOverTime() == null ? 0 : domain.getOverTime().getTime().v();
		this.calcOverTime10 = domain.getOverTime() == null ? 0 : domain.getOverTime().getCalcTime().v();
		this.beforeOverTime10 = domain.getBeforeOverTime().v();
		this.transferOverTime10 = domain.getTransferOverTime() == null ? 0 : domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime10 = domain.getTransferOverTime() == null ? 0
				: domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime10 = domain.getLegalOverTime().v();
		this.legalTransferOverTime10 = domain.getLegalTransferOverTime().v();
	}


	/* KRCDT_MON_AGGR_TOTAL_SPT */
	private void toEntityTotalTimeSpentAtWork(AggregateTotalTimeSpentAtWork domain) {
		this.overTimeSpentAtWork = domain == null ? 0 : domain.getOverTimeSpentAtWork().v();
		this.midnightTimeSpentAtWork = domain == null ? 0 : domain.getMidnightTimeSpentAtWork().v();
		this.holidayTimeSpentAtWork = domain == null ? 0 : domain.getHolidayTimeSpentAtWork().v();
		this.varienceTimeSpentAtWork = domain == null ? 0 : domain.getVarienceTimeSpentAtWork().v();
		this.totalTimeSpentAtWork = domain == null ? 0 : domain.getTotalTimeSpentAtWork().v();
	}

	/* KRCDT_MON_AGGR_TOTAL_WRK */
	private void toEntityTotalWorkingTime(AggregateTotalWorkingTime domain) {
		/** 就業時間 */
		WorkTimeOfMonthly workTime = domain.getWorkTime();
		/** 所定労働時間 */
		PrescribedWorkingTimeOfMonthly prescribedWorkingTime = domain.getPrescribedWorkingTime();
		/** 残業時間 */
		OverTimeOfMonthly overTime = domain.getOverTime();
		/** 休出時間 */
		HolidayWorkTimeOfMonthly holidayWorkTime = domain.getHolidayWorkTime();
		/** 休暇使用時間 */
		VacationUseTimeOfMonthly vacationUseTime = domain.getVacationUseTime();
		toEntityWorkTimeOfMonthly(workTime);
		toEntityPrescribedWorkingTimeOfMonthly(prescribedWorkingTime);
		toEntityOverTimeOfMonthly(overTime);
		toEntityHolidayWorkTimeOfMonthly(holidayWorkTime);
		toEntityVacationUseTimeOfMonth(vacationUseTime);
	}
	
	/** 就業時間 */
	private void toEntityWorkTimeOfMonthly(WorkTimeOfMonthly workTime) {
		this.workTime = workTime.getWorkTime().v();
		this.actualWorkTime = workTime.getActualWorkTime().v();
		this.withinPrescribedPremiumTime = workTime.getWithinPrescribedPremiumTime().v();
	}
	
	/** 所定労働時間 */
	private void toEntityPrescribedWorkingTimeOfMonthly(PrescribedWorkingTimeOfMonthly prescribedWorkingTime) {
		this.schedulePrescribedWorkingTime = prescribedWorkingTime.getSchedulePrescribedWorkingTime().v();
		this.recordPrescribedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime().v();
	}

	/* KRCDT_MON_ATTENDANCE_TIME */
	public void toEntityAttendanceTimeOfMonthly(AttendanceTimeOfMonthly domain,
			KrcdtMonTimeSup ouenEntity, KrcdtMonVerticalTotal verticalEntity) {
		
		this.startYmd = domain.getDatePeriod().start();
		this.endYmd = domain.getDatePeriod().end();
		this.aggregateDays = domain.getAggregateDays().v();
		
		/** 月の計算 */
		val monthlyCalculation = domain.getMonthlyCalculation();
		toEntityMonthlyCalculation(monthlyCalculation);
		
		/** 時間外超過 */
		val excessOutsideWork = domain.getExcessOutsideWork();
		toEntityExcessOutsideWorkMerge(excessOutsideWork);
		
		/** 縦計 */
		verticalEntity.entity(domain.getVerticalTotal());
		
		/** 回数集計 */
		val totalCount = domain.getTotalCount();
		toEntityTotalCount(totalCount.getTotalCountList());
		
		ouenEntity.setOuen(domain.getOuenTime());
		
		this.version = domain.getVersion();
	}
	
	public void resetAttendanceTime() {
		
		this.aggregateDays = 0;
		
		this.weeklyTotalPremiumTime = 0;
		this.monthlyTotalPremiumTime = 0;
		this.multiMonthIrregularMiddleTime = 0;
		this.irregularPeriodCarryforwardTime = 0;
		this.irregularWorkingShortageTime = 0;
		this.irregularLegalOverTime = 0;
		this.calcIrregularLegalOverTime = 0;
		
		this.flexTime = 0;
		this.calcFlexTime = 0;
		this.beforeFlexTime = 0;
		this.legalFlexTime = 0;
		this.illegalFlexTime = 0;
		this.flexExcessTime = 0;
		this.flexShortageTime = 0;
		this.flexCarryforwardWorkTime = 0;
		this.flexCarryforwardTime = 0;
		this.flexCarryforwardShortageTime = 0;
		this.excessFlexAtr = 0;
		this.principleTime = 0;
		this.forConvenienceTime = 0;
		this.annualLeaveDeductDays = 0;
		this.absenceDeductTime = 0;
		this.shotTimeBeforeDeduct = 0;
		this.flexSettleTime = 0;
		this.flexTimeCurrent = 0;
		this.standardTimeCurrent = 0;
		this.excessWeekAveTimeCurrent = 0;
		this.flexTimeCurrentOT = 0;
		this.standardTimeCurrentOT = 0;
		this.excessWeekAveTimeCurrentOT = 0;
		
		/** 法定労働時間 */
		this.statutoryWorkingTime = 0;
		
		/** 集計時間 */
		this.workTime = 0;
		this.actualWorkTime = 0;
		this.withinPrescribedPremiumTime = 0;
		this.schedulePrescribedWorkingTime = 0;
		this.recordPrescribedWorkingTime = 0;
		this.totalOverTime = 0;
		this.calcTotalOverTime = 0;
		this.beforeOverTime = 0;
		this.totalTransferOverTime = 0;
		this.calcTotalTransferOverTime = 0;
		
		/** 集計残業時間 */
		toEntityOverTime(new HashMap<>());
		
		this.totalHolidayWorkTime = 0;
		this.calcTotalHolidayWorkTime = 0;
		this.beforeHolidayWorkTime = 0;
		this.totalTransferTime = 0;
		this.calcTotalTransferTime = 0;
		toEntityAggregateHolidayWorkTime(new HashMap<>());
		
		/** 総労働時間 */
		this.totalWorkingTime = 0;
		
		/** 総拘束時間 */
		toEntityTotalTimeSpentAtWork(null);
		
//		/** 36協定時間 */
//		toEntityAgreementTimeOfMonthly(null);
//		
//		/** 36協定上限時間 */
//		toEntityAgreMaxTimeOfMonthly(null);
		
		/** 時間外超過 */
		toEntityExcessOutsideWorkMerge(null);
		
		/** 回数集計 */
		toEntityTotalCount(new HashMap<>());
	}
	
	/**
	 * 月別実績の月の計算
	 * MonthlyCalculation
	 */
	private void toEntityMonthlyCalculation(MonthlyCalculation monthlyCalculation) {

		/** 実働時間 */
		val actualWorkingTime = monthlyCalculation.getActualWorkingTime();
		/** フレックス時間 */
		val flexTime = monthlyCalculation.getFlexTime();
		
		toEntityRegAndIrreTimeOfMonth(actualWorkingTime);
		
		toEntityFlexTimeOfMonthly(flexTime);
		
		/** 法定労働時間 */
		this.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		
		/** 集計時間 */
		val aggregateTime = monthlyCalculation.getAggregateTime();
		toEntityTotalWorkingTime(aggregateTime);
		
		/** 総労働時間 */
		this.totalWorkingTime = monthlyCalculation.getTotalWorkingTime().v();
		
		/** 総拘束時間 */
		val totalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		toEntityTotalTimeSpentAtWork(totalTimeSpentAtWork);
		
//		/** 36協定時間 */
//		val agreementTime = monthlyCalculation.getAgreementTime();
//		toEntityAgreementTimeOfMonthly(agreementTime);
//		
//		/** 36協定上限時間 */
//		val agreMaxTime = monthlyCalculation.getAgreMaxTime();
//		toEntityAgreMaxTimeOfMonthly(agreMaxTime);
	}

	/* KRCDT_MON_FLEX_TIME */
	private void toEntityFlexTimeOfMonthly(FlexTimeOfMonthly domain) {

		val flexTime = domain.getFlexTime();
		val flexCarryForwardTime = domain.getFlexCarryforwardTime();
		val flexTimeOfExcessOutsideTime = domain.getFlexTimeOfExcessOutsideTime();
		val flexShortDeductTime = domain.getFlexShortDeductTime();

		this.flexTime = flexTime.getFlexTime().getTime().v();
		this.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		this.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		this.legalFlexTime = flexTime.getLegalFlexTime().v();
		this.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		this.flexExcessTime = domain.getFlexExcessTime().v();
		this.flexShortageTime = domain.getFlexShortageTime().v();
		this.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		this.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		this.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		this.flexNotCarryforwardTime = flexCarryForwardTime.getFlexNotCarryforwardTime().v();
		this.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		this.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		this.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		this.annualLeaveDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays().v();
		this.absenceDeductTime = flexShortDeductTime.getAbsenceDeductTime().v();
		this.shotTimeBeforeDeduct = flexShortDeductTime.getFlexShortTimeBeforeDeduct().v();
		this.flexSettleTime = domain.getFlexSettleTime().v();
		this.flexTimeCurrent = flexTime.getFlexTimeCurrentMonth().getFlexTime().v();
		this.standardTimeCurrent = flexTime.getFlexTimeCurrentMonth().getStandardTime().v();
		this.excessWeekAveTimeCurrent = flexTime.getFlexTimeCurrentMonth().getExcessWeekAveTime().v();
		this.flexTimeCurrentOT = flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth().getFlexTime().v();
		this.standardTimeCurrentOT = flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth().getStandardTime().v();
		this.excessWeekAveTimeCurrentOT = flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth().getExcessWeekAveTime().v();
	}

	/* KRCDT_MON_HDWK_TIME 集計時間：休出・代休：集計休出時間*/
	private void toEntityHolidayWorkTimeOfMonthly(HolidayWorkTimeOfMonthly domain) {
		/** 休出合計時間 */
		TimeMonthWithCalculation totalHolidayWorkTime = domain.getTotalHolidayWorkTime();
		/** 振替合計時間 */
		TimeMonthWithCalculation totalTransferTime = domain.getTotalTransferTime();
		/** 集計休出時間 */
		Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> aggregateHolidayWorkTimeMap = domain.getAggregateHolidayWorkTimeMap();
		
		this.totalHolidayWorkTime = totalHolidayWorkTime.getTime().v();
		this.calcTotalHolidayWorkTime = totalHolidayWorkTime.getCalcTime().v();
		this.beforeHolidayWorkTime = domain.getBeforeHolidayWorkTime().v();
		this.totalTransferTime = totalTransferTime.getTime().v();
		this.calcTotalTransferTime = totalTransferTime.getCalcTime().v();
		toEntityAggregateHolidayWorkTime(aggregateHolidayWorkTimeMap);
	}
	
	/** KRCDT_MON_AGGR_HDWK_TIME 10 **/
	private void toEntityAggregateHolidayWorkTime(Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> aggregateHolidayWorkTimeMap) {
		for (int i = 1; i <= 10; i++){
			HolidayWorkFrameNo frameNo = new HolidayWorkFrameNo(i);
			AggregateHolidayWorkTime aggrHolidayWorkTime = new AggregateHolidayWorkTime(frameNo);
			if (aggregateHolidayWorkTimeMap.containsKey(frameNo)){
				aggrHolidayWorkTime = aggregateHolidayWorkTimeMap.get(frameNo);
			}
			switch (i){
			case 1:
				toEntityHolidayWorkTime1(aggrHolidayWorkTime);
				break;
			case 2:
				toEntityHolidayWorkTime2(aggrHolidayWorkTime);
				break;
			case 3:
				toEntityHolidayWorkTime3(aggrHolidayWorkTime);
				break;
			case 4:
				toEntityHolidayWorkTime4(aggrHolidayWorkTime);
				break;
			case 5:
				toEntityHolidayWorkTime5(aggrHolidayWorkTime);
				break;
			case 6:
				toEntityHolidayWorkTime6(aggrHolidayWorkTime);
				break;
			case 7:
				toEntityHolidayWorkTime7(aggrHolidayWorkTime);
				break;
			case 8:
				toEntityHolidayWorkTime8(aggrHolidayWorkTime);
				break;
			case 9:
				toEntityHolidayWorkTime9(aggrHolidayWorkTime);
				break;
			case 10:
				toEntityHolidayWorkTime10(aggrHolidayWorkTime);
				break;
			}
		}
	}

	/* KRCDT_MON_OVER_TIME */
	private void toEntityOverTimeOfMonthly(OverTimeOfMonthly domain) {
		/** 残業合計時間 */
		TimeMonthWithCalculation totalOverTime = domain.getTotalOverTime();
		/** 振替残業合計時間 */
		TimeMonthWithCalculation totalTransferOverTime = domain.getTotalTransferOverTime();
		
		this.totalOverTime = totalOverTime.getTime().v();
		this.calcTotalOverTime = totalOverTime.getCalcTime().v();
		this.beforeOverTime = domain.getBeforeOverTime().v();
		this.totalTransferOverTime = totalTransferOverTime.getTime().v();
		this.calcTotalTransferOverTime = totalTransferOverTime.getCalcTime().v();
		
		/** 集計残業時間 */
		toEntityOverTime(domain.getAggregateOverTimeMap());
	}

	/* KRCDT_MON_REG_IRREG_TIME */
	private void toEntityRegAndIrreTimeOfMonth(RegularAndIrregularTimeOfMonthly domain) {

		val irregularWorkingTime = domain.getIrregularWorkingTime();

		this.weeklyTotalPremiumTime = domain.getWeeklyTotalPremiumTime().v();
		this.monthlyTotalPremiumTime = domain.getMonthlyTotalPremiumTime().v();
		this.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		this.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		this.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		this.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		this.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalcTime().v();
	}

	/* KRCDT_MON_VACT_USE_TIME */
	private void toEntityVacationUseTimeOfMonth(VacationUseTimeOfMonthly domain) {
		this.annualLeaveUseTime = domain.getAnnualLeave().getUseTime().v();
		this.retentionYearlyUseTime = domain.getRetentionYearly().getUseTime().v();
		this.specialHolidayUseTime = domain.getSpecialHoliday().getUseTime().v();
		this.compensatoryLeaveUseTime = domain.getCompensatoryLeave().getUseTime().v();
	}
	
	private void toEntityTotalCount(Map<Integer, TotalCount> totalCountList) {
		for (int i = 1; i <= 30; i++){
			TotalCount totalCount = new TotalCount(i);
			if (totalCountList.containsKey(i)){
				totalCount = totalCountList.get(i);
			}
			switch (i){
			case 1:
				this.toEntityTotalCount1(totalCount);
				break;
			case 2:
				this.toEntityTotalCount2(totalCount);
				break;
			case 3:
				this.toEntityTotalCount3(totalCount);
				break;
			case 4:
				this.toEntityTotalCount4(totalCount);
				break;
			case 5:
				this.toEntityTotalCount5(totalCount);
				break;
			case 6:
				this.toEntityTotalCount6(totalCount);
				break;
			case 7:
				this.toEntityTotalCount7(totalCount);
				break;
			case 8:
				this.toEntityTotalCount8(totalCount);
				break;
			case 9:
				this.toEntityTotalCount9(totalCount);
				break;
			case 10:
				this.toEntityTotalCount10(totalCount);
				break;
			case 11:
				this.toEntityTotalCount11(totalCount);
				break;
			case 12:
				this.toEntityTotalCount12(totalCount);
				break;
			case 13:
				this.toEntityTotalCount13(totalCount);
				break;
			case 14:
				this.toEntityTotalCount14(totalCount);
				break;
			case 15:
				this.toEntityTotalCount15(totalCount);
				break;
			case 16:
				this.toEntityTotalCount16(totalCount);
				break;
			case 17:
				this.toEntityTotalCount17(totalCount);
				break;
			case 18:
				this.toEntityTotalCount18(totalCount);
				break;
			case 19:
				this.toEntityTotalCount19(totalCount);
				break;
			case 20:
				this.toEntityTotalCount20(totalCount);
				break;
			case 21:
				this.toEntityTotalCount21(totalCount);
				break;
			case 22:
				this.toEntityTotalCount22(totalCount);
				break;
			case 23:
				this.toEntityTotalCount23(totalCount);
				break;
			case 24:
				this.toEntityTotalCount24(totalCount);
				break;
			case 25:
				this.toEntityTotalCount25(totalCount);
				break;
			case 26:
				this.toEntityTotalCount26(totalCount);
				break;
			case 27:
				this.toEntityTotalCount27(totalCount);
				break;
			case 28:
				this.toEntityTotalCount28(totalCount);
				break;
			case 29:
				this.toEntityTotalCount29(totalCount);
				break;
			case 30:
				this.toEntityTotalCount30(totalCount);
				break;
			}
		}
	}

	/* KRCDT_MON_EXCESS_OUTSIDE*/
	private void toEntityExcessOutsideWorkOfMonthly(ExcessOutsideWorkOfMonthly domain) {
		this.totalWeeklyPremiumTime1 = domain == null ? 0 : domain.getWeeklyTotalPremiumTime().v();
		this.totalMonthlyPremiumTime1 = domain == null ? 0 : domain.getMonthlyTotalPremiumTime().v();
		this.multiMonIrgmdlTime = domain == null ? 0 : domain.getDeformationCarryforwardTime().v();
	}	
	
	/* KRCDT_MON_EXCOUT_TIME 50 */
	private void toEntityExcessOutsideWork1_1(ExcessOutsideWork domain) {
		this.excessTime_1_1 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_2(ExcessOutsideWork domain) {
		this.excessTime_1_2 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_3(ExcessOutsideWork domain) {
		this.excessTime_1_3 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_4(ExcessOutsideWork domain) {
		this.excessTime_1_4 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_5(ExcessOutsideWork domain) {
		this.excessTime_1_5 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_6(ExcessOutsideWork domain) {
		this.excessTime_1_6 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_7(ExcessOutsideWork domain) {
		this.excessTime_1_7 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_8(ExcessOutsideWork domain) {
		this.excessTime_1_8 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_9(ExcessOutsideWork domain) {
		this.excessTime_1_9 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork1_10(ExcessOutsideWork domain) {
		this.excessTime_1_10 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_1(ExcessOutsideWork domain) {
		this.excessTime_2_1 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_2(ExcessOutsideWork domain) {
		this.excessTime_2_2 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_3(ExcessOutsideWork domain) {
		this.excessTime_2_3 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_4(ExcessOutsideWork domain) {
		this.excessTime_2_4 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_5(ExcessOutsideWork domain) {
		this.excessTime_2_5 = domain == null ? 0 : domain.getExcessTime().v();
	}
	private void toEntityExcessOutsideWork2_6(ExcessOutsideWork domain) {
		this.excessTime_2_6 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_7(ExcessOutsideWork domain) {
		this.excessTime_2_7 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_8(ExcessOutsideWork domain) {
		this.excessTime_2_8 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_9(ExcessOutsideWork domain) {
		this.excessTime_2_9 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork2_10(ExcessOutsideWork domain) {
		this.excessTime_2_10 = domain == null ? 0 : domain.getExcessTime().v();
	}	

	private void toEntityExcessOutsideWork3_1(ExcessOutsideWork domain) {
		this.excessTime_3_1 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_2(ExcessOutsideWork domain) {
		this.excessTime_3_2 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_3(ExcessOutsideWork domain) {
		this.excessTime_3_3 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_4(ExcessOutsideWork domain) {
		this.excessTime_3_4 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_5(ExcessOutsideWork domain) {
		this.excessTime_3_5 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_6(ExcessOutsideWork domain) {
		this.excessTime_3_6 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_7(ExcessOutsideWork domain) {
		this.excessTime_3_7 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_8(ExcessOutsideWork domain) {
		this.excessTime_3_8 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_9(ExcessOutsideWork domain) {
		this.excessTime_3_9 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork3_10(ExcessOutsideWork domain) {
		this.excessTime_3_10 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_1(ExcessOutsideWork domain) {
		this.excessTime_4_1 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_2(ExcessOutsideWork domain) {
		this.excessTime_4_2 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_3(ExcessOutsideWork domain) {
		this.excessTime_4_3 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_4(ExcessOutsideWork domain) {
		this.excessTime_4_4 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_5(ExcessOutsideWork domain) {
		this.excessTime_4_5 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_6(ExcessOutsideWork domain) {
		this.excessTime_4_6 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_7(ExcessOutsideWork domain) {
		this.excessTime_4_7 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_8(ExcessOutsideWork domain) {
		this.excessTime_4_8 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_9(ExcessOutsideWork domain) {
		this.excessTime_4_9 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork4_10(ExcessOutsideWork domain) {
		this.excessTime_4_10 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_1(ExcessOutsideWork domain) {
		this.excessTime_5_1 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_2(ExcessOutsideWork domain) {
		this.excessTime_5_2 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_3(ExcessOutsideWork domain) {
		this.excessTime_5_3 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_4(ExcessOutsideWork domain) {
		this.excessTime_5_4 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_5(ExcessOutsideWork domain) {
		this.excessTime_5_5 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_6(ExcessOutsideWork domain) {
		this.excessTime_5_6 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_7(ExcessOutsideWork domain) {
		this.excessTime_5_7 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_8(ExcessOutsideWork domain) {
		this.excessTime_5_8 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_9(ExcessOutsideWork domain) {
		this.excessTime_5_9 = domain == null ? 0 : domain.getExcessTime().v();
	}

	private void toEntityExcessOutsideWork5_10(ExcessOutsideWork domain) {
		this.excessTime_5_10 = domain == null ? 0 : domain.getExcessTime().v();
	}

	/* KRCDT_MON_AGREEMENT_TIME */
//	private void toEntityAgreementTimeOfMonthly(AgreementTimeOfMonthly domain) {
//		this.agreementTime = domain == null ? 0 : domain.getAgreementTime().v();
//		this.limitErrorTime = domain == null ? 0 : domain.getLimitErrorTime().v();
//		this.limitAlarmTime = domain == null ? 0 : domain.getLimitAlarmTime().v();
//		this.exceptionLimitErrorTime = (domain != null && domain.getExceptionLimitErrorTime().isPresent()
//				? domain.getExceptionLimitErrorTime().get().v()
//				: null);
//		this.exceptionLimitAlarmTime = (domain != null && domain.getExceptionLimitAlarmTime().isPresent()
//				? domain.getExceptionLimitAlarmTime().get().v()
//				: null);
//		this.status = domain == null ? 0 : domain.getStatus().value;
//	}
	
//	private void toEntityAgreMaxTimeOfMonthly(AgreMaxTimeOfMonthly domain) {
//		this.agreementRegTime = domain == null ? 0 : domain.getAgreementTime().v();
//	}

	/* KRCDT_MON_AFFILIATION */
	public void toEntityAffiliationInfoOfMonthly(AffiliationInfoOfMonthly domain) {

		this.firstEmploymentCd = domain.getFirstInfo().getEmploymentCd().v();
		this.firstWorkplaceId = domain.getFirstInfo().getWorkplaceId().v();
		this.firstJobTitleId = domain.getFirstInfo().getJobTitleId().v();
		this.firstClassCd = domain.getFirstInfo().getClassCd().v();
		this.firstBusinessTypeCd = domain.getFirstInfo().getBusinessTypeCd().map(c -> c.v()).orElse(null);
		this.lastEmploymentCd = domain.getLastInfo().getEmploymentCd().v();
		this.lastWorkplaceId = domain.getLastInfo().getWorkplaceId().v();
		this.lastJobTitleId = domain.getLastInfo().getJobTitleId().v();
		this.lastClassCd = domain.getLastInfo().getClassCd().v();
		this.lastBusinessTypeCd = domain.getLastInfo().getBusinessTypeCd().map(c -> c.v()).orElse(null);
		
		this.version = domain.getVersion();
	}
	
	public void resetAffiliationInfo() {

		this.firstEmploymentCd = "";
		this.firstWorkplaceId = "";
		this.firstJobTitleId = "";
		this.firstClassCd = "";
		this.firstBusinessTypeCd = "";
		this.lastEmploymentCd = "";
		this.lastWorkplaceId = "";
		this.lastJobTitleId = "";
		this.lastClassCd = "";
		this.lastBusinessTypeCd = "";
	}

	/**
	 * ドメインに変換
	 * @return 集計休出時間
	 */
	private AggregateHolidayWorkTime toDomainHolidayWorkTime1() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(1),
		new TimeMonthWithCalculation(
		new AttendanceTimeMonth(this.holidayWorkTime1),
		new AttendanceTimeMonth(this.calcHolidayWorkTime1)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime1),
		new TimeMonthWithCalculation(
		new AttendanceTimeMonth(this.transferTime1),
		new AttendanceTimeMonth(this.calcTransferTime1)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime1),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime1));
	}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime2() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(2),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime2),
			new AttendanceTimeMonth(this.calcHolidayWorkTime2)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime2),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime2),
			new AttendanceTimeMonth(this.calcTransferTime2)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime2),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime2));
	}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime3() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(3),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime3),
			new AttendanceTimeMonth(this.calcHolidayWorkTime3)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime3),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime3),
			new AttendanceTimeMonth(this.calcTransferTime3)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime3),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime3));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime4() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(4),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime4),
			new AttendanceTimeMonth(this.calcHolidayWorkTime4)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime4),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime4),
			new AttendanceTimeMonth(this.calcTransferTime4)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime4),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime4));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime5() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(5),
		new TimeMonthWithCalculation(
		new AttendanceTimeMonth(this.holidayWorkTime5),
			new AttendanceTimeMonth(this.calcHolidayWorkTime5)),
			new AttendanceTimeMonth(this.beforeHolidayWorkTime5),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime5),
			new AttendanceTimeMonth(this.calcTransferTime5)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime5),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime5));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime6() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(6),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime6),
			new AttendanceTimeMonth(this.calcHolidayWorkTime6)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime6),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime6),
			new AttendanceTimeMonth(this.calcTransferTime6)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime6),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime6));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime7() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(7),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime7),
			new AttendanceTimeMonth(this.calcHolidayWorkTime7)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime7),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime7),
			new AttendanceTimeMonth(this.calcTransferTime7)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime7),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime7));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime8() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(8),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime8),
			new AttendanceTimeMonth(this.calcHolidayWorkTime8)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime8),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime8),
			new AttendanceTimeMonth(this.calcTransferTime8)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime8),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime8));
		}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime9() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(9),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime9),
			new AttendanceTimeMonth(this.calcHolidayWorkTime9)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime9),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime9),
			new AttendanceTimeMonth(this.calcTransferTime9)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime9),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime9));
	}

	private AggregateHolidayWorkTime toDomainHolidayWorkTime10() {
		return AggregateHolidayWorkTime.of(
		new HolidayWorkFrameNo(10),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.holidayWorkTime10),
			new AttendanceTimeMonth(this.calcHolidayWorkTime10)),
		new AttendanceTimeMonth(this.beforeHolidayWorkTime10),
		new TimeMonthWithCalculation(
			new AttendanceTimeMonth(this.transferTime10),
			new AttendanceTimeMonth(this.calcTransferTime10)),
		new AttendanceTimeMonth(this.legalHolidayWorkTime10),
		new AttendanceTimeMonth(this.legalTransferHolidayWorkTime10));
	}
	
	/** KRCDT_MON_AGGR_OVER_TIME 10 **/
	/**
	 * ドメインに変換
	 * @return 集計残業時間
	 */
	private AggregateOverTime toDomainOverTime1() {
		return AggregateOverTime.of(new OverTimeFrameNo(1),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime1), 
						new AttendanceTimeMonth(this.calcOverTime1)),
				new AttendanceTimeMonth(this.beforeOverTime1),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime1),
						new AttendanceTimeMonth(this.calcTransferOverTime1)),
				new AttendanceTimeMonth(this.legalOverTime1), 
				new AttendanceTimeMonth(this.legalTransferOverTime1));
	}

	private AggregateOverTime toDomainOverTime2() {
		return AggregateOverTime.of(new OverTimeFrameNo(2),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime2), 
						new AttendanceTimeMonth(this.calcOverTime2)),
				new AttendanceTimeMonth(this.beforeOverTime2),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime2),
						new AttendanceTimeMonth(this.calcTransferOverTime2)),
				new AttendanceTimeMonth(this.legalOverTime2), 
				new AttendanceTimeMonth(this.legalTransferOverTime2));
	}

	private AggregateOverTime toDomainOverTime3() {
		return AggregateOverTime.of(new OverTimeFrameNo(3),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime3), 
						new AttendanceTimeMonth(this.calcOverTime3)),
				new AttendanceTimeMonth(this.beforeOverTime3),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime3),
						new AttendanceTimeMonth(this.calcTransferOverTime3)),
				new AttendanceTimeMonth(this.legalOverTime3), 
				new AttendanceTimeMonth(this.legalTransferOverTime3));
	}

	private AggregateOverTime toDomainOverTime4() {
		return AggregateOverTime.of(new OverTimeFrameNo(4),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime4), 
						new AttendanceTimeMonth(this.calcOverTime4)),
				new AttendanceTimeMonth(this.beforeOverTime4),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime4),
						new AttendanceTimeMonth(this.calcTransferOverTime4)),
				new AttendanceTimeMonth(this.legalOverTime4), 
				new AttendanceTimeMonth(this.legalTransferOverTime4));
	}

	private AggregateOverTime toDomainOverTime5() {
		return AggregateOverTime.of(new OverTimeFrameNo(5),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime5), 
						new AttendanceTimeMonth(this.calcOverTime5)),
				new AttendanceTimeMonth(this.beforeOverTime5),
				new TimeMonthWithCalculation(new AttendanceTimeMonth(this.transferOverTime5),
						new AttendanceTimeMonth(this.calcTransferOverTime5)),
				new AttendanceTimeMonth(this.legalOverTime5),
				new AttendanceTimeMonth(this.legalTransferOverTime5));
	}

	private AggregateOverTime toDomainOverTime6() {
		return AggregateOverTime.of(new OverTimeFrameNo(6),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime6), 
						new AttendanceTimeMonth(this.calcOverTime6)),
				new AttendanceTimeMonth(this.beforeOverTime6),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime6),
						new AttendanceTimeMonth(this.calcTransferOverTime6)),
				new AttendanceTimeMonth(this.legalOverTime6), 
				new AttendanceTimeMonth(this.legalTransferOverTime6));
	}

	private AggregateOverTime toDomainOverTime7() {
		return AggregateOverTime.of(new OverTimeFrameNo(7),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime7), 
						new AttendanceTimeMonth(this.calcOverTime7)),
				new AttendanceTimeMonth(this.beforeOverTime7),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime7),
						new AttendanceTimeMonth(this.calcTransferOverTime7)),
				new AttendanceTimeMonth(this.legalOverTime7), 
				new AttendanceTimeMonth(this.legalTransferOverTime7));
	}

	private AggregateOverTime toDomainOverTime8() {
		return AggregateOverTime.of(new OverTimeFrameNo(8),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime8), 
						new AttendanceTimeMonth(this.calcOverTime8)),
				new AttendanceTimeMonth(this.beforeOverTime8),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime8),
						new AttendanceTimeMonth(this.calcTransferOverTime8)),
				new AttendanceTimeMonth(this.legalOverTime8), 
				new AttendanceTimeMonth(this.legalTransferOverTime8));
	}

	private AggregateOverTime toDomainOverTime9() {
		return AggregateOverTime.of(new OverTimeFrameNo(9),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime9), 
						new AttendanceTimeMonth(this.calcOverTime9)),
				new AttendanceTimeMonth(this.beforeOverTime9),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime9),
						new AttendanceTimeMonth(this.calcTransferOverTime9)),
				new AttendanceTimeMonth(this.legalOverTime9), 
				new AttendanceTimeMonth(this.legalTransferOverTime9));
	}

	private AggregateOverTime toDomainOverTime10() {
		return AggregateOverTime.of(new OverTimeFrameNo(10),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime10), 
						new AttendanceTimeMonth(this.calcOverTime10)),
				new AttendanceTimeMonth(this.beforeOverTime10),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime10),
						new AttendanceTimeMonth(this.calcTransferOverTime10)),
				new AttendanceTimeMonth(this.legalOverTime10),
				new AttendanceTimeMonth(this.legalTransferOverTime10));
	}
	
	/** KRCDT_MON_AGGR_TOTAL_SPT **/
	
	/**
	 * ドメインに変換
	 * @return 集計総拘束時間
	 */
	private AggregateTotalTimeSpentAtWork toDomainTotalTimeSpentAtWork(){
		
		return AggregateTotalTimeSpentAtWork.of(
				new AttendanceTimeMonth(this.overTimeSpentAtWork),
				new AttendanceTimeMonth(this.midnightTimeSpentAtWork),
				new AttendanceTimeMonth(this.holidayTimeSpentAtWork),
				new AttendanceTimeMonth(this.varienceTimeSpentAtWork),
				new AttendanceTimeMonth(this.totalTimeSpentAtWork));
	}
	
	/** KRCDT_MON_AGGR_TOTAL_WRK **/
	
	/**
	 * ドメインに変換
	 * @param krcdtMonOverTime 月別実績の残業時間
	 * @param krcdtMonAggrOverTimes 集計残業時間
	 * @param krcdtMonHdwkTime 月別実績の休出時間
	 * @param krcdtMonAggrHdwkTimes 集計休出時間
	 * @param krcdtMonVactUseTime 月別実績の休暇使用時間
	 * @return 集計総労働時間
	 */
	private AggregateTotalWorkingTime toDomainTotalWorkingTime(){
		
		// 月別実績の就業時間
		val workTime = WorkTimeOfMonthly.of(
				new AttendanceTimeMonth(this.workTime),
				new AttendanceTimeMonth(this.withinPrescribedPremiumTime),
				new AttendanceTimeMonth(this.actualWorkTime));

		// 月別実績の残業時間
		OverTimeOfMonthly overTime = toDomainOverTimeOfMonthly();
		
		// 月別実績の休出時間
		HolidayWorkTimeOfMonthly holidayWorkTime = toDomainHolidayWorkTimeOfMonthly();
		
		// 月別実績の休暇使用時間
		VacationUseTimeOfMonthly vacationUseTime = toDomainVacationUseTimeOfMonthly();
		
		// 月別実績の所定労働時間
		val prescribedWorkingTime = PrescribedWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(this.schedulePrescribedWorkingTime),
				new AttendanceTimeMonth(this.recordPrescribedWorkingTime));
		// 集計総労働時間
		return AggregateTotalWorkingTime.of(
				workTime,
				overTime,
				holidayWorkTime,
				vacationUseTime,
				prescribedWorkingTime);
	}
	
	
	private MonthlyCalculation toDomainMonthlyCalculation() {
		// 月別実績の通常変形時間
		RegularAndIrregularTimeOfMonthly regAndIrgTime = toDomainRegularAndIrregularTimeOfMonthly();
		
		// 月別実績のフレックス時間
		FlexTimeOfMonthly flexTime = toDomainFlexTimeOfMonthly();
		
		// 集計総労働時間
		AggregateTotalWorkingTime aggregateTotalWorkingTime = toDomainTotalWorkingTime();
		
		// 集計総拘束時間
		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpent = toDomainTotalTimeSpentAtWork();
		
		// 月別実績の36協定時間
//		AgreementTimeOfMonthly agreementTime =  toDomainAgreementTimeOfMonthly();
		
//		// 月別実績の36協定上限時間
//		AgreMaxTimeOfMonthly agreMaxTime = toDomainAgreMaxTimeOfMonthly();
		
		// 月別実績の月の計算
		return  MonthlyCalculation.of(
				regAndIrgTime, 
				flexTime, 
				new AttendanceTimeMonth(this.statutoryWorkingTime),
				aggregateTotalWorkingTime,
				new AttendanceTimeMonth(this.totalWorkingTime), 
				aggregateTotalTimeSpent);
	}
	
	private List<TotalCount> getTotalCounts(){
		List<TotalCount> lst = new ArrayList<>();
		lst.add(toDomainTotalCount(1, this.totalCountDays1, this.totalCountTime1));
		lst.add(toDomainTotalCount(2, this.totalCountDays2, this.totalCountTime2));
		lst.add(toDomainTotalCount(3, this.totalCountDays3, this.totalCountTime3));
		lst.add(toDomainTotalCount(4, this.totalCountDays4, this.totalCountTime4));
		lst.add(toDomainTotalCount(5, this.totalCountDays5, this.totalCountTime5));
		lst.add(toDomainTotalCount(6, this.totalCountDays6, this.totalCountTime6));
		lst.add(toDomainTotalCount(7, this.totalCountDays7, this.totalCountTime7));
		lst.add(toDomainTotalCount(8, this.totalCountDays8, this.totalCountTime8));
		lst.add(toDomainTotalCount(9, this.totalCountDays9, this.totalCountTime9));
		lst.add(toDomainTotalCount(10, this.totalCountDays10, this.totalCountTime10	));
		lst.add(toDomainTotalCount(11, this.totalCountDays11, this.totalCountTime11	));
		lst.add(toDomainTotalCount(12, this.totalCountDays12, this.totalCountTime12	));
		lst.add(toDomainTotalCount(13, this.totalCountDays13, this.totalCountTime13	));
		lst.add(toDomainTotalCount(14, this.totalCountDays14, this.totalCountTime14	));
		lst.add(toDomainTotalCount(15, this.totalCountDays15, this.totalCountTime15	));
		lst.add(toDomainTotalCount(16, this.totalCountDays16, this.totalCountTime16	));
		lst.add(toDomainTotalCount(17, this.totalCountDays17, this.totalCountTime17	));
		lst.add(toDomainTotalCount(18, this.totalCountDays18, this.totalCountTime18	));
		lst.add(toDomainTotalCount(19, this.totalCountDays19, this.totalCountTime19	));
		lst.add(toDomainTotalCount(20, this.totalCountDays20, this.totalCountTime20	));
		lst.add(toDomainTotalCount(21, this.totalCountDays21, this.totalCountTime21	));
		lst.add(toDomainTotalCount(22, this.totalCountDays22, this.totalCountTime22	));
		lst.add(toDomainTotalCount(23, this.totalCountDays23, this.totalCountTime23	));
		lst.add(toDomainTotalCount(24, this.totalCountDays24, this.totalCountTime24	));
		lst.add(toDomainTotalCount(25, this.totalCountDays25, this.totalCountTime25	));
		lst.add(toDomainTotalCount(26, this.totalCountDays26, this.totalCountTime26	));
		lst.add(toDomainTotalCount(27, this.totalCountDays27, this.totalCountTime27	));
		lst.add(toDomainTotalCount(28, this.totalCountDays28, this.totalCountTime28	));
		lst.add(toDomainTotalCount(29, this.totalCountDays29, this.totalCountTime29	));
		lst.add(toDomainTotalCount(30, this.totalCountDays30, this.totalCountTime30	));
		return lst;
	}
	
	/** KRCDT_MON_ATTENDANCE_TIME **/
	/**
	 * ドメインに変換
	 * @return 月別実績の勤怠時間
	 */
	public AttendanceTimeOfMonthly toDomainAttendanceTimeOfMonthly(KrcdtMonTimeSup ouen, KrcdtMonVerticalTotal vertical) {
		
		// 月別実績の月の計算
		MonthlyCalculation monthlyCalculation = toDomainMonthlyCalculation();
		
		// 月別実績の時間外超過
		ExcessOutsideWorkOfMonthly excessOutsideWork = toDomainExcessOutsideWorkOfMonthly();
		
		// TODO:LamVT-HERE ----------------------------()()()()()()()()()()-----------
		
		// 期間別の回数集計
		TotalCountByPeriod totalCount = toDomainTotalCountByPeriod(this.getTotalCounts());
		
		AttendanceTimeOfMonthly domain = AttendanceTimeOfMonthly.of(
				this.krcdtMonMergePk.getEmployeeId(),
				new YearMonth(this.krcdtMonMergePk.getYearMonth()),
				ClosureId.valueOf(this.krcdtMonMergePk.getClosureId()),
				new ClosureDate(this.krcdtMonMergePk.getClosureDay(), (this.krcdtMonMergePk.getIsLastDay() != 0)),
				new DatePeriod(this.startYmd, this.endYmd),
				monthlyCalculation,
				excessOutsideWork,
				vertical == null ? new VerticalTotalOfMonthly() : vertical.domain(),
				totalCount,
				new AttendanceDaysMonth(this.aggregateDays),
				ouen == null ? OuenTimeOfMonthly.empty() : ouen.convertToOuen());
		
		domain.setVersion(this.version);
		
		return domain;
	}
	
	private TotalCountByPeriod toDomainTotalCountByPeriod(List<TotalCount> totalCounts) {
		TotalCountByPeriod totalCountPeriod = new TotalCountByPeriod();
		
		if (totalCounts != null){
			totalCountPeriod = TotalCountByPeriod.of(totalCounts);
		}
		
		return totalCountPeriod;
	}
	
	/** KRCDT_MON_FLEX_TIME **/
	/**
	 * ドメインに変換
	 * @return 月別実績のフレックス時間
	 */
	private FlexTimeOfMonthly toDomainFlexTimeOfMonthly(){
		
		return FlexTimeOfMonthly.of(
				FlexTime.of(
						new TimeMonthWithCalculationAndMinus(
								new AttendanceTimeMonthWithMinus(this.flexTime),
								new AttendanceTimeMonthWithMinus(this.calcFlexTime)),
						new AttendanceTimeMonth(this.beforeFlexTime),
						new AttendanceTimeMonthWithMinus(this.legalFlexTime),
						new AttendanceTimeMonthWithMinus(this.illegalFlexTime),
						FlexTimeCurrentMonth.of(
								new AttendanceTimeMonthWithMinus(this.flexTimeCurrent),
								new AttendanceTimeMonth(this.standardTimeCurrent),
								new AttendanceTimeMonth(this.excessWeekAveTimeCurrent))),
				new AttendanceTimeMonth(this.flexExcessTime),
				new AttendanceTimeMonth(this.flexShortageTime),
				FlexCarryforwardTime.of(
						new AttendanceTimeMonthWithMinus(this.flexCarryforwardTime),
						new AttendanceTimeMonth(this.flexCarryforwardWorkTime),
						new AttendanceTimeMonth(this.flexCarryforwardShortageTime),
						new AttendanceTimeMonth(this.flexNotCarryforwardTime)),
				FlexTimeOfExcessOutsideTime.of(
						EnumAdaptor.valueOf(this.excessFlexAtr, ExcessFlexAtr.class),
						new AttendanceTimeMonth(this.principleTime),
						new AttendanceTimeMonth(this.forConvenienceTime),
						FlexTimeCurrentMonth.of(
								new AttendanceTimeMonthWithMinus(this.flexTimeCurrentOT),
								new AttendanceTimeMonth(this.standardTimeCurrentOT),
								new AttendanceTimeMonth(this.excessWeekAveTimeCurrentOT))),
				FlexShortDeductTime.of(
						new AttendanceDaysMonth(this.annualLeaveDeductDays),
						new AttendanceTimeMonth(this.absenceDeductTime),
						new AttendanceTimeMonth(this.shotTimeBeforeDeduct)),
				new AttendanceTimeMonthWithMinus(this.flexSettleTime));
	}
	
	/** KRCDT_MON_HDWK_TIME **/
	/**
	 * ドメインに変換
	 * @param krcdtMonAggrHdwkTimes 集計休出時間
	 * @return 月別実績の休出時間
	 */
	private HolidayWorkTimeOfMonthly toDomainHolidayWorkTimeOfMonthly(){
		List<AggregateHolidayWorkTime> aggrHolidayWorkTimeLst = this.getAggrHolidayWorkTimeLst();
		
		return HolidayWorkTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalHolidayWorkTime),
						new AttendanceTimeMonth(this.calcTotalHolidayWorkTime)),
				new AttendanceTimeMonth(this.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalTransferTime),
						new AttendanceTimeMonth(this.calcTotalTransferTime)),
				aggrHolidayWorkTimeLst);
	}
	
	private List<AggregateHolidayWorkTime> getAggrHolidayWorkTimeLst(){
		List<AggregateHolidayWorkTime> aggrHolidayWorkTimeLst = new ArrayList<>();
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime1());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime2());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime3());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime4());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime5());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime6());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime7());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime8());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime9());
		aggrHolidayWorkTimeLst.add(this.toDomainHolidayWorkTime10());

		return aggrHolidayWorkTimeLst;
	}	
	
	/** KRCDT_MON_OVER_TIME **/
	/**
	 * ドメインに変換
	 * @param krcdtMonAggrOverTimes 集計残業時間
	 * @return 月別実績の残業時間
	 */
	private OverTimeOfMonthly toDomainOverTimeOfMonthly(){
		List<AggregateOverTime> overTimeList = this.getOverTimeLst();	
		return OverTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalOverTime),
						new AttendanceTimeMonth(this.calcTotalOverTime)),
				new AttendanceTimeMonth(this.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalTransferOverTime),
						new AttendanceTimeMonth(this.calcTotalTransferOverTime)),
				overTimeList);
	}
	
	private  List<AggregateOverTime> getOverTimeLst(){
		List<AggregateOverTime> overTimeList = new ArrayList<>();
		overTimeList.add(this.toDomainOverTime1());
		overTimeList.add(this.toDomainOverTime2());
		overTimeList.add(this.toDomainOverTime3());
		overTimeList.add(this.toDomainOverTime4());
		overTimeList.add(this.toDomainOverTime5());
		overTimeList.add(this.toDomainOverTime6());
		overTimeList.add(this.toDomainOverTime7());
		overTimeList.add(this.toDomainOverTime8());
		overTimeList.add(this.toDomainOverTime9());
		overTimeList.add(this.toDomainOverTime10());
		return overTimeList;
	}
	
	/** KRCDT_MON_REG_IRREG_TIME **/
	/**
	 * ドメインに変換
	 * @return 月別実績の通常変形時間
	 */
	private RegularAndIrregularTimeOfMonthly toDomainRegularAndIrregularTimeOfMonthly(){
		
		// 月別実績の変形労働時間
		val irregularWorkingTime = IrregularWorkingTimeOfMonthly.of(
				new AttendanceTimeMonthWithMinus(this.multiMonthIrregularMiddleTime),
				new AttendanceTimeMonthWithMinus(this.irregularPeriodCarryforwardTime),
				new AttendanceTimeMonth(this.irregularWorkingShortageTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.irregularLegalOverTime),
						new AttendanceTimeMonth(this.calcIrregularLegalOverTime)));
		
		return RegularAndIrregularTimeOfMonthly.of(
				new AttendanceTimeMonth(this.weeklyTotalPremiumTime),
				new AttendanceTimeMonth(this.monthlyTotalPremiumTime),
				irregularWorkingTime);
	}
	
	/** KRCDT_MON_VACT_USE_TIME **/
	/**
	 * ドメインに変換
	 * @return 月別実績の休暇使用時間
	 */
	private VacationUseTimeOfMonthly toDomainVacationUseTimeOfMonthly(){
		
		return VacationUseTimeOfMonthly.of(
				AnnualLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.annualLeaveUseTime)),
				RetentionYearlyUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.retentionYearlyUseTime)),
				SpecialHolidayUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.specialHolidayUseTime)),
				CompensatoryLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.compensatoryLeaveUseTime)));
	}
	
	/** KRCDT_MON_EXCESS_OUTSIDE 50 **/
	/**
	 * ドメインに変換
	 * @param krcdtMonExcoutTime 時間外超過
	 * @return 月別実績の時間外超過
	 */
	
	private List<ExcessOutsideWork> getExcessOutsideWorkLst(){
		List<ExcessOutsideWork> excessOutsideWork = new ArrayList<>();
		
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 1, this.excessTime_1_1));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 2, this.excessTime_1_2));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 3, this.excessTime_1_3));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 4, this.excessTime_1_4));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 5, this.excessTime_1_5));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 6, this.excessTime_1_6));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 7, this.excessTime_1_7));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 8, this.excessTime_1_8));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 9, this.excessTime_1_9));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(1, 10, this.excessTime_1_10));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 1, this.excessTime_2_1));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 2, this.excessTime_2_2));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 3, this.excessTime_2_3));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 4, this.excessTime_2_4));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 5, this.excessTime_2_5));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 6, this.excessTime_2_6));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 7, this.excessTime_2_7));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 8, this.excessTime_2_8));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 9, this.excessTime_2_9));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(2, 10, this.excessTime_2_10));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 1, this.excessTime_3_1));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 2, this.excessTime_3_2));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 3, this.excessTime_3_3));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 4, this.excessTime_3_4));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 5, this.excessTime_3_5));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 6, this.excessTime_3_6));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 7, this.excessTime_3_7));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 8, this.excessTime_3_8));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 9, this.excessTime_3_9));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(3, 10, this.excessTime_3_10));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 1, this.excessTime_4_1));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 2, this.excessTime_4_2));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 3, this.excessTime_4_3));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 4, this.excessTime_4_4));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 5, this.excessTime_4_5));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 6, this.excessTime_4_6));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 7, this.excessTime_4_7));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 8, this.excessTime_4_8));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 9, this.excessTime_4_9));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(4, 10, this.excessTime_4_10));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 1, this.excessTime_5_1));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 2, this.excessTime_5_2));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 3, this.excessTime_5_3));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 4, this.excessTime_5_4));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 5, this.excessTime_5_5));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 6, this.excessTime_5_6));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 7, this.excessTime_5_7));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 8, this.excessTime_5_8));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 9, this.excessTime_5_9));
		excessOutsideWork.add(this.toDomainExcessOutsideWorkXX(5, 10, this.excessTime_5_10));
		
		return excessOutsideWork;
	}
	
	private ExcessOutsideWorkOfMonthly toDomainExcessOutsideWorkOfMonthly(){
		List<ExcessOutsideWork> excessOutsideWork = this.getExcessOutsideWorkLst();		
		return ExcessOutsideWorkOfMonthly.of(
				new AttendanceTimeMonth(this.totalWeeklyPremiumTime1),
				new AttendanceTimeMonth(this.totalMonthlyPremiumTime1),
				new AttendanceTimeMonthWithMinus(this.multiMonIrgmdlTime),
				excessOutsideWork);
	}

	/** KRCDT_MON_EXCOUT_TIME **/
	/**
	 * ドメインに変換
	 * @return 時間外超過
	 */
	private ExcessOutsideWork toDomainExcessOutsideWorkXX(int excessNo, int breakdownNo, int excessTime) {
		return ExcessOutsideWork.of(breakdownNo, excessNo, new AttendanceTimeMonth(excessTime));
	}
	
	/** KRCDT_MON_AGREEMENT_TIME **/
	/**
	 * ドメインに変換
	 * @return 月別実績の36協定時間
	 */
//	private AgreementTimeOfMonthly toDomainAgreementTimeOfMonthly(){
//		
//		return AgreementTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.agreementTime),
//				new LimitOneMonth(this.limitErrorTime),
//				new LimitOneMonth(this.limitAlarmTime),
//		(this.exceptionLimitErrorTime == null ?
//						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitErrorTime))),
//		(this.exceptionLimitAlarmTime == null ?
//						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitAlarmTime))),
//				EnumAdaptor.valueOf(this.status, AgreementTimeStatusOfMonthly.class));
//	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績の36協定上限時間
	 */
//	private AgreMaxTimeOfMonthly toDomainAgreMaxTimeOfMonthly() {
//		
//		return AgreMaxTimeOfMonthly.of(
//				new AttendanceTimeMonth(this.agreementRegTime),
//				new LimitOneMonth(0),
//				AgreMaxTimeStatusOfMonthly.NORMAL);
//	}
	
	/** KRCDT_MON_AFFILIATION **/
	/**
	 * ドメインに変換
	 * @return 月別実績の所属情報
	 */
	public AffiliationInfoOfMonthly toDomainAffiliationInfoOfMonthly(){
		
		AffiliationInfoOfMonthly domain = AffiliationInfoOfMonthly.of(
				this.krcdtMonMergePk.getEmployeeId(),
				new YearMonth(this.krcdtMonMergePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonMergePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonMergePk.getClosureDay(), (this.krcdtMonMergePk.getIsLastDay() == 1)),
				AggregateAffiliationInfo.of(
						new EmploymentCode(this.firstEmploymentCd),
						new WorkplaceId(this.firstWorkplaceId),
						new JobTitleId(this.firstJobTitleId),
						new ClassificationCode(this.firstClassCd),
						Optional.ofNullable(this.firstBusinessTypeCd == null ? null : new BusinessTypeCode(this.firstBusinessTypeCd))),
				AggregateAffiliationInfo.of(
						new EmploymentCode(this.lastEmploymentCd),
						new WorkplaceId(this.lastWorkplaceId),
						new JobTitleId(this.lastJobTitleId),
						new ClassificationCode(this.lastClassCd),
						Optional.ofNullable(this.lastBusinessTypeCd == null ? null : new BusinessTypeCode(this.lastBusinessTypeCd))));
		
		domain.setVersion(this.version);
		
		return domain;
	}

	private void toEntityTotalCount1(TotalCount domain) {
		this.totalCountDays1 = domain.getCount().v();
		this.totalCountTime1 = domain.getTime().v();
	}
	
	private void toEntityTotalCount2(TotalCount domain) {
		this.totalCountDays2 = domain.getCount().v();
		this.totalCountTime2 = domain.getTime().v();
	}
	
		private void toEntityTotalCount3(TotalCount domain) {
		this.totalCountDays3 = domain.getCount().v();
		this.totalCountTime3 = domain.getTime().v();
	}
	
	private void toEntityTotalCount4(TotalCount domain) {
		this.totalCountDays4 = domain.getCount().v();
		this.totalCountTime4 = domain.getTime().v();
	}
		
	private void toEntityTotalCount5(TotalCount domain) {
		this.totalCountDays5 = domain.getCount().v();
		this.totalCountTime5 = domain.getTime().v();
	}
	
	private void toEntityTotalCount6(TotalCount domain) {
		this.totalCountDays6 = domain.getCount().v();
		this.totalCountTime6 = domain.getTime().v();
	}
		private void toEntityTotalCount7(TotalCount domain) {
		this.totalCountDays7 = domain.getCount().v();
		this.totalCountTime7 = domain.getTime().v();
	}
	
	private void toEntityTotalCount8(TotalCount domain) {
		this.totalCountDays8 = domain.getCount().v();
		this.totalCountTime8 = domain.getTime().v();
	}	
	
	private void toEntityTotalCount9(TotalCount domain) {
		this.totalCountDays9 = domain.getCount().v();
		this.totalCountTime9 = domain.getTime().v();
	}
	
	private void toEntityTotalCount10(TotalCount domain) {
		this.totalCountDays10 = domain.getCount().v();
		this.totalCountTime10 = domain.getTime().v();
	}	
	
	
	private void toEntityTotalCount11(TotalCount domain) {
		this.totalCountDays11 = domain.getCount().v();
		this.totalCountTime11 = domain.getTime().v();
	}
	
	private void toEntityTotalCount12(TotalCount domain) {
		this.totalCountDays12 = domain.getCount().v();
		this.totalCountTime12 = domain.getTime().v();
	}
	
		private void toEntityTotalCount13(TotalCount domain) {
		this.totalCountDays13 = domain.getCount().v();
		this.totalCountTime13 = domain.getTime().v();
	}
	
	private void toEntityTotalCount14(TotalCount domain) {
		this.totalCountDays14 = domain.getCount().v();
		this.totalCountTime14 = domain.getTime().v();
	}
		
	private void toEntityTotalCount15(TotalCount domain) {
		this.totalCountDays15 = domain.getCount().v();
		this.totalCountTime15 = domain.getTime().v();
	}
	
	private void toEntityTotalCount16(TotalCount domain) {
		this.totalCountDays16 = domain.getCount().v();
		this.totalCountTime16 = domain.getTime().v();
	}
		private void toEntityTotalCount17(TotalCount domain) {
		this.totalCountDays17 = domain.getCount().v();
		this.totalCountTime17 = domain.getTime().v();
	}
	
	private void toEntityTotalCount18(TotalCount domain) {
		this.totalCountDays18 = domain.getCount().v();
		this.totalCountTime18 = domain.getTime().v();
	}	
	
	private void toEntityTotalCount19(TotalCount domain) {
		this.totalCountDays19 = domain.getCount().v();
		this.totalCountTime19 = domain.getTime().v();
	}
	
	private void toEntityTotalCount20(TotalCount domain) {
		this.totalCountDays20 = domain.getCount().v();
		this.totalCountTime20 = domain.getTime().v();
	}	
	
	private void toEntityTotalCount21(TotalCount domain) {
		this.totalCountDays21 = domain.getCount().v();
		this.totalCountTime21 = domain.getTime().v();
	}
	
	private void toEntityTotalCount22(TotalCount domain) {
		this.totalCountDays22 = domain.getCount().v();
		this.totalCountTime22 = domain.getTime().v();
	}
	
		private void toEntityTotalCount23(TotalCount domain) {
		this.totalCountDays23 = domain.getCount().v();
		this.totalCountTime23 = domain.getTime().v();
	}
	
	private void toEntityTotalCount24(TotalCount domain) {
		this.totalCountDays24 = domain.getCount().v();
		this.totalCountTime24 = domain.getTime().v();
	}
		
	private void toEntityTotalCount25(TotalCount domain) {
		this.totalCountDays25 = domain.getCount().v();
		this.totalCountTime25 = domain.getTime().v();
	}
	
	private void toEntityTotalCount26(TotalCount domain) {
		this.totalCountDays26 = domain.getCount().v();
		this.totalCountTime26 = domain.getTime().v();
	}
		private void toEntityTotalCount27(TotalCount domain) {
		this.totalCountDays27 = domain.getCount().v();
		this.totalCountTime27 = domain.getTime().v();
	}
	
	private void toEntityTotalCount28(TotalCount domain) {
		this.totalCountDays28 = domain.getCount().v();
		this.totalCountTime28 = domain.getTime().v();
	}	
	
	private void toEntityTotalCount29(TotalCount domain) {
		this.totalCountDays29 = domain.getCount().v();
		this.totalCountTime29 = domain.getTime().v();
	}
	
	private void toEntityTotalCount30(TotalCount domain) {
		this.totalCountDays30  = domain.getCount().v();
		this.totalCountTime30 = domain.getTime().v();
	}	
	
	private TotalCount toDomainTotalCount(int totalCountNo, double totalCountDays, int totalCountTime) {
	    return TotalCount.of(totalCountNo, new AttendanceDaysMonth(totalCountDays), new AttendanceTimeMonth(totalCountTime));
	}
}