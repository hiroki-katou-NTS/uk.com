package nts.uk.ctx.at.record.infra.entity.monthly.mergetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.SpecialHolidayRemainDataMerge;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.RealAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.TimeAnnualLeaveUsedTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedAnnualLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.RemainDataTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveUndigestedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.ActualSpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeavaRemainTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveGrantUseDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemainDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUnDigestion;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.UseNumber;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 残数系
 * 
 * @author lanlt
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MON_REMAIN_MERGE")
public class KrcdtMonRemainMerge extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonMergePk krcdtMonRemainPk;

	/* KRCDT_MON_ANNLEA_REMAIN - エンティティ：年休月別残数データ */
	/** 締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	public int closureStatus;

	/** 開始年月日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	/** 終了年月日 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	/** 年休使用日数 */
	@Column(name = "ANNLEA_USED_DAYS")
	public double annleaUsedDays;

	/** 年休使用日数付与前 */
	@Column(name = "ANNLEA_USED_DAYS_BEFORE")
	public double annleaUsedDaysBefore;

	/** 年休使用日数付与後 */
	@Column(name = "ANNLEA_USED_DAYS_AFTER")
	public Double annleaUsedDaysAfter;

	/** 使用時間 */
	@Column(name = "ANNLEA_USED_MINUTES")
	public Integer annleaUsedMinutes;

	/** 使用時間付与前 */
	@Column(name = "ANNLEA_USED_MINUTES_BEFORE")
	public Integer annleaUsedMinutesBefore;

	/** 使用時間付与後 */
	@Column(name = "ANNLEA_USED_MINUTES_AFTER")
	public Integer annleaUsedMinutesAfter;
	/** 使用回数 */
	@Column(name = "ANNLEA_USED_TIMES")
	public Integer annleaUsedTimes;

	/** 実年休使用日数 */
	@Column(name = "ANNLEA_FACT_USED_DAYS")
	public double annleaFactUsedDays;

	/** 実年休使用日数付与前 */
	@Column(name = "ANNLEA_FACT_USED_DAYS_BEFORE")
	public double annleaFactUsedDaysBefore;

	/** 実年休使用日数付与後 */
	@Column(name = "ANNLEA_FACT_USED_DAYS_AFTER")
	public Double annleaFactUsedDaysAfter;

	/** 実使用時間 */
	@Column(name = "ANNLEA_FACT_USED_MINUTES")
	public Integer annleaFactUsedMinutes;

	/** 実使用時間付与前 */
	@Column(name = "ANNLEA_FACT_USED_MINUTES_BEFORE")
	public Integer annleaFactUsedMinutesBefore;

	/** 実使用時間付与後 */
	@Column(name = "ANNLEA_FACT_USED_MINUTES_AFTER")
	public Integer annleaFactUsedMinutesAfter;

	/** 実使用回数 */
	@Column(name = "ANNLEA_FACT_USED_TIMES")
	public Integer annleaFactUsedTimes;

	/** 合計残日数 */
	@Column(name = "ANNLEA_REMAINING_DAYS")
	public double annleaRemainingDays;

	/** 合計残時間 */
	@Column(name = "ANNLEA_REMAINING_MINUTES")
	public Integer annleaRemainingMinutes;

	/** 実合計残日数 */
	@Column(name = "ANNLEA_FACT_REMAINING_DAYS")
	public double annleaFactRemainingDays;

	/** 実合計残時間 */
	@Column(name = "ANNLEA_FACT_REMAINING_MINUTES")
	public Integer annleaFactRemainingMinutes;

	/** 合計残日数付与前 */
	@Column(name = "ANNLEA_REMAINING_DAYS_BEFORE")
	public double annleaRemainingDaysBefore;

	/** 合計残時間付与前 */
	@Column(name = "ANNLEA_REMAINING_MINUTES_BEFORE")
	public Integer annleaRemainingMinutesBefore;

	/** 実合計残日数付与前 */
	@Column(name = "ANNLEA_FACT_REMAINING_DAYS_BEFORE")
	public double annleaFactRemainingDaysBefore;

	/** 実合計残時間付与前 */
	@Column(name = "ANNLEA_FACT_REMAINING_MINUTES_BEFORE")
	public Integer annleaFactRemainingMinutesBefore;

	/** 合計残日数付与後 */
	@Column(name = "ANNLEA_REMAINING_DAYS_AFTER")
	public Double annleaRemainingDaysAfter;

	/** 合計残時間付与後 */
	@Column(name = "ANNLEA_REMAINING_MINUTES_AFTER")
	public Integer annleaRemainingMinutesAfter;

	/** 実合計残日数付与後 */
	@Column(name = "ANNLEA_FACT_REMAINING_DAYS_AFTER")
	public Double annleaFactRemainingDaysAfter;

	/** 実合計残時間付与後 */
	@Column(name = "ANNLEA_FACT_REMAINING_MINUTES_AFTER")
	public Integer annleaFactRemainingMinutesAfter;

	/** 未消化日数 */
	@Column(name = "ANNLEA_UNUSED_DAYS")
	public double annleaUnusedDays;

	/** 未消化時間 */
	@Column(name = "ANNLEA_UNUSED_MINUTES")
	public Integer annleaUnusedMinutes;

	/** 所定日数 */
	@Column(name = "ANNLEA_PREDETERMINED_DAYS")
	public int annleaPredeterminedDays;
	/** 労働日数 */
	@Column(name = "ANNLEA_LABOR_DAYS")
	public int annleaLaborDays;
	/** 控除日数 */
	@Column(name = "ANNLEA_DEDUCTION_DAYS")
	public int annleaDeductionDays;

	/** 半日年休使用回数 */
	@Column(name = "ANNLEA_HALF_USED_TIMES")
	public Integer annleaHalfUsedTimes;

	/** 半日年休使用回数付与前 */
	@Column(name = "ANNLEA_HALF_USED_TIMES_BEFORE")
	public Integer annleaHalfUsedTimesBefore;

	/** 半日年休使用回数付与後 */
	@Column(name = "ANNLEA_HALF_USED_TIMES_AFTER")
	public Integer annleaHalfUsedTimesAfter;

	/** 半日年休残回数 */
	@Column(name = "ANNLEA_HALF_REMAINING_TIMES")
	public Integer annleaHalfRemainingTimes;

	/** 半日年休残回数付与前 */
	@Column(name = "ANNLEA_HALF_REMAINING_TIMES_BEFORE")
	public Integer annleaHalfRemainingTimesBefore;

	/** 半日年休残回数付与後 */
	@Column(name = "ANNLEA_HALF_REMAINING_TIMES_AFTER")
	public Integer annleaHalfRemainingTimesAfter;

	/** 実半日年休使用回数 */
	@Column(name = "ANNLEA_FACT_HALF_USED_TIMES")
	public Integer annleaFactHalfUsedTimes;

	/** 実半日年休使用回数付与前 */
	@Column(name = "ANNLEA_FACT_HALF_USED_TIMES_BEFORE")
	public Integer annleaFactHalfUsedTimesBefore;

	/** 実半日年休使用回数付与後 */
	@Column(name = "ANNLEA_FACT_HALF_USED_TIMES_AFTER")
	public Integer annleaFactHalfUsedTimesAfter;

	/** 実半日年休残回数 */
	@Column(name = "ANNLEA_FACT_HALF_REMAINING_TIMES")
	public Integer annleaFactHalfRemainingTimes;

	/** 実半日年休残回数付与前 */
	@Column(name = "ANNLEA_FACT_HALF_REMAINING_TIMES_BE")
	public Integer annleaFactHalfRemainingTimesBefore;

	/** 実半日年休残回数付与後 */
	@Column(name = "ANNLEA_FACT_HALF_REMAINING_TIMES_AF")
	public Integer annleaFactHalfRemainingTimesAfter;

	/** 時間年休上限残時間 */
	@Column(name = "ANNLEA_TIME_REMAINING_MINUTES")
	public Integer annleaTimeRemainingMinutes;

	/** 時間年休上限残時間付与前 */
	@Column(name = "ANNLEA_TIME_REMAINING_MINUTES_BEFORE")
	public Integer annleaTimeRemainingMinutesBefore;

	/** 時間年休上限残時間付与後 */
	@Column(name = "ANNLEA_TIME_REMAINING_MINUTES_AFTER")
	public Integer annleaTimeRemainingMinutesAfter;

	/** 実時間年休上限残時間 */
	@Column(name = "ANNLEA_FACT_TIME_REMAINING_MINUTES")
	public Integer annleaFactTimeRemainingMinutes;

	/** 実時間年休上限残時間付与前 */
	@Column(name = "ANNLEA_FACT_TIME_REMAINING_MINUTES_BE")
	public Integer annleaFactTimeRemainingMinutesBefore;

	/** 実時間年休上限残時間付与後 */
	@Column(name = "ANNLEA_FACT_TIME_REMAINING_MINUTES_AF")
	public Integer annleaFactTimeRemainingMinutesAfter;

	/** 付与区分 */
	@Column(name = "ANNLEA_GRANT_ATR")
	public int annleaGrantAtr;

	/** 付与日数 */
	@Column(name = "ANNLEA_GRANT_DAYS")
	public Double annleaGrantDays;

	/** 付与所定日数 */
	@Column(name = "ANNLEA_GRANT_PREDETERMINED_DAYS")
	public Integer annleaGrantPredeterminedDays;

	/** 付与労働日数 */
	@Column(name = "ANNLEA_GRANT_LABOR_DAYS")
	public Integer annleaGrantLaborDays;

	/** 付与控除日数 */
	@Column(name = "ANNLEA_GRANT_DEDUCTION_DAYS")
	public Integer annleaGrantDeductionDays;

	/** 控除日数付与前 */
	@Column(name = "ANNLEA_DEDUCTION_DAYS_BEFORE")
	public Integer annleaDeductionDaysBefore;

	/** 控除日数付与後 */
	@Column(name = "ANNLEA_DEDUCTION_DAYS_AFTER")
	public Integer annleaDeductionDaysAfter;

	/** 出勤率 */
	@Column(name = "ANNLEA_ATTENDANCE_RATE")
	public Double annleaAttendanceRate;

	/* KRCDT_MON_RSVLEA_REMAIN */

	/** 使用日数 */
	@Column(name = "RSVLEA_USED_DAYS")
	public double rsvleaUsedDays;

	/** 使用日数付与前 */
	@Column(name = "RSVLEA_USED_DAYS_BEFORE")
	public double rsvleaUsedDaysBefore;

	/** 使用日数付与後 */
	@Column(name = "RSVLEA_USED_DAYS_AFTER")
	public Double rsvleaUsedDaysAfter;

	/** 実使用日数 */
	@Column(name = "RSVLEA_FACT_USED_DAYS")
	public double rsvleaFactUsedDays;

	/** 実使用日数付与前 */
	@Column(name = "RSVLEA_FACT_USED_DAYS_BEFORE")
	public double rsvleaFactUsedDaysBefore;

	/** 実使用日数付与後 */
	@Column(name = "RSVLEA_FACT_USED_DAYS_AFTER")
	public Double rsvleaFactUsedDaysAfter;

	/** 合計残日数 */
	@Column(name = "RSVLEA_REMAINING_DAYS")
	public double rsvleaRemainingDays;

	/** 実合計残日数 */
	@Column(name = "RSVLEA_FACT_REMAINING_DAYS")
	public double rsvleaFactRemainingDays;

	/** 合計残日数付与前 */
	@Column(name = "RSVLEA_REMAINING_DAYS_BEFORE")
	public double rsvleaRemainingDaysBefore;

	/** 実合計残日数付与前 */
	@Column(name = "RSVLEA_FACT_REMAINING_DAYS_BEFORE")
	public double rsvleaFactRemainingDaysBefore;

	/** 合計残日数付与後 */
	@Column(name = "RSVLEA_REMAINING_DAYS_AFTER")
	public Double rsvleaRemainingDaysAfter;

	/** 実合計残日数付与後 */
	@Column(name = "RSVLEA_FACT_REMAINING_DAYS_AFTER")
	public Double rsvleaFactRemainingDaysAfter;

	/** 未消化日数 */
	@Column(name = "RSVLEA_NOT_USED_DAYS")
	public double rsvleaNotUsedDays;

	/** 付与区分 */
	@Column(name = "RSVLEA_GRANT_ATR")
	public int rsvleaGrantAtr;

	/** 付与日数 */
	@Column(name = "RSVLEA_GRANT_DAYS")
	public Double rsvleaGrantDays;

	/* KRCDT_MON_SP_REMAIN */
	/** 特別休暇月別残数データ．特別休暇．使用数．使用日数 */
	@Column(name = "SP_USED_DAYS_1")
	public double useDays1;

	@Column(name = "SP_USED_DAYS_2")
	public double useDays2;

	@Column(name = "SP_USED_DAYS_3")
	public double useDays3;

	@Column(name = "SP_USED_DAYS_4")
	public double useDays4;

	@Column(name = "SP_USED_DAYS_5")
	public double useDays5;

	@Column(name = "SP_USED_DAYS_6")
	public double useDays6;

	@Column(name = "SP_USED_DAYS_7")
	public double useDays7;

	@Column(name = "SP_USED_DAYS_8")
	public double useDays8;

	@Column(name = "SP_USED_DAYS_9")
	public double useDays9;

	@Column(name = "SP_USED_DAYS_10")
	public double useDays10;

	@Column(name = "SP_USED_DAYS_11")
	public double useDays11;

	@Column(name = "SP_USED_DAYS_12")
	public double useDays12;

	@Column(name = "SP_USED_DAYS_13")
	public double useDays13;

	@Column(name = "SP_USED_DAYS_14")
	public double useDays14;

	@Column(name = "SP_USED_DAYS_15")
	public double useDays15;

	@Column(name = "SP_USED_DAYS_16")
	public double useDays16;

	@Column(name = "SP_USED_DAYS_17")
	public double useDays17;

	@Column(name = "SP_USED_DAYS_18")
	public double useDays18;

	@Column(name = "SP_USED_DAYS_19")
	public double useDays19;

	@Column(name = "SP_USED_DAYS_20")
	public double useDays20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */

	@Column(name = "SP_USED_DAYS_BEFORE_1")
	public double beforeUseDays1;

	@Column(name = "SP_SP_USED_DAYS_BEFORE_2")
	public double beforeUseDays2;

	@Column(name = "SP_USED_DAYS_BEFORE_3")
	public double beforeUseDays3;

	@Column(name = "SP_USED_DAYS_BEFORE_4")
	public double beforeUseDays4;

	@Column(name = "SP_USED_DAYS_BEFORE_5")
	public double beforeUseDays5;

	@Column(name = "SP_USED_DAYS_BEFORE_6")
	public double beforeUseDays6;

	@Column(name = "SP_USED_DAYS_BEFORE_7")
	public double beforeUseDays7;

	@Column(name = "SP_USED_DAYS_BEFORE_8")
	public double beforeUseDays8;

	@Column(name = "SP_USED_DAYS_BEFORE_9")
	public double beforeUseDays9;

	@Column(name = "SP_USED_DAYS_BEFORE_10")
	public double beforeUseDays10;

	@Column(name = "SP_USED_DAYS_BEFORE_11")
	public double beforeUseDays11;

	@Column(name = "SP_USED_DAYS_BEFORE_12")
	public double beforeUseDays12;

	@Column(name = "SP_USED_DAYS_BEFORE_13")
	public double beforeUseDays13;

	@Column(name = "SP_USED_DAYS_BEFORE_14")
	public double beforeUseDays14;

	@Column(name = "SP_USED_DAYS_BEFORE_15")
	public double beforeUseDays15;

	@Column(name = "SP_USED_DAYS_BEFORE_16")
	public double beforeUseDays16;

	@Column(name = "SP_USED_DAYS_BEFORE_17")
	public double beforeUseDays17;

	@Column(name = "SP_USED_DAYS_BEFORE_18")
	public double beforeUseDays18;

	@Column(name = "SP_USED_DAYS_BEFORE_19")
	public double beforeUseDays19;

	@Column(name = "SP_USED_DAYS_BEFORE_20")
	public double beforeUseDays20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用日数． 特別休暇使用日数付与後 */

	@Column(name = "SP_USED_DAYS_AFTER_1")
	public Double afterUseDays1;

	@Column(name = "SP_USED_DAYS_AFTER_2")
	public Double afterUseDays2;

	@Column(name = "SP_USED_DAYS_AFTER_3")
	public Double afterUseDays3;

	@Column(name = "SP_USED_DAYS_AFTER_4")
	public Double afterUseDays4;

	@Column(name = "SP_USED_DAYS_AFTER_5")
	public Double afterUseDays5;

	@Column(name = "SP_USED_DAYS_AFTER_6")
	public Double afterUseDays6;

	@Column(name = "SP_USED_DAYS_AFTER_7")
	public Double afterUseDays7;

	@Column(name = "SP_USED_DAYS_AFTER_8")
	public Double afterUseDays8;

	@Column(name = "SP_SP_USED_DAYS_AFTER_9")
	public Double afterUseDays9;

	@Column(name = "SP_USED_DAYS_AFTER_10")
	public Double afterUseDays10;

	@Column(name = "SP_USED_DAYS_AFTER_11")
	public Double afterUseDays11;

	@Column(name = "SP_USED_DAYS_AFTER_12")
	public Double afterUseDays12;

	@Column(name = "SP_USED_DAYS_AFTER_13")
	public Double afterUseDays13;

	@Column(name = "SP_USED_DAYS_AFTER_14")
	public Double afterUseDays14;

	@Column(name = "SP_USED_DAYS_AFTER_15")
	public Double afterUseDays15;

	@Column(name = "SP_USED_DAYS_AFTER_16")
	public Double afterUseDays16;

	@Column(name = "SP_USED_DAYS_AFTER_17")
	public Double afterUseDays17;

	@Column(name = "SP_USED_DAYS_AFTER_18")
	public Double afterUseDays18;

	@Column(name = "SP_USED_DAYS_AFTER_19")
	public Double afterUseDays19;

	@Column(name = "SP_USED_DAYS_AFTER_20")
	public Double afterUseDays20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用時間．使用時間 */

	@Column(name = "SP_USED_MINUTES_1")
	public Integer useMinutes1;

	@Column(name = "SP_USED_MINUTES_2")
	public Integer useMinutes2;

	@Column(name = "SP_USED_MINUTES_3")
	public Integer useMinutes3;

	@Column(name = "SP_USED_MINUTES_4")
	public Integer useMinutes4;

	@Column(name = "SP_USED_MINUTES_5")
	public Integer useMinutes5;

	@Column(name = "SP_USED_MINUTES_6")
	public Integer useMinutes6;

	@Column(name = "SP_USED_MINUTES_7")
	public Integer useMinutes7;

	@Column(name = "SP_USED_MINUTES_8")
	public Integer useMinutes8;

	@Column(name = "SP_USED_MINUTES_9")
	public Integer useMinutes9;

	@Column(name = "SP_USED_MINUTES_10")
	public Integer useMinutes10;

	@Column(name = "SP_USED_MINUTES_11")
	public Integer useMinutes11;

	@Column(name = "SP_USED_MINUTES_12")
	public Integer useMinutes12;

	@Column(name = "SP_USED_MINUTES_13")
	public Integer useMinutes13;

	@Column(name = "SP_USED_MINUTES_14")
	public Integer useMinutes14;

	@Column(name = "SP_USED_MINUTES_15")
	public Integer useMinutes15;

	@Column(name = "SP_USED_MINUTES_16")
	public Integer useMinutes16;

	@Column(name = "SP_USED_MINUTES_17")
	public Integer useMinutes17;

	@Column(name = "SP_USED_MINUTES_18")
	public Integer useMinutes18;

	@Column(name = "SP_USED_MINUTES_19")
	public Integer useMinutes19;

	@Column(name = "SP_USED_MINUTES_20")
	public Integer useMinutes20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
	@Column(name = "SP_USED_MINUTES_BEFORE_1")
	public Integer beforeUseMinutes1;

	@Column(name = "SP_USED_MINUTES_BEFORE_2")
	public Integer beforeUseMinutes2;

	@Column(name = "SP_USED_MINUTES_BEFORE_3")
	public Integer beforeUseMinutes3;

	@Column(name = "SP_USED_MINUTES_BEFORE_4")
	public Integer beforeUseMinutes4;

	@Column(name = "SP_USED_MINUTES_BEFORE_5")
	public Integer beforeUseMinutes5;

	@Column(name = "SP_USED_MINUTES_BEFORE_6")
	public Integer beforeUseMinutes6;

	@Column(name = "SP_USED_MINUTES_BEFORE_7")
	public Integer beforeUseMinutes7;

	@Column(name = "SP_USED_MINUTES_BEFORE_8")
	public Integer beforeUseMinutes8;

	@Column(name = "SP_USED_MINUTES_BEFORE_9")
	public Integer beforeUseMinutes9;

	@Column(name = "SP_USED_MINUTES_BEFORE_10")
	public Integer beforeUseMinutes10;

	@Column(name = "SP_USED_MINUTES_BEFORE_11")
	public Integer beforeUseMinutes11;

	@Column(name = "SP_USED_MINUTES_BEFORE_12")
	public Integer beforeUseMinutes12;

	@Column(name = "SP_USED_MINUTES_BEFORE_13")
	public Integer beforeUseMinutes13;

	@Column(name = "SP_USED_MINUTES_BEFORE_14")
	public Integer beforeUseMinutes14;

	@Column(name = "SP_USED_MINUTES_BEFORE_15")
	public Integer beforeUseMinutes15;

	@Column(name = "SP_USED_MINUTES_BEFORE_16")
	public Integer beforeUseMinutes16;

	@Column(name = "SP_USED_MINUTES_BEFORE_17")
	public Integer beforeUseMinutes17;

	@Column(name = "SP_USED_MINUTES_BEFORE_18")
	public Integer beforeUseMinutes18;

	@Column(name = "SP_USED_MINUTES_BEFORE_19")
	public Integer beforeUseMinutes19;

	@Column(name = "SP_USED_MINUTES_BEFORE_20")
	public Integer beforeUseMinutes20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用時間 ．使用時間付与後 */
	@Column(name = "SP_USED_MINUTES_AFTER_1")
	public Integer afterUseMinutes1;

	@Column(name = "SP_USED_MINUTES_AFTER_2")
	public Integer afterUseMinutes2;

	@Column(name = "SP_USED_MINUTES_AFTER_3")
	public Integer afterUseMinutes3;

	@Column(name = "SP_USED_MINUTES_AFTER_4")
	public Integer afterUseMinutes4;

	@Column(name = "SP_USED_MINUTES_AFTER_5")
	public Integer afterUseMinutes5;

	@Column(name = "SP_USED_MINUTES_AFTER_6")
	public Integer afterUseMinutes6;

	@Column(name = "SP_USED_MINUTES_AFTER_7")
	public Integer afterUseMinutes7;

	@Column(name = "SP_USED_MINUTES_AFTER_8")
	public Integer afterUseMinutes8;

	@Column(name = "SP_USED_MINUTES_AFTER_9")
	public Integer afterUseMinutes9;

	@Column(name = "SP_USED_MINUTES_AFTER_10")
	public Integer afterUseMinutes10;

	@Column(name = "SP_USED_MINUTES_AFTER_11")
	public Integer afterUseMinutes11;

	@Column(name = "SP_USED_MINUTES_AFTER_12")
	public Integer afterUseMinutes12;

	@Column(name = "SP_USED_MINUTES_AFTER_13")
	public Integer afterUseMinutes13;

	@Column(name = "SP_USED_MINUTES_AFTER_14")
	public Integer afterUseMinutes14;

	@Column(name = "SP_USED_MINUTES_AFTER_15")
	public Integer afterUseMinutes15;

	@Column(name = "SP_USED_MINUTES_AFTER_16")
	public Integer afterUseMinutes16;

	@Column(name = "SP_USED_MINUTES_AFTER_17")
	public Integer afterUseMinutes17;

	@Column(name = "SP_USED_MINUTES_AFTER_18")
	public Integer afterUseMinutes18;

	@Column(name = "SP_USED_MINUTES_AFTER_19")
	public Integer afterUseMinutes19;

	@Column(name = "SP_USED_MINUTES_AFTER_20")
	public Integer afterUseMinutes20;

	/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
	@Column(name = "SP_USED_TIMES_1")
	public Integer useTimes1;

	@Column(name = "SP_USED_TIMES_2")
	public Integer useTimes2;

	@Column(name = "SP_USED_TIMES_3")
	public Integer useTimes3;

	@Column(name = "SP_USED_TIMES_4")
	public Integer useTimes4;

	@Column(name = "SP_USED_TIMES_5")
	public Integer useTimes5;

	@Column(name = "SP_USED_TIMES_6")
	public Integer useTimes6;

	@Column(name = "SP_USED_TIMES_7")
	public Integer useTimes7;

	@Column(name = "SP_USED_TIMES_8")
	public Integer useTimes8;

	@Column(name = "SP_USED_TIMES_9")
	public Integer useTimes9;

	@Column(name = "SP_USED_TIMES_10")
	public Integer useTimes10;

	@Column(name = "SP_USED_TIMES_11")
	public Integer useTimes11;

	@Column(name = "SP_USED_TIMES_12")
	public Integer useTimes12;

	@Column(name = "SP_USED_TIMES_13")
	public Integer useTimes13;

	@Column(name = "SP_USED_TIMES_14")
	public Integer useTimes14;

	@Column(name = "SP_USED_TIMES_15")
	public Integer useTimes15;

	@Column(name = "SP_USED_TIMES_16")
	public Integer useTimes16;

	@Column(name = "SP_USED_TIMES_17")
	public Integer useTimes17;

	@Column(name = "SP_USED_TIMES_18")
	public Integer useTimes18;

	@Column(name = "SP_USED_TIMES_19")
	public Integer useTimes19;

	@Column(name = "SP_USED_TIMES_20")
	public Integer useTimes20;
	
	/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
	@Column(name = "SP_FACT_USED_DAYS_1")
	public double factUseDays1;

	@Column(name = "SP_FACT_USED_DAYS_2")
	public double factUseDays2;

	@Column(name = "SP_FACT_USED_DAYS_3")
	public double factUseDays3;

	@Column(name = "SP_FACT_USED_DAYS_4")
	public double factUseDays4;

	@Column(name = "SP_FACT_USED_DAYS_5")
	public double factUseDays5;

	@Column(name = "SP_FACT_USED_DAYS_6")
	public double factUseDays6;

	@Column(name = "SP_FACT_USED_DAYS_7")
	public double factUseDays7;

	@Column(name = "SP_FACT_USED_DAYS_8")
	public double factUseDays8;

	@Column(name = "SP_FACT_USED_DAYS_9")
	public double factUseDays9;

	@Column(name = "SP_FACT_USED_DAYS_10")
	public double factUseDays10;

	@Column(name = "SP_FACT_USED_DAYS_11")
	public double factUseDays11;

	@Column(name = "SP_FACT_USED_DAYS_12")
	public double factUseDays12;

	@Column(name = "SP_FACT_USED_DAYS_13")
	public double factUseDays13;

	@Column(name = "SP_FACT_USED_DAYS_14")
	public double factUseDays14;

	@Column(name = "SP_FACT_USED_DAYS_15")
	public double factUseDays15;

	@Column(name = "SP_FACT_USED_DAYS_16")
	public double factUseDays16;

	@Column(name = "SP_FACT_USED_DAYS_17")
	public double factUseDays17;

	@Column(name = "SP_FACT_USED_DAYS_18")
	public double factUseDays18;

	@Column(name = "SP_FACT_USED_DAYS_19")
	public double factUseDays19;

	@Column(name = "SP_FACT_USED_DAYS_20")
	public double factUseDays20;

	/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数. 使用日数付与前 */
	@Column(name = "SP_FACT_USED_DAYS_BEFORE_1")
	public double beforeFactUseDays1;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_2")
	public double beforeFactUseDays2;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_3")
	public double beforeFactUseDays3;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_4")
	public double beforeFactUseDays4;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_5")
	public double beforeFactUseDays5;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_6")
	public double beforeFactUseDays6;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_7")
	public double beforeFactUseDays7;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_8")
	public double beforeFactUseDays8;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_9")
	public double beforeFactUseDays9;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_10")
	public double beforeFactUseDays10;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_11")
	public double beforeFactUseDays11;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_12")
	public double beforeFactUseDays12;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_13")
	public double beforeFactUseDays13;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_14")
	public double beforeFactUseDays14;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_15")
	public double beforeFactUseDays15;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_16")
	public double beforeFactUseDays16;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_17")
	public double beforeFactUseDays17;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_18")
	public double beforeFactUseDays18;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_19")
	public double beforeFactUseDays19;

	@Column(name = "SP_FACT_USED_DAYS_BEFORE_20")
	public double beforeFactUseDays20;
	
	/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数.使用日数付与後 */
	@Column(name = "SP_FACT_USED_DAYS_AFTER_1")
	public Double afterFactUseDays1;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_2")
	public Double afterFactUseDays2;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_3")
	public Double afterFactUseDays3;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_4")
	public Double afterFactUseDays4;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_5")
	public Double afterFactUseDays5;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_6")
	public Double afterFactUseDays6;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_7")
	public Double afterFactUseDays7;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_8")
	public Double afterFactUseDays8;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_9")
	public Double afterFactUseDays9;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_10")
	public Double afterFactUseDays10;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_11")
	public Double afterFactUseDays11;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_12")
	public Double afterFactUseDays12;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_13")
	public Double afterFactUseDays13;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_14")
	public Double afterFactUseDays14;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_15")
	public Double afterFactUseDays15;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_16")
	public Double afterFactUseDays16;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_17")
	public Double afterFactUseDays17;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_18")
	public Double afterFactUseDays18;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_19")
	public Double afterFactUseDays19;

	@Column(name = "SP_FACT_USED_DAYS_AFTER_20")
	public Double afterFactUseDays20;

	/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
	@Column(name = "SP_FACT_USED_MINUTES_1")
	public Integer factUseMinutes1;

	@Column(name = "SP_FACT_USED_MINUTES_2")
	public Integer factUseMinutes2;

	@Column(name = "SP_FACT_USED_MINUTES_3")
	public Integer factUseMinutes3;

	@Column(name = "SP_FACT_USED_MINUTES_4")
	public Integer factUseMinutes4;

	@Column(name = "SP_FACT_USED_MINUTES_5")
	public Integer factUseMinutes5;

	@Column(name = "SP_FACT_USED_MINUTES_6")
	public Integer factUseMinutes6;

	@Column(name = "SP_FACT_USED_MINUTES_7")
	public Integer factUseMinutes7;

	@Column(name = "SP_FACT_USED_MINUTES_8")
	public Integer factUseMinutes8;

	@Column(name = "SP_FACT_USED_MINUTES_9")
	public Integer factUseMinutes9;

	@Column(name = "SP_FACT_USED_MINUTES_10")
	public Integer factUseMinutes10;

	@Column(name = "SP_FACT_USED_MINUTES_11")
	public Integer factUseMinutes11;

	@Column(name = "SP_FACT_USED_MINUTES_12")
	public Integer factUseMinutes12;

	@Column(name = "SP_FACT_USED_MINUTES_13")
	public Integer factUseMinutes13;

	@Column(name = "SP_FACT_USED_MINUTES_14")
	public Integer factUseMinutes14;

	@Column(name = "SP_FACT_USED_MINUTES_15")
	public Integer factUseMinutes15;

	@Column(name = "SP_FACT_USED_MINUTES_16")
	public Integer factUseMinutes16;

	@Column(name = "SP_FACT_USED_MINUTES_17")
	public Integer factUseMinutes17;

	@Column(name = "SP_FACT_USED_MINUTES_18")
	public Integer factUseMinutes18;

	@Column(name = "SP_FACT_USED_MINUTES_19")
	public Integer factUseMinutes19;

	@Column(name = "SP_FACT_USED_MINUTES_20")
	public Integer factUseMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間付与前 */
	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_1")
	public Integer beforeFactUseMinutes1;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_2")
	public Integer beforeFactUseMinutes2;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_3")
	public Integer beforeFactUseMinutes3;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_4")
	public Integer beforeFactUseMinutes4;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_5")
	public Integer beforeFactUseMinutes5;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_6")
	public Integer beforeFactUseMinutes6;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_7")
	public Integer beforeFactUseMinutes7;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_8")
	public Integer beforeFactUseMinutes8;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_9")
	public Integer beforeFactUseMinutes9;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_10")
	public Integer beforeFactUseMinutes10;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_11")
	public Integer beforeFactUseMinutes11;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_12")
	public Integer beforeFactUseMinutes12;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_13")
	public Integer beforeFactUseMinutes13;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_14")
	public Integer beforeFactUseMinutes14;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_15")
	public Integer beforeFactUseMinutes15;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_16")
	public Integer beforeFactUseMinutes16;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_17")
	public Integer beforeFactUseMinutes17;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_18")
	public Integer beforeFactUseMinutes18;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_19")
	public Integer beforeFactUseMinutes19;

	@Column(name = "SP_FACT_USED_MINUTES_BEFORE_20")
	public Integer beforeFactUseMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間. 使用時間付与後 */
	@Column(name = "SP_FACT_USED_MINUTES_AFTER_1")
	public Integer afterFactUseMinutes1;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_2")
	public Integer afterFactUseMinutes2;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_3")
	public Integer afterFactUseMinutes3;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_4")
	public Integer afterFactUseMinutes4;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_5")
	public Integer afterFactUseMinutes5;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_6")
	public Integer afterFactUseMinutes6;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_7")
	public Integer afterFactUseMinutes7;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_8")
	public Integer afterFactUseMinutes8;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_9")
	public Integer afterFactUseMinutes9;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_10")
	public Integer afterFactUseMinutes10;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_11")
	public Integer afterFactUseMinutes11;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_12")
	public Integer afterFactUseMinutes12;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_13")
	public Integer afterFactUseMinutes13;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_14")
	public Integer afterFactUseMinutes14;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_15")
	public Integer afterFactUseMinutes15;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_16")
	public Integer afterFactUseMinutes16;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_17")
	public Integer afterFactUseMinutes17;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_18")
	public Integer afterFactUseMinutes18;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_19")
	public Integer afterFactUseMinutes19;

	@Column(name = "SP_FACT_USED_MINUTES_AFTER_20")
	public Integer afterFactUseMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
	@Column(name = "SP_FACT_USED_TIMES_1")
	public Integer factUseTimes1;

	@Column(name = "SP_FACT_USED_TIMES_2")
	public Integer factUseTimes2;

	@Column(name = "SP_FACT_USED_TIMES_3")
	public Integer factUseTimes3;

	@Column(name = "SP_FACT_USED_TIMES_4")
	public Integer factUseTimes4;

	@Column(name = "SP_FACT_USED_TIMES_5")
	public Integer factUseTimes5;

	@Column(name = "SP_FACT_USED_TIMES_6")
	public Integer factUseTimes6;

	@Column(name = "SP_FACT_USED_TIMES_7")
	public Integer factUseTimes7;

	@Column(name = "SP_FACT_USED_TIMES_8")
	public Integer factUseTimes8;

	@Column(name = "SP_FACT_USED_TIMES_9")
	public Integer factUseTimes9;

	@Column(name = "SP_FACT_USED_TIMES_10")
	public Integer factUseTimes10;

	@Column(name = "SP_FACT_USED_TIMES_11")
	public Integer factUseTimes11;

	@Column(name = "SP_FACT_USED_TIMES_12")
	public Integer factUseTimes12;

	@Column(name = "SP_FACT_USED_TIMES_13")
	public Integer factUseTimes13;

	@Column(name = "SP_FACT_USED_TIMES_14")
	public Integer factUseTimes14;

	@Column(name = "SP_FACT_USED_TIMES_15")
	public Integer factUseTimes15;

	@Column(name = "SP_FACT_USED_TIMES_16")
	public Integer factUseTimes16;

	@Column(name = "SP_FACT_USED_TIMES_17")
	public Integer factUseTimes17;

	@Column(name = "SP_FACT_USED_TIMES_18")
	public Integer factUseTimes18;

	@Column(name = "SP_FACT_USED_TIMES_19")
	public Integer factUseTimes19;

	@Column(name = "SP_FACT_USED_TIMES_20")
	public Integer factUseTimes20;

	/** 特別休暇月別残数データ．特別休暇．残数.日数 */
	@Column(name = "SP_REMAINING_DAYS_1")
	public double remainDays1;

	@Column(name = "SP_REMAINING_DAYS_2")
	public double remainDays2;

	@Column(name = "SP_REMAINING_DAYS_3")
	public double remainDays3;

	@Column(name = "SP_REMAINING_DAYS_4")
	public double remainDays4;

	@Column(name = "SP_REMAINING_DAYS_5")
	public double remainDays5;

	@Column(name = "SP_REMAINING_DAYS_6")
	public double remainDays6;

	@Column(name = "SP_REMAINING_DAYS_7")
	public double remainDays7;

	@Column(name = "SP_REMAINING_DAYS_8")
	public double remainDays8;

	@Column(name = "SP_REMAINING_DAYS_9")
	public double remainDays9;

	@Column(name = "SP_REMAINING_DAYS_10")
	public double remainDays10;

	@Column(name = "SP_REMAINING_DAYS_11")
	public double remainDays11;

	@Column(name = "SP_REMAINING_DAYS_12")
	public double remainDays12;

	@Column(name = "SP_REMAINING_DAYS_13")
	public double remainDays13;

	@Column(name = "SP_REMAINING_DAYS_14")
	public double remainDays14;

	@Column(name = "SP_REMAINING_DAYS_15")
	public double remainDays15;

	@Column(name = "SP_REMAINING_DAYS_16")
	public double remainDays16;

	@Column(name = "SP_REMAINING_DAYS_17")
	public double remainDays17;

	@Column(name = "SP_REMAINING_DAYS_18")
	public double remainDays18;

	@Column(name = "SP_REMAINING_DAYS_19")
	public double remainDays19;

	@Column(name = "SP_REMAINING_DAYS_20")
	public double remainDays20;

	/** 特別休暇月別残数データ．特別休暇．残数.時間 */
	@Column(name = "SP_REMAINING_MINUTES_1")
	public Integer remainMinutes1;

	@Column(name = "SP_REMAINING_MINUTES_2")
	public Integer remainMinutes2;

	@Column(name = "SP_REMAINING_MINUTES_3")
	public Integer remainMinutes3;

	@Column(name = "SP_REMAINING_MINUTES_4")
	public Integer remainMinutes4;

	@Column(name = "SP_REMAINING_MINUTES_5")
	public Integer remainMinutes5;

	@Column(name = "SP_REMAINING_MINUTES_6")
	public Integer remainMinutes6;

	@Column(name = "SP_REMAINING_MINUTES_7")
	public Integer remainMinutes7;

	@Column(name = "SP_REMAINING_MINUTES_8")
	public Integer remainMinutes8;

	@Column(name = "SP_REMAINING_MINUTES_9")
	public Integer remainMinutes9;

	@Column(name = "SP_REMAINING_MINUTES_10")
	public Integer remainMinutes10;

	@Column(name = "SP_REMAINING_MINUTES_11")
	public Integer remainMinutes11;

	@Column(name = "SP_REMAINING_MINUTES_12")
	public Integer remainMinutes12;

	@Column(name = "SP_REMAINING_MINUTES_13")
	public Integer remainMinutes13;

	@Column(name = "SP_REMAINING_MINUTES_14")
	public Integer remainMinutes14;

	@Column(name = "SP_REMAINING_MINUTES_15")
	public Integer remainMinutes15;

	@Column(name = "SP_REMAINING_MINUTES_16")
	public Integer remainMinutes16;

	@Column(name = "SP_REMAINING_MINUTES_17")
	public Integer remainMinutes17;

	@Column(name = "SP_REMAINING_MINUTES_18")
	public Integer remainMinutes18;

	@Column(name = "SP_REMAINING_MINUTES_19")
	public Integer remainMinutes19;

	@Column(name = "SP_REMAINING_MINUTES_20")
	public Integer remainMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．残数. 日数 */
	@Column(name = "SP_FACT_REMAINING_DAYS_1")
	public double factRemainDays1;

	@Column(name = "SP_FACT_REMAINING_DAYS_2")
	public double factRemainDays2;

	@Column(name = "SP_FACT_REMAINING_DAYS_3")
	public double factRemainDays3;

	@Column(name = "SP_FACT_REMAINING_DAYS_4")
	public double factRemainDays4;

	@Column(name = "SP_FACT_REMAINING_DAYS_5")
	public double factRemainDays5;

	@Column(name = "SP_FACT_REMAINING_DAYS_6")
	public double factRemainDays6;

	@Column(name = "SP_FACT_REMAINING_DAYS_7")
	public double factRemainDays7;

	@Column(name = "SP_FACT_REMAINING_DAYS_8")
	public double factRemainDays8;

	@Column(name = "SP_FACT_REMAINING_DAYS_9")
	public double factRemainDays9;

	@Column(name = "SP_FACT_REMAINING_DAYS_10")
	public double factRemainDays10;

	@Column(name = "SP_FACT_REMAINING_DAYS_11")
	public double factRemainDays11;

	@Column(name = "SP_FACT_REMAINING_DAYS_12")
	public double factRemainDays12;

	@Column(name = "SP_FACT_REMAINING_DAYS_13")
	public double factRemainDays13;

	@Column(name = "SP_FACT_REMAINING_DAYS_14")
	public double factRemainDays14;

	@Column(name = "SP_FACT_REMAINING_DAYS_15")
	public double factRemainDays15;

	@Column(name = "SP_FACT_REMAINING_DAYS_16")
	public double factRemainDays16;

	@Column(name = "SP_FACT_REMAINING_DAYS_17")
	public double factRemainDays17;

	@Column(name = "SP_FACT_REMAINING_DAYS_18")
	public double factRemainDays18;

	@Column(name = "SP_FACT_REMAINING_DAYS_19")
	public double factRemainDays19;

	@Column(name = "SP_FACT_REMAINING_DAYS_20")
	public double factRemainDays20;

	/** 特別休暇月別残数データ．実特別休暇．残数.時間 */
	
	@Column(name = "SP_FACT_REMAINING_MINUTES_1")
	public Integer factRemainMinutes1;

	@Column(name = "SP_FACT_REMAINING_MINUTES_2")
	public Integer factRemainMinutes2;

	@Column(name = "SP_FACT_REMAINING_MINUTES_3")
	public Integer factRemainMinutes3;

	@Column(name = "SP_FACT_REMAINING_MINUTES_4")
	public Integer factRemainMinutes4;

	@Column(name = "SP_FACT_REMAINING_MINUTES_5")
	public Integer factRemainMinutes5;

	@Column(name = "SP_FACT_REMAINING_MINUTES_6")
	public Integer factRemainMinutes6;

	@Column(name = "SP_FACT_REMAINING_MINUTES_7")
	public Integer factRemainMinutes7;

	@Column(name = "SP_FACT_REMAINING_MINUTES_8")
	public Integer factRemainMinutes8;

	@Column(name = "SP_FACT_REMAINING_MINUTES_9")
	public Integer factRemainMinutes9;

	@Column(name = "SP_FACT_REMAINING_MINUTES_10")
	public Integer factRemainMinutes10;

	@Column(name = "SP_FACT_REMAINING_MINUTES_11")
	public Integer factRemainMinutes11;

	@Column(name = "SP_FACT_REMAINING_MINUTES_12")
	public Integer factRemainMinutes12;

	@Column(name = "SP_FACT_REMAINING_MINUTES_13")
	public Integer factRemainMinutes13;

	@Column(name = "SP_FACT_REMAINING_MINUTES_14")
	public Integer factRemainMinutes14;

	@Column(name = "SP_FACT_REMAINING_MINUTES_15")
	public Integer factRemainMinutes15;

	@Column(name = "SP_FACT_REMAINING_MINUTES_16")
	public Integer factRemainMinutes16;

	@Column(name = "SP_FACT_REMAINING_MINUTES_17")
	public Integer factRemainMinutes17;

	@Column(name = "SP_FACT_REMAINING_MINUTES_18")
	public Integer factRemainMinutes18;

	@Column(name = "SP_FACT_REMAINING_MINUTES_19")
	public Integer factRemainMinutes19;

	@Column(name = "SP_FACT_REMAINING_MINUTES_20")
	public Integer factRemainMinutes20;

	/** 特別休暇月別残数データ．特別休暇．残数付与前.日数 */
	@Column(name = "SP_REMAINING_DAYS_BEFORE_1")
	public double beforeRemainDays1;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_2")
	public double beforeRemainDays2;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_3")
	public double beforeRemainDays3;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_4")
	public double beforeRemainDays4;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_5")
	public double beforeRemainDays5;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_6")
	public double beforeRemainDays6;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_7")
	public double beforeRemainDays7;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_8")
	public double beforeRemainDays8;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_9")
	public double beforeRemainDays9;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_10")
	public double beforeRemainDays10;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_11")
	public double beforeRemainDays11;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_12")
	public double beforeRemainDays12;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_13")
	public double beforeRemainDays13;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_14")
	public double beforeRemainDays14;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_15")
	public double beforeRemainDays15;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_16")
	public double beforeRemainDays16;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_17")
	public double beforeRemainDays17;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_18")
	public double beforeRemainDays18;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_19")
	public double beforeRemainDays19;

	@Column(name = "SP_REMAINING_DAYS_BEFORE_20")
	public double beforeRemainDays20;

	/** 特別休暇月別残数データ．特別休暇．残数付与前.時間 */

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_1")
	public Integer beforeRemainMinutes1;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_2")
	public Integer beforeRemainMinutes2;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_3")
	public Integer beforeRemainMinutes3;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_4")
	public Integer beforeRemainMinutes4;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_5")
	public Integer beforeRemainMinutes5;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_6")
	public Integer beforeRemainMinutes6;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_7")
	public Integer beforeRemainMinutes7;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_8")
	public Integer beforeRemainMinutes8;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_9")
	public Integer beforeRemainMinutes9;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_10")
	public Integer beforeRemainMinutes10;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_11")
	public Integer beforeRemainMinutes11;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_12")
	public Integer beforeRemainMinutes12;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_13")
	public Integer beforeRemainMinutes13;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_14")
	public Integer beforeRemainMinutes14;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_15")
	public Integer beforeRemainMinutes15;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_16")
	public Integer beforeRemainMinutes16;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_17")
	public Integer beforeRemainMinutes17;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_18")
	public Integer beforeRemainMinutes18;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_19")
	public Integer beforeRemainMinutes19;

	@Column(name = "SP_REMAINING_MINUTES_BEFORE_20")
	public Integer beforeRemainMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．残数付与前.日数 */

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_1")
	public double beforeFactRemainDays1;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_2")
	public double beforeFactRemainDays2;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_3")
	public double beforeFactRemainDays3;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_4")
	public double beforeFactRemainDays4;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_5")
	public double beforeFactRemainDays5;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_6")
	public double beforeFactRemainDays6;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_7")
	public double beforeFactRemainDays7;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_8")
	public double beforeFactRemainDays8;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_9")
	public double beforeFactRemainDays9;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_10")
	public double beforeFactRemainDays10;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_11")
	public double beforeFactRemainDays11;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_12")
	public double beforeFactRemainDays12;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_13")
	public double beforeFactRemainDays13;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_14")
	public double beforeFactRemainDays14;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_15")
	public double beforeFactRemainDays15;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_16")
	public double beforeFactRemainDays16;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_17")
	public double beforeFactRemainDays17;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_18")
	public double beforeFactRemainDays18;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_19")
	public double beforeFactRemainDays19;

	@Column(name = "SP_FACT_REMAINING_DAYS_BEFORE_20")
	public double beforeFactRemainDays20;

	/** 特別休暇月別残数データ．実特別休暇．残数付与前.時間 */
	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_1")
	public Integer beforeFactRemainMinutes1;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_2")
	public Integer beforeFactRemainMinutes2;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_3")
	public Integer beforeFactRemainMinutes3;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_4")
	public Integer beforeFactRemainMinutes4;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_5")
	public Integer beforeFactRemainMinutes5;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_6")
	public Integer beforeFactRemainMinutes6;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_7")
	public Integer beforeFactRemainMinutes7;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_8")
	public Integer beforeFactRemainMinutes8;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_9")
	public Integer beforeFactRemainMinutes9;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_10")
	public Integer beforeFactRemainMinutes10;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_11")
	public Integer beforeFactRemainMinutes11;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_12")
	public Integer beforeFactRemainMinutes12;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_13")
	public Integer beforeFactRemainMinutes13;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_14")
	public Integer beforeFactRemainMinutes14;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_15")
	public Integer beforeFactRemainMinutes15;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_16")
	public Integer beforeFactRemainMinutes16;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_17")
	public Integer beforeFactRemainMinutes17;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_18")
	public Integer beforeFactRemainMinutes18;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_19")
	public Integer beforeFactRemainMinutes19;

	@Column(name = "SP_FACT_REMAINING_MINUTES_BEFORE_20")
	public Integer beforeFactRemainMinutes20;

	/** 特別休暇月別残数データ．特別休暇．残数付与後.日数 */
	@Column(name = "SP_REMAINING_DAYS_AFTER_1")
	public Double afterRemainDays1;

	@Column(name = "SP_REMAINING_DAYS_AFTER_2")
	public Double afterRemainDays2;

	@Column(name = "SP_REMAINING_DAYS_AFTER_3")
	public Double afterRemainDays3;

	@Column(name = "SP_REMAINING_DAYS_AFTER_4")
	public Double afterRemainDays4;

	@Column(name = "SP_REMAINING_DAYS_AFTER_5")
	public Double afterRemainDays5;

	@Column(name = "SP_REMAINING_DAYS_AFTER_6")
	public Double afterRemainDays6;

	@Column(name = "SP_REMAINING_DAYS_AFTER_7")
	public Double afterRemainDays7;

	@Column(name = "SP_REMAINING_DAYS_AFTER_8")
	public Double afterRemainDays8;

	@Column(name = "SP_REMAINING_DAYS_AFTER_9")
	public Double afterRemainDays9;

	@Column(name = "SP_REMAINING_DAYS_AFTER_10")
	public Double afterRemainDays10;

	@Column(name = "SP_REMAINING_DAYS_AFTER_11")
	public Double afterRemainDays11;

	@Column(name = "SP_REMAINING_DAYS_AFTER_12")
	public Double afterRemainDays12;

	@Column(name = "SP_REMAINING_DAYS_AFTER_13")
	public Double afterRemainDays13;

	@Column(name = "SP_REMAINING_DAYS_AFTER_14")
	public Double afterRemainDays14;

	@Column(name = "SP_REMAINING_DAYS_AFTER_15")
	public Double afterRemainDays15;

	@Column(name = "SP_REMAINING_DAYS_AFTER_16")
	public Double afterRemainDays16;

	@Column(name = "SP_REMAINING_DAYS_AFTER_17")
	public Double afterRemainDays17;

	@Column(name = "SP_REMAINING_DAYS_AFTER_18")
	public Double afterRemainDays18;

	@Column(name = "SP_REMAINING_DAYS_AFTER_19")
	public Double afterRemainDays19;

	@Column(name = "SP_REMAINING_DAYS_AFTER_20")
	public Double afterRemainDays20;

	/** 特別休暇月別残数データ．特別休暇．残数付与後.時間 */

	@Column(name = "SP_REMAINING_MINUTES_AFTER_1")
	public Integer afterRemainMinutes1;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_2")
	public Integer afterRemainMinutes2;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_3")
	public Integer afterRemainMinutes3;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_4")
	public Integer afterRemainMinutes4;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_5")
	public Integer afterRemainMinutes5;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_6")
	public Integer afterRemainMinutes6;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_7")
	public Integer afterRemainMinutes7;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_8")
	public Integer afterRemainMinutes8;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_9")
	public Integer afterRemainMinutes9;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_10")
	public Integer afterRemainMinutes10;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_11")
	public Integer afterRemainMinutes11;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_12")
	public Integer afterRemainMinutes12;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_13")
	public Integer afterRemainMinutes13;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_14")
	public Integer afterRemainMinutes14;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_15")
	public Integer afterRemainMinutes15;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_16")
	public Integer afterRemainMinutes16;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_17")
	public Integer afterRemainMinutes17;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_18")
	public Integer afterRemainMinutes18;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_19")
	public Integer afterRemainMinutes19;

	@Column(name = "SP_REMAINING_MINUTES_AFTER_20")
	public Integer afterRemainMinutes20;

	/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_1")
	public Double afterFactRemainDays1;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_2")
	public Double afterFactRemainDays2;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_3")
	public Double afterFactRemainDays3;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_4")
	public Double afterFactRemainDays4;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_5")
	public Double afterFactRemainDays5;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_6")
	public Double afterFactRemainDays6;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_7")
	public Double afterFactRemainDays7;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_8")
	public Double afterFactRemainDays8;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_9")
	public Double afterFactRemainDays9;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_10")
	public Double afterFactRemainDays10;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_11")
	public Double afterFactRemainDays11;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_12")
	public Double afterFactRemainDays12;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_13")
	public Double afterFactRemainDays13;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_14")
	public Double afterFactRemainDays14;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_15")
	public Double afterFactRemainDays15;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_16")
	public Double afterFactRemainDays16;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_17")
	public Double afterFactRemainDays17;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_18")
	public Double afterFactRemainDays18;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_19")
	public Double afterFactRemainDays19;

	@Column(name = "SP_FACT_REMAINING_DAYS_AFTER_20")
	public Double afterFactRemainDays20;

	/** 特別休暇月別残数データ．実特別休暇．残数付与後. 時間 */
	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_1")
	public Double afterFactRemainMinutes1;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_2")
	public Double afterFactRemainMinutes2;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_3")
	public Double afterFactRemainMinutes3;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_4")
	public Double afterFactRemainMinutes4;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_5")
	public Double afterFactRemainMinutes5;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_6")
	public Double afterFactRemainMinutes6;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_7")
	public Double afterFactRemainMinutes7;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_8")
	public Double afterFactRemainMinutes8;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_9")
	public Double afterFactRemainMinutes9;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_10")
	public Double afterFactRemainMinutes10;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_11")
	public Double afterFactRemainMinutes11;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_12")
	public Integer afterFactRemainMinutes12;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_13")
	public Double afterFactRemainMinutes13;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_14")
	public Double afterFactRemainMinutes14;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_15")
	public Double afterFactRemainMinutes15;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_16")
	public Double afterFactRemainMinutes16;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_17")
	public Double afterFactRemainMinutes17;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_18")
	public Double afterFactRemainMinutes18;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_19")
	public Double afterFactRemainMinutes19;

	@Column(name = "SP_FACT_REMAINING_MINUTES_AFTER_20")
	public Double afterFactRemainMinutes20;

	/** 特別休暇月別残数データ．特別休暇．未消化数．未消化日数.未消化日数 */
	@Column(name = "SP_NOT_USED_DAYS_1")
	public double notUseDays1;

	@Column(name = "SP_NOT_USED_DAYS_2")
	public double notUseDays2;

	@Column(name = "SP_NOT_USED_DAYS_3")
	public double notUseDays3;

	@Column(name = "SP_NOT_USED_DAYS_4")
	public double notUseDays4;

	@Column(name = "SP_NOT_USED_DAYS_5")
	public double notUseDays5;

	@Column(name = "SP_NOT_USED_DAYS_6")
	public double notUseDays6;

	@Column(name = "SP_NOT_USED_DAYS_7")
	public double notUseDays7;

	@Column(name = "SP_NOT_USED_DAYS_8")
	public double notUseDays8;

	@Column(name = "SP_NOT_USED_DAYS_9")
	public double notUseDays9;

	@Column(name = "SP_NOT_USED_DAYS_10")
	public double notUseDays10;

	@Column(name = "SP_NOT_USED_DAYS_11")
	public double notUseDays11;

	@Column(name = "SP_NOT_USED_DAYS_12")
	public double notUseDays12;

	@Column(name = "SP_NOT_USED_DAYS_13")
	public double notUseDays13;

	@Column(name = "SP_NOT_USED_DAYS_14")
	public double notUseDays14;

	@Column(name = "SP_NOT_USED_DAYS_15")
	public double notUseDays15;

	@Column(name = "SP_NOT_USED_DAYS_16")
	public double notUseDays16;

	@Column(name = "SP_NOT_USED_DAYS_17")
	public double notUseDays17;

	@Column(name = "SP_NOT_USED_DAYS_18")
	public double notUseDays18;

	@Column(name = "SP_NOT_USED_DAYS_19")
	public double notUseDays19;

	@Column(name = "SP_NOT_USED_DAYS_20")
	public double notUseDays20;

	/** 特別休暇月別残数データ．特別休暇．未消化数．未消化時間.未消化時間 */
	@Column(name = "SP_NOT_USED_MINUTES_1")
	public Integer notUseMinutes1;

	@Column(name = "SP_NOT_USED_MINUTES_2")
	public Integer notUseMinutes2;

	@Column(name = "SP_NOT_USED_MINUTES_3")
	public Integer notUseMinutes3;

	@Column(name = "SP_NOT_USED_MINUTES_4")
	public Integer notUseMinutes4;

	@Column(name = "SP_NOT_USED_MINUTES_5")
	public Integer notUseMinutes5;

	@Column(name = "SP_NOT_USED_MINUTES_6")
	public Integer notUseMinutes6;

	@Column(name = "SP_NOT_USED_MINUTES_7")
	public Integer notUseMinutes7;

	@Column(name = "SP_NOT_USED_MINUTES_8")
	public Integer notUseMinutes8;

	@Column(name = "SP_NOT_USED_MINUTES_9")
	public Integer notUseMinutes9;

	@Column(name = "SP_NOT_USED_MINUTES_10")
	public Integer notUseMinutes10;

	@Column(name = "SP_NOT_USED_MINUTES_11")
	public Integer notUseMinutes11;

	@Column(name = "SP_NOT_USED_MINUTES_12")
	public Integer notUseMinutes12;

	@Column(name = "SP_NOT_USED_MINUTES_13")
	public Integer notUseMinutes13;

	@Column(name = "SP_NOT_USED_MINUTES_14")
	public Integer notUseMinutes14;

	@Column(name = "SP_NOT_USED_MINUTES_15")
	public Integer notUseMinutes15;

	@Column(name = "SP_NOT_USED_MINUTES_16")
	public Integer notUseMinutes16;

	@Column(name = "SP_NOT_USED_MINUTES_17")
	public Integer notUseMinutes17;

	@Column(name = "SP_NOT_USED_MINUTES_18")
	public Integer notUseMinutes18;

	@Column(name = "SP_NOT_USED_MINUTES_19")
	public Integer notUseMinutes19;

	@Column(name = "SP_NOT_USED_MINUTES_20")
	public Integer notUseMinutes20;

	/** 付与区分 */
	@Column(name = "SP_GRANT_ATR_1")
	public int grantAtr1;

	@Column(name = "SP_GRANT_ATR_2")
	public int grantAtr2;

	@Column(name = "SP_GRANT_ATR_3")
	public int grantAtr3;

	@Column(name = "SP_GRANT_ATR_4")
	public int grantAtr4;

	@Column(name = "SP_GRANT_ATR_5")
	public int grantAtr5;

	@Column(name = "SP_GRANT_ATR_6")
	public int grantAtr6;

	@Column(name = "SP_GRANT_ATR_7")
	public int grantAtr7;

	@Column(name = "SP_GRANT_ATR_8")
	public int grantAtr8;

	@Column(name = "SP_GRANT_ATR_9")
	public int grantAtr9;

	@Column(name = "SP_GRANT_ATR_10")
	public int grantAtr10;

	@Column(name = "SP_GRANT_ATR_11")
	public int grantAtr11;

	@Column(name = "SP_GRANT_ATR_12")
	public int grantAtr12;

	@Column(name = "SP_GRANT_ATR_13")
	public int grantAtr13;

	@Column(name = "SP_GRANT_ATR_14")
	public int grantAtr14;

	@Column(name = "SP_GRANT_ATR_15")
	public int grantAtr15;

	@Column(name = "SP_GRANT_ATR_16")
	public int grantAtr16;

	@Column(name = "SP_GRANT_ATR_17")
	public int grantAtr17;

	@Column(name = "SP_GRANT_ATR_18")
	public int grantAtr18;

	@Column(name = "SP_GRANT_ATR_19")
	public int grantAtr19;

	@Column(name = "SP_GRANT_ATR_20")
	public int grantAtr20;

	/** 特別休暇月別残数データ．特別休暇付与情報.付与日数 */
	@Column(name = "SP_GRANT_DAYS_1")
	public Integer grantDays1;

	@Column(name = "SP_GRANT_DAYS_2")
	public Integer grantDays2;

	@Column(name = "SP_GRANT_DAYS_3")
	public Integer grantDays3;

	@Column(name = "SP_GRANT_DAYS_4")
	public Integer grantDays4;

	@Column(name = "SP_GRANT_DAYS_5")
	public Integer grantDays5;

	@Column(name = "SP_GRANT_DAYS_6")
	public Integer grantDays6;

	@Column(name = "SP_GRANT_DAYS_7")
	public Integer grantDays7;

	@Column(name = "SP_GRANT_DAYS_8")
	public Integer grantDays8;

	@Column(name = "SP_GRANT_DAYS_9")
	public Integer grantDays9;

	@Column(name = "SP_GRANT_DAYS_10")
	public Integer grantDays10;

	@Column(name = "SP_GRANT_DAYS_11")
	public Integer grantDays11;

	@Column(name = "SP_GRANT_DAYS_12")
	public Integer grantDays12;

	@Column(name = "SP_GRANT_DAYS_13")
	public Integer grantDays13;

	@Column(name = "SP_GRANT_DAYS_14")
	public Integer grantDays14;

	@Column(name = "SP_GRANT_DAYS_15")
	public Integer grantDays15;

	@Column(name = "SP_GRANT_DAYS_16")
	public Integer grantDays16;

	@Column(name = "SP_GRANT_DAYS_17")
	public Integer grantDays17;

	@Column(name = "SP_GRANT_DAYS_18")
	public Integer grantDays18;

	@Column(name = "SP_GRANT_DAYS_19")
	public Integer grantDays19;

	@Column(name = "SP_GRANT_DAYS_20")
	public Integer grantDays20;

	/* KRCDT_MON_DAYOFF_REMAIN */
	/** 発生日数 */
	@Column(name = "DAYOFF_OCCURRED_DAYS")
	public double dayOffOccurredDays;

	/** 発生時間 */
	@Column(name = "DAYOFF_OCCURRED_TIMES")
	public Integer dayOffOccurredTimes;

	/** 使用日数 */
	@Column(name = "DAYOFF_USED_DAYS")
	public double dayOffUsedDays;

	/** 使用時間 */
	@Column(name = "DAYOFF_USED_MINUTES")
	public Integer dayOffUsedMinutes;

	/** 残日数 */
	@Column(name = "DAYOFF_REMAINING_DAYS")
	public double dayOffRemainingDays;

	/** 残時間 */
	@Column(name = "DAYOFF_REMAINING_MINUTES")
	public Integer dayOffRemainingMinutes;

	/** 繰越日数 */
	@Column(name = "DAYOFF_CARRYFORWARD_DAYS")
	public double dayOffCarryforwardDays;

	/** 繰越時間 */
	@Column(name = "DAYOFF_CARRYFORWARD_MINUTES")
	public Integer dayOffCarryforwardMinutes;

	/** 未消化日数 */
	@Column(name = "DAYOFF_UNUSED_DAYS")
	public double dayOffUnUsedDays;

	/** 未消化時間 */
	@Column(name = "DAYOFF_UNUSED_TIMES")
	public Integer dayOffUnUsedTimes;

	/* KRCDT_MON_SUBOFHD_REMAIN */
	/** 発生日数 */
	@Column(name = "SUBOFHD_OCCURRED_DAYS")
	public double subofHdOccurredDays;
	/** 使用日数 */
	@Column(name = "SUBOFHD_USED_DAYS")
	public double subofHdUsedDays;
	/** 残日数 */
	@Column(name = "SUBOFHD_REMAINING_DAYS")
	public double subofHdRemainingDays;
	/** 繰越日数 */
	@Column(name = "SUBOFHD_CARRYFORWARD_DAYS")
	public double subofHdCarryForWardDays;
	/** 未消化日数 */
	@Column(name = "SUBOFHD_UNUSED_DAYS")
	public double subofHdUnUsedDays;

	/* KRCDT_MON_CHILD_HD_REMAIN */
	//TODO - thiếu domain
	@Column(name = "CHILD_USED_DAYS")
	public double childUsedDays;

	@Column(name = "CHILD_USED_DAYS_BEFORE")
	public double childBeforeCareUsedDays;

	@Column(name = "CHILD_USED_DAYS_AFTER")
	public double childAfterCareUsedDays;

	@Column(name = "CHILD_USED_MINUTES")
	public int childUsedMinutes;

	@Column(name = "CHILD_USED_MINUTES_BEFORE")
	public int childBeforeCareUsedMinutes;

	@Column(name = "CHILD_USED_MINUTES_AFTER")
	public int childAfterCareUsedMinutes;
	
	
	/* KRCDT_MON_CARE_HD_REMAIN */
	// TODO thiếu domain
	@Column(name = "CARE_USED_DAYS")
	public double careUsedDays;

	@Column(name = "CARE_USED_DAYS_BEFORE")
	public double beforeCareUsedDays;

	@Column(name = "CARE_USED_DAYS_AFTER")
	public double afterCareUsedDays;

	@Column(name = "CARE_USED_MINUTES")
	public int careUsedMinutes;

	@Column(name = "CARE_USED_MINUTES_BEFORE")
	public int beforeCareUsedMinutes;

	@Column(name = "CARE_USED_MINUTES_AFTER")
	public int afterCareUsedMinutes;
	
	@Override
	protected Object getKey() {
		return krcdtMonRemainPk;
	}

	public 	MonthMergeKey toDomainKey() {
		MonthMergeKey key = new MonthMergeKey();
		key.setEmployeeId(this.krcdtMonRemainPk.getEmployeeId());
		key.setYearMonth(new YearMonth(this.krcdtMonRemainPk.getYearMonth()));
		key.setClosureId(EnumAdaptor.valueOf(this.krcdtMonRemainPk.getClosureId(), ClosureId.class));
		key.setClosureDate(new ClosureDate(this.krcdtMonRemainPk.getClosureDay(),
			(this.krcdtMonRemainPk.getIsLastDay() == 1)));
		return key;
		
	}
	public void toEntityRemainMerge(RemainMerge domain) {
          this.toEntityMonAnnleaRemain(domain.getAnnLeaRemNumEachMonth());
          this.toEntityRsvLeaRemNumEachMonth(domain.getRsvLeaRemNumEachMonth());
          this.toEntitySpeRemain(domain.getSpecialHolidayRemainDataMerge());
          this.toEntityDayOffRemainDayAndTimes(domain.getMonthlyDayoffRemainData());
          this.toEntityAbsenceLeaveRemainData(domain.getAbsenceLeaveRemainData());
	}

	/* KRCDT_MON_ANNLEA_REMAIN - エンティティ：年休月別残数データ */
	public void toEntityMonAnnleaRemain(AnnLeaRemNumEachMonth domain) {
		// Optional列の初期化
		this.annleaUsedDaysAfter = null;
		this.annleaUsedMinutes = null;
		this.annleaUsedMinutesBefore = null;
		this.annleaUsedMinutesAfter = null;
		this.annleaUsedTimes = null;
		this.annleaFactUsedDaysAfter = null;
		this.annleaFactUsedMinutes = null;
		this.annleaFactUsedMinutesBefore = null;
		this.annleaFactUsedMinutesAfter = null;
		this.annleaFactUsedTimes = null;
		this.annleaRemainingMinutes = null;
		this.annleaFactRemainingMinutes = null;
		this.annleaRemainingMinutesBefore = null;
		this.annleaFactRemainingMinutesBefore = null;
		this.annleaRemainingDaysAfter = null;
		this.annleaRemainingMinutesAfter = null;
		this.annleaFactRemainingDaysAfter = null;
		this.annleaFactRemainingMinutesAfter = null;
		this.annleaUnusedMinutes = null;
		this.annleaHalfUsedTimes = null;
		this.annleaHalfUsedTimesBefore = null;
		this.annleaHalfUsedTimesAfter = null;
		this.annleaHalfRemainingTimes = null;
		this.annleaHalfRemainingTimesBefore = null;
		this.annleaHalfRemainingTimesAfter = null;
		this.annleaFactHalfUsedTimes = null;
		this.annleaFactHalfUsedTimesBefore = null;
		this.annleaFactHalfUsedTimesAfter = null;
		this.annleaFactHalfRemainingTimes = null;
		this.annleaFactHalfRemainingTimesBefore = null;
		this.annleaFactHalfRemainingTimesAfter = null;
		this.annleaTimeRemainingMinutes = null;
		this.annleaTimeRemainingMinutesBefore = null;
		this.annleaTimeRemainingMinutesAfter = null;
		this.annleaFactTimeRemainingMinutes = null;
		this.annleaFactTimeRemainingMinutesBefore = null;
		this.annleaFactTimeRemainingMinutesAfter = null;
		this.annleaGrantDays = null;
		this.annleaGrantPredeterminedDays = null;
		this.annleaGrantLaborDays = null;
		this.annleaGrantDeductionDays = null;
		this.annleaDeductionDaysBefore = null;
		this.annleaDeductionDaysAfter = null;
		this.annleaAttendanceRate = null;

		val normal = domain.getAnnualLeave();
		val normalUsed = normal.getUsedNumber();
		val real = domain.getRealAnnualLeave();
		val realUsed = real.getUsedNumber();

		this.closureStatus = domain.getClosureStatus().value;
		this.startDate = domain.getClosurePeriod().start();
		this.endDate = domain.getClosurePeriod().end();

		// 年休：使用数
		this.annleaUsedDays = normalUsed.getUsedDays().getUsedDays().v();
		this.annleaUsedDaysBefore = normalUsed.getUsedDays().getUsedDaysBeforeGrant().v();
		if (normalUsed.getUsedDays().getUsedDaysAfterGrant().isPresent()) {
			this.annleaUsedDaysAfter = normalUsed.getUsedDays().getUsedDaysAfterGrant().get().v();
		}
		if (normalUsed.getUsedTime().isPresent()) {
			val normalUsedTime = normalUsed.getUsedTime().get();
			this.annleaUsedMinutes = normalUsedTime.getUsedTime().v();
			this.annleaUsedMinutesBefore = normalUsedTime.getUsedTimeBeforeGrant().v();
			if (normalUsedTime.getUsedTimeAfterGrant().isPresent()) {
				this.annleaUsedMinutesAfter = normalUsedTime.getUsedTimeAfterGrant().get().v();
			}
			this.annleaUsedTimes = normalUsedTime.getUsedTimes().v();
		}

		// 実年休：使用数
		this.annleaFactUsedDays = realUsed.getUsedDays().getUsedDays().v();
		this.annleaFactUsedDaysBefore = realUsed.getUsedDays().getUsedDaysBeforeGrant().v();
		if (realUsed.getUsedDays().getUsedDaysAfterGrant().isPresent()) {
			this.annleaFactUsedDaysAfter = realUsed.getUsedDays().getUsedDaysAfterGrant().get().v();
		}
		if (realUsed.getUsedTime().isPresent()) {
			val realUsedTime = realUsed.getUsedTime().get();
			this.annleaFactUsedMinutes = realUsedTime.getUsedTime().v();
			this.annleaFactUsedMinutesBefore = realUsedTime.getUsedTimeBeforeGrant().v();
			if (realUsedTime.getUsedTimeAfterGrant().isPresent()) {
				this.annleaFactUsedMinutesAfter = realUsedTime.getUsedTimeAfterGrant().get().v();
			}
			this.annleaFactUsedTimes = realUsedTime.getUsedTimes().v();
		}

		// 年休：残数
		this.annleaRemainingDays = normal.getRemainingNumber().getTotalRemainingDays().v();
		if (normal.getRemainingNumber().getTotalRemainingTime().isPresent()) {
			this.annleaRemainingMinutes = normal.getRemainingNumber().getTotalRemainingTime().get().v();
		}
		this.annleaRemainingDaysBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (normal.getRemainingNumberBeforeGrant().getTotalRemainingTime().isPresent()) {
			this.annleaRemainingMinutesBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingTime().get()
					.v();
		}
		if (normal.getRemainingNumberAfterGrant().isPresent()) {
			val normalRemainAfter = normal.getRemainingNumberAfterGrant().get();
			this.annleaRemainingDaysAfter = normalRemainAfter.getTotalRemainingDays().v();
			if (normalRemainAfter.getTotalRemainingTime().isPresent()) {
				this.annleaRemainingMinutesAfter = normalRemainAfter.getTotalRemainingTime().get().v();
			}
		}

		// 実年休：残数
		this.annleaFactRemainingDays = real.getRemainingNumber().getTotalRemainingDays().v();
		if (real.getRemainingNumber().getTotalRemainingTime().isPresent()) {
			this.annleaFactRemainingMinutes = real.getRemainingNumber().getTotalRemainingTime().get().v();
		}
		this.annleaFactRemainingDaysBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (real.getRemainingNumberBeforeGrant().getTotalRemainingTime().isPresent()) {
			this.annleaFactRemainingMinutesBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingTime().get()
					.v();
		}
		if (real.getRemainingNumberAfterGrant().isPresent()) {
			val realRemainAfter = real.getRemainingNumberAfterGrant().get();
			this.annleaFactRemainingDaysAfter = realRemainAfter.getTotalRemainingDays().v();
			if (realRemainAfter.getTotalRemainingTime().isPresent()) {
				this.annleaFactRemainingMinutesAfter = realRemainAfter.getTotalRemainingTime().get().v();
			}
		}

		// 年休：未消化数
		val normalUndigest = normal.getUndigestedNumber();
		this.annleaUnusedDays = normalUndigest.getUndigestedDays().getUndigestedDays().v();
		if (normalUndigest.getUndigestedTime().isPresent()) {
			this.annleaUnusedMinutes = normalUndigest.getUndigestedTime().get().getUndigestedTime().v();
		}

		// 出勤率日数
		val attendanceRateDays = domain.getAttendanceRateDays();
		this.annleaPredeterminedDays = attendanceRateDays.getPrescribedDays().v().intValue();
		this.annleaLaborDays = attendanceRateDays.getWorkingDays().v().intValue();
		this.annleaDeductionDays = attendanceRateDays.getDeductedDays().v().intValue();

		// 半日年休
		if (domain.getHalfDayAnnualLeave().isPresent()) {
			val normalHalf = domain.getHalfDayAnnualLeave().get();
			this.annleaHalfUsedTimes = normalHalf.getUsedNum().getTimes().v();
			this.annleaHalfUsedTimesBefore = normalHalf.getUsedNum().getTimesBeforeGrant().v();
			if (normalHalf.getUsedNum().getTimesAfterGrant().isPresent()) {
				this.annleaHalfUsedTimesAfter = normalHalf.getUsedNum().getTimesAfterGrant().get().v();
			}
			this.annleaHalfRemainingTimes = normalHalf.getRemainingNum().getTimes().v();
			this.annleaHalfRemainingTimesBefore = normalHalf.getRemainingNum().getTimesBeforeGrant().v();
			if (normalHalf.getRemainingNum().getTimesAfterGrant().isPresent()) {
				this.annleaHalfRemainingTimesAfter = normalHalf.getRemainingNum().getTimesAfterGrant().get().v();
			}
		}

		// 実半日年休
		if (domain.getRealHalfDayAnnualLeave().isPresent()) {
			val realHalf = domain.getRealHalfDayAnnualLeave().get();
			this.annleaFactHalfUsedTimes = realHalf.getUsedNum().getTimes().v();
			this.annleaFactHalfUsedTimesBefore = realHalf.getUsedNum().getTimesBeforeGrant().v();
			if (realHalf.getUsedNum().getTimesAfterGrant().isPresent()) {
				this.annleaFactHalfUsedTimesAfter = realHalf.getUsedNum().getTimesAfterGrant().get().v();
			}
			this.annleaFactHalfRemainingTimes = realHalf.getRemainingNum().getTimes().v();
			this.annleaFactHalfRemainingTimesBefore = realHalf.getRemainingNum().getTimesBeforeGrant().v();
			if (realHalf.getRemainingNum().getTimesAfterGrant().isPresent()) {
				this.annleaFactHalfRemainingTimesAfter = realHalf.getRemainingNum().getTimesAfterGrant().get().v();
			}
		}

		// 上限残時間
		if (domain.getMaxRemainingTime().isPresent()) {
			val normalMax = domain.getMaxRemainingTime().get();
			this.annleaTimeRemainingMinutes = normalMax.getTime().v();
			this.annleaTimeRemainingMinutesBefore = normalMax.getTimeBeforeGrant().v();
			if (normalMax.getTimeAfterGrant().isPresent()) {
				this.annleaTimeRemainingMinutesAfter = normalMax.getTimeAfterGrant().get().v();
			}
		}

		// 実上限残時間
		if (domain.getRealMaxRemainingTime().isPresent()) {
			val realMax = domain.getRealMaxRemainingTime().get();
			this.annleaFactTimeRemainingMinutes = realMax.getTime().v();
			this.annleaFactTimeRemainingMinutesBefore = realMax.getTimeBeforeGrant().v();
			if (realMax.getTimeAfterGrant().isPresent()) {
				this.annleaFactTimeRemainingMinutesAfter = realMax.getTimeAfterGrant().get().v();
			}
		}

		// 付与区分
		this.annleaGrantAtr = (domain.isGrantAtr() ? 1 : 0);

		// 年休付与情報
		if (domain.getAnnualLeaveGrant().isPresent()) {
			val grantInfo = domain.getAnnualLeaveGrant().get();
			this.annleaGrantDays = grantInfo.getGrantDays().v();
			this.annleaGrantPredeterminedDays = grantInfo.getGrantPrescribedDays().v().intValue();
			this.annleaGrantLaborDays = grantInfo.getGrantWorkingDays().v().intValue();
			this.annleaGrantDeductionDays = grantInfo.getGrantDeductedDays().v().intValue();
			this.annleaDeductionDaysBefore = grantInfo.getDeductedDaysBeforeGrant().v().intValue();
			this.annleaDeductionDaysAfter = grantInfo.getDeductedDaysAfterGrant().v().intValue();
			this.annleaAttendanceRate = grantInfo.getAttendanceRate().v().doubleValue();
		}

	}

	/** KRCDT_MON_RSVLEA_REMAIN **/
	public void toEntityRsvLeaRemNumEachMonth(RsvLeaRemNumEachMonth domain) {

		// Optional列の初期化
		this.rsvleaUsedDaysAfter = null;
		this.rsvleaFactUsedDaysAfter = null;
		this.rsvleaRemainingDaysAfter = null;
		this.rsvleaFactRemainingDaysAfter = null;
		this.rsvleaGrantDays = null;

		val normal = domain.getReserveLeave();
		val normalUsed = normal.getUsedNumber();
		val real = domain.getRealReserveLeave();
		val realUsed = real.getUsedNumber();
		// 積立年休：使用数
		this.rsvleaUsedDays = normalUsed.getUsedDays().v();
		this.rsvleaUsedDaysBefore = normalUsed.getUsedDaysBeforeGrant().v();
		if (normalUsed.getUsedDaysAfterGrant().isPresent()) {
			this.rsvleaUsedDaysAfter = normalUsed.getUsedDaysAfterGrant().get().v();
		}

		// 実積立年休：使用数
		this.rsvleaFactUsedDays = realUsed.getUsedDays().v();
		this.rsvleaFactUsedDaysBefore = realUsed.getUsedDaysBeforeGrant().v();
		if (realUsed.getUsedDaysAfterGrant().isPresent()) {
			this.rsvleaFactUsedDaysAfter = realUsed.getUsedDaysAfterGrant().get().v();
		}

		// 積立年休：残数
		this.rsvleaRemainingDays = normal.getRemainingNumber().getTotalRemainingDays().v();
		this.rsvleaRemainingDaysBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (normal.getRemainingNumberAfterGrant().isPresent()) {
			val normalRemainAfter = normal.getRemainingNumberAfterGrant().get();
			this.rsvleaRemainingDaysAfter = normalRemainAfter.getTotalRemainingDays().v();
		}

		// 実積立年休：残数
		this.rsvleaFactRemainingDays = real.getRemainingNumber().getTotalRemainingDays().v();
		this.rsvleaFactRemainingDaysBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (real.getRemainingNumberAfterGrant().isPresent()) {
			val realRemainAfter = real.getRemainingNumberAfterGrant().get();
			this.rsvleaFactRemainingDaysAfter = realRemainAfter.getTotalRemainingDays().v();
		}

		// 積立年休：未消化数
		val normalUndigest = normal.getUndigestedNumber();
		this.rsvleaNotUsedDays = normalUndigest.getUndigestedDays().v();

		// 付与区分
		this.rsvleaGrantAtr = (domain.isGrantAtr() ? 1 : 0);

		// 積立年休付与情報
		if (domain.getReserveLeaveGrant().isPresent()) {
			val grantInfo = domain.getReserveLeaveGrant().get();
			this.rsvleaGrantDays = grantInfo.getGrantDays().v();
		}
	}

	/** KRCDT_MON_SP_REMAIN **/
	public void toEntitySpeRemain(SpecialHolidayRemainDataMerge domain) {
		this.toEntityMonthSpeRemain1(domain.getSpecialHolidayRemainData1());
		this.toEntityMonthSpeRemain2(domain.getSpecialHolidayRemainData2());
		this.toEntityMonthSpeRemain3(domain.getSpecialHolidayRemainData3());
		this.toEntityMonthSpeRemain4(domain.getSpecialHolidayRemainData4());
		this.toEntityMonthSpeRemain5(domain.getSpecialHolidayRemainData5());
		this.toEntityMonthSpeRemain6(domain.getSpecialHolidayRemainData6());
		this.toEntityMonthSpeRemain7(domain.getSpecialHolidayRemainData7());
		this.toEntityMonthSpeRemain8(domain.getSpecialHolidayRemainData8());
		this.toEntityMonthSpeRemain9(domain.getSpecialHolidayRemainData9());
		this.toEntityMonthSpeRemain10(domain.getSpecialHolidayRemainData10());
		this.toEntityMonthSpeRemain11(domain.getSpecialHolidayRemainData11());
		this.toEntityMonthSpeRemain12(domain.getSpecialHolidayRemainData12());
		this.toEntityMonthSpeRemain13(domain.getSpecialHolidayRemainData13());
		this.toEntityMonthSpeRemain14(domain.getSpecialHolidayRemainData14());
		this.toEntityMonthSpeRemain15(domain.getSpecialHolidayRemainData15());
		this.toEntityMonthSpeRemain16(domain.getSpecialHolidayRemainData16());
		this.toEntityMonthSpeRemain17(domain.getSpecialHolidayRemainData17());
		this.toEntityMonthSpeRemain18(domain.getSpecialHolidayRemainData18());
		this.toEntityMonthSpeRemain19(domain.getSpecialHolidayRemainData19());
		this.toEntityMonthSpeRemain20(domain.getSpecialHolidayRemainData20());
	}
	
	
	/** KRCDT_MON_DAYOFF_REMAIN **/
	public void toEntityDayOffRemainDayAndTimes(MonthlyDayoffRemainData domain) {
		
		this.dayOffOccurredTimes = null;
		this.dayOffUsedMinutes = null;
		this.dayOffRemainingMinutes = null;
		this.dayOffCarryforwardMinutes  = null;
		this.dayOffUnUsedTimes = null;
		
		/**	発生 */
		val occurrenceDayTimes = domain.getOccurrenceDayTimes();
		/**	使用日数 */
		val useDayTimes = domain.getUseDayTimes();
		/**	残日数, 残時間 */
		val remainingDayTimes = domain.getRemainingDayTimes();
		/**	繰越日数, 	繰越時間 */
		val carryForWardDayTimes = domain.getCarryForWardDayTimes();
		/**	未消化日数, 未消化時間 */
		val unUsedDayTimes = domain.getUnUsedDayTimes();
		
		if(occurrenceDayTimes != null) {
			/**	日数 */
			this.dayOffOccurredDays = occurrenceDayTimes.getDay() == null? 0.0d: occurrenceDayTimes.getDay().v().doubleValue() ;
			/**	時間 */
			Optional<RemainDataTimesMonth> timeOptional = occurrenceDayTimes.getTime();
			if(timeOptional.isPresent()) {
				RemainDataTimesMonth time = timeOptional.get();
				this.dayOffOccurredTimes = time == null? null: time.v();
			}
		}
		
		if(useDayTimes != null) {
			/**	日数 */
			this.dayOffUsedDays = useDayTimes.getDay() == null? 0.0d: useDayTimes.getDay().v().doubleValue() ;
			/**	時間 */
			Optional<RemainDataTimesMonth> timeOptional = useDayTimes.getTime();
			if(timeOptional.isPresent()) {
				RemainDataTimesMonth time = timeOptional.get();
				this.dayOffUsedMinutes = time == null? null: time.v();
			}
			
		}
		
		if(remainingDayTimes != null) {
			/**	残日数 */
			this.dayOffRemainingDays = remainingDayTimes.getDays() == null? 0.0d: remainingDayTimes.getDays().v().doubleValue() ;
			/**残時間	 */
			Optional<RemainingMinutes> timeOptional = remainingDayTimes.getTimes();
			if(timeOptional.isPresent()) {
				RemainingMinutes time = timeOptional.get();
				this.dayOffRemainingMinutes = time == null? null: time.v();
			}
			
		}
		

		if(carryForWardDayTimes != null) {
			/**	残日数 */
			this.dayOffCarryforwardDays = remainingDayTimes.getDays() == null? 0.0d: remainingDayTimes.getDays().v().doubleValue() ;
			/**残時間	 */
			Optional<RemainingMinutes> timeOptional = remainingDayTimes.getTimes();
			if(timeOptional.isPresent()) {
				RemainingMinutes time = timeOptional.get();
				this.dayOffCarryforwardMinutes = time == null? null: time.v();
			}
			
		}

		if(unUsedDayTimes != null) {
			/**	日数 */
			this.dayOffUnUsedDays = occurrenceDayTimes.getDay() == null? 0.0d: occurrenceDayTimes.getDay().v().doubleValue() ;
			/**	時間 */
			Optional<RemainDataTimesMonth> timeOptional = occurrenceDayTimes.getTime();
			if(timeOptional.isPresent()) {
				RemainDataTimesMonth time = timeOptional.get();
				this.dayOffUnUsedTimes = time == null? null: time.v();
			}
		}
	}
	
	/**KRCDT_MON_SUBOFHD_REMAIN**/
	public void toEntityAbsenceLeaveRemainData(AbsenceLeaveRemainData domain) {
		this.subofHdOccurredDays = domain.getOccurredDay().v();
		this.subofHdUsedDays = domain.getUsedDays().v();
		this.subofHdRemainingDays = domain.getRemainingDays().v();
		this.subofHdCarryForWardDays = domain.getCarryforwardDays().v();
		this.subofHdUnUsedDays = domain.getUnUsedDays().v();
		
	}
	
	
	
	/** KRCDT_MON_CHILD_HD_REMAIN **/
	public void toEntityMonthChildHolidayRemain() {
		//TODO - code have not domain
		
	}
	
	/** KRCDT_MON_CARE_HD_REMAIN **/
	public void toEntityMonthCareHolidayRemain() {
		//TODO - code have not domain
		
	}


	/** KRCDT_MON_SP_REMAIN **/
	public void toEntityMonthSpeRemain1(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays1 = null;
		this.useMinutes1 = null;
		this.beforeUseMinutes1 = null;
		this.afterUseMinutes1 = null;
		this.useTimes1 = null;
		this.afterFactUseDays1 = null;
		this.factUseMinutes1 = null;
		this.beforeFactUseMinutes1 = null;
		this.afterFactUseMinutes1 = null;
		this.factUseTimes1 = null;
		this.remainMinutes1 = null;
		this.factRemainMinutes1 = null;
		this.beforeRemainMinutes1 = null;
		this.beforeFactRemainMinutes1 = null;
		this.afterRemainDays1 = null;
		this.afterRemainMinutes1 = null;
		this.afterFactRemainDays1 = null;
		this.afterFactRemainMinutes1 = null;
		this.grantDays1 = null;
		this.notUseMinutes1 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays1 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes1 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays1 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes1 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays1 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes1 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays1 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays1 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays1 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes1 = useTimes.getUseTimes().v();
					this.beforeUseMinutes1 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes1 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes1 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays1 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes1 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays1 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes1 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays1 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes1 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays1 = useDays.getUseDays().v();
					this.beforeFactUseDays1 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays1 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes1 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes1 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes1 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes1 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays1 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes1 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr1 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays1 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain2(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays2 = null;
		this.useMinutes2 = null;
		this.beforeUseMinutes2 = null;
		this.afterUseMinutes2 = null;
		this.useTimes2 = null;
		this.afterFactUseDays2 = null;
		this.factUseMinutes2 = null;
		this.beforeFactUseMinutes2 = null;
		this.afterFactUseMinutes2 = null;
		this.factUseTimes2 = null;
		this.remainMinutes2 = null;
		this.factRemainMinutes2 = null;
		this.beforeRemainMinutes2 = null;
		this.beforeFactRemainMinutes2 = null;
		this.afterRemainDays2 = null;
		this.afterRemainMinutes2 = null;
		this.afterFactRemainDays2 = null;
		this.afterFactRemainMinutes2 = null;
		this.grantDays2 = null;
		this.notUseMinutes2 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays2 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes2 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays2 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes2 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays2 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes2 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays2 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays2 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays2 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes2 = useTimes.getUseTimes().v();
					this.beforeUseMinutes2 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes2 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes2 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays2 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes2 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays2 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes2 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays2 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes2 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays2 = useDays.getUseDays().v();
					this.beforeFactUseDays2 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays2 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes2 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes2 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes2 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes2 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays2 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes2 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr2 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays2 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain3(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays3 = null;
		this.useMinutes3 = null;
		this.beforeUseMinutes3 = null;
		this.afterUseMinutes3 = null;
		this.useTimes3 = null;
		this.afterFactUseDays3 = null;
		this.factUseMinutes3 = null;
		this.beforeFactUseMinutes3 = null;
		this.afterFactUseMinutes3 = null;
		this.factUseTimes3 = null;
		this.remainMinutes3 = null;
		this.factRemainMinutes3 = null;
		this.beforeRemainMinutes3 = null;
		this.beforeFactRemainMinutes3 = null;
		this.afterRemainDays3 = null;
		this.afterRemainMinutes3 = null;
		this.afterFactRemainDays3 = null;
		this.afterFactRemainMinutes3 = null;
		this.grantDays3 = null;
		this.notUseMinutes3 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays3 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes3 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays3 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes3 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays3 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes3 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays3 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays3 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays3 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes3 = useTimes.getUseTimes().v();
					this.beforeUseMinutes3 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes3 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes3 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays3 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes3 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays3 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes3 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays3 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes3 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays3 = useDays.getUseDays().v();
					this.beforeFactUseDays3 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays3 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes3 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes3 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes3 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes3 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays3 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes3 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr3 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays3 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain4(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays4 = null;
		this.useMinutes4 = null;
		this.beforeUseMinutes4 = null;
		this.afterUseMinutes4 = null;
		this.useTimes4 = null;
		this.afterFactUseDays4 = null;
		this.factUseMinutes4 = null;
		this.beforeFactUseMinutes4 = null;
		this.afterFactUseMinutes4 = null;
		this.factUseTimes4 = null;
		this.remainMinutes4 = null;
		this.factRemainMinutes4 = null;
		this.beforeRemainMinutes4 = null;
		this.beforeFactRemainMinutes4 = null;
		this.afterRemainDays4 = null;
		this.afterRemainMinutes4 = null;
		this.afterFactRemainDays4 = null;
		this.afterFactRemainMinutes4 = null;
		this.grantDays4 = null;
		this.notUseMinutes4 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays4 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes4 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays4 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes4 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays4 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes4 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays4 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays4 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays4 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes4 = useTimes.getUseTimes().v();
					this.beforeUseMinutes4 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes4 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes4 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays4 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes4 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays4 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes4 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays4 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes4 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays4 = useDays.getUseDays().v();
					this.beforeFactUseDays4 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays4 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes4 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes4 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes4 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes4 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays4 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes4 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr4 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays4 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain5(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays5 = null;
		this.useMinutes5 = null;
		this.beforeUseMinutes5 = null;
		this.afterUseMinutes5 = null;
		this.useTimes5 = null;
		this.afterFactUseDays5 = null;
		this.factUseMinutes5 = null;
		this.beforeFactUseMinutes5 = null;
		this.afterFactUseMinutes5 = null;
		this.factUseTimes5 = null;
		this.remainMinutes5 = null;
		this.factRemainMinutes5 = null;
		this.beforeRemainMinutes5 = null;
		this.beforeFactRemainMinutes5 = null;
		this.afterRemainDays5 = null;
		this.afterRemainMinutes5 = null;
		this.afterFactRemainDays5 = null;
		this.afterFactRemainMinutes5 = null;
		this.grantDays5 = null;
		this.notUseMinutes5 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays5 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes5 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays5 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes5 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays5 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes5 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays5 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays5 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays5 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes5 = useTimes.getUseTimes().v();
					this.beforeUseMinutes5 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes5 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes5 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays5 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes5 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays5 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes5 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays5 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes5 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays5 = useDays.getUseDays().v();
					this.beforeFactUseDays5 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays5 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes5 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes5 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes5 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes5 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays5 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes5 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr5 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays5 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain6(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays6 = null;
		this.useMinutes6 = null;
		this.beforeUseMinutes6 = null;
		this.afterUseMinutes6 = null;
		this.useTimes6 = null;
		this.afterFactUseDays6 = null;
		this.factUseMinutes6 = null;
		this.beforeFactUseMinutes6 = null;
		this.afterFactUseMinutes6 = null;
		this.factUseTimes6 = null;
		this.remainMinutes6 = null;
		this.factRemainMinutes6 = null;
		this.beforeRemainMinutes6 = null;
		this.beforeFactRemainMinutes6 = null;
		this.afterRemainDays6 = null;
		this.afterRemainMinutes6 = null;
		this.afterFactRemainDays6 = null;
		this.afterFactRemainMinutes6 = null;
		this.grantDays6 = null;
		this.notUseMinutes6 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays6 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes6 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays6 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes6 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays6 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes6 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays6 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays6 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays6 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes6 = useTimes.getUseTimes().v();
					this.beforeUseMinutes6 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes6 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes6 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays6 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes6 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays6 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes6 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays6 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes6 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays6 = useDays.getUseDays().v();
					this.beforeFactUseDays6 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays6 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes6 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes6 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes6 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes6 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays6 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes6 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr6 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays6 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain7(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays7 = null;
		this.useMinutes7 = null;
		this.beforeUseMinutes7 = null;
		this.afterUseMinutes7 = null;
		this.useTimes7 = null;
		this.afterFactUseDays7 = null;
		this.factUseMinutes7 = null;
		this.beforeFactUseMinutes7 = null;
		this.afterFactUseMinutes7 = null;
		this.factUseTimes7 = null;
		this.remainMinutes7 = null;
		this.factRemainMinutes7 = null;
		this.beforeRemainMinutes7 = null;
		this.beforeFactRemainMinutes7 = null;
		this.afterRemainDays7 = null;
		this.afterRemainMinutes7 = null;
		this.afterFactRemainDays7 = null;
		this.afterFactRemainMinutes7 = null;
		this.grantDays7 = null;
		this.notUseMinutes7 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays7 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes7 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays7 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes7 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays7 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes7 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays7 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays7 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays7 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes7 = useTimes.getUseTimes().v();
					this.beforeUseMinutes7 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes7 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes7 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays7 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes7 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays7 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes7 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays7 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes7 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays7 = useDays.getUseDays().v();
					this.beforeFactUseDays7 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays7 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes7 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes7 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes7 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes7 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays7 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes7 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr7 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays7 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain8(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays8 = null;
		this.useMinutes8 = null;
		this.beforeUseMinutes8 = null;
		this.afterUseMinutes8 = null;
		this.useTimes8 = null;
		this.afterFactUseDays8 = null;
		this.factUseMinutes8 = null;
		this.beforeFactUseMinutes8 = null;
		this.afterFactUseMinutes8 = null;
		this.factUseTimes8 = null;
		this.remainMinutes8 = null;
		this.factRemainMinutes8 = null;
		this.beforeRemainMinutes8 = null;
		this.beforeFactRemainMinutes8 = null;
		this.afterRemainDays8 = null;
		this.afterRemainMinutes8 = null;
		this.afterFactRemainDays8 = null;
		this.afterFactRemainMinutes8 = null;
		this.grantDays8 = null;
		this.notUseMinutes8 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays8 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes8 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays8 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes8 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays8 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes8 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays8 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays8 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays8 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes8 = useTimes.getUseTimes().v();
					this.beforeUseMinutes8 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes8 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes8 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays8 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes8 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays8 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes8 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays8 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes8 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays8 = useDays.getUseDays().v();
					this.beforeFactUseDays8 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays8 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes8 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes8 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes8 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes8 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays8 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes8 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr8 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays8 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain9(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays9 = null;
		this.useMinutes9 = null;
		this.beforeUseMinutes9 = null;
		this.afterUseMinutes9 = null;
		this.useTimes9 = null;
		this.afterFactUseDays9 = null;
		this.factUseMinutes9 = null;
		this.beforeFactUseMinutes9 = null;
		this.afterFactUseMinutes9 = null;
		this.factUseTimes9 = null;
		this.remainMinutes9 = null;
		this.factRemainMinutes9 = null;
		this.beforeRemainMinutes9 = null;
		this.beforeFactRemainMinutes9 = null;
		this.afterRemainDays9 = null;
		this.afterRemainMinutes9 = null;
		this.afterFactRemainDays9 = null;
		this.afterFactRemainMinutes9 = null;
		this.grantDays9 = null;
		this.notUseMinutes9 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays9 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes9 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays9 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes9 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays9 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes9 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays9 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays9 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays9 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes9 = useTimes.getUseTimes().v();
					this.beforeUseMinutes9 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes9 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes9 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays9 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes9 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays9 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes9 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays9 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes9 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays9 = useDays.getUseDays().v();
					this.beforeFactUseDays9 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays9 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes9 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes9 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes9 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes9 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays9 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes9 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr9 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays9 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain10(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays10 = null;
		this.useMinutes10 = null;
		this.beforeUseMinutes10 = null;
		this.afterUseMinutes10 = null;
		this.useTimes10 = null;
		this.afterFactUseDays10 = null;
		this.factUseMinutes10 = null;
		this.beforeFactUseMinutes10 = null;
		this.afterFactUseMinutes10 = null;
		this.factUseTimes10 = null;
		this.remainMinutes10 = null;
		this.factRemainMinutes10 = null;
		this.beforeRemainMinutes10 = null;
		this.beforeFactRemainMinutes10 = null;
		this.afterRemainDays10 = null;
		this.afterRemainMinutes10 = null;
		this.afterFactRemainDays10 = null;
		this.afterFactRemainMinutes10 = null;
		this.grantDays10 = null;
		this.notUseMinutes10 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays10 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes10 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays10 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes10 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays10 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes10 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays10 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays10 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays10 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes10 = useTimes.getUseTimes().v();
					this.beforeUseMinutes10 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes10 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes10 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays10 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes10 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays10 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes10 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays10 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes10 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays10 = useDays.getUseDays().v();
					this.beforeFactUseDays10 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays10 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes10 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes10 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes10 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes10 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays10 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes10 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr10 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays10 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain11(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays11 = null;
		this.useMinutes11 = null;
		this.beforeUseMinutes11 = null;
		this.afterUseMinutes11 = null;
		this.useTimes11 = null;
		this.afterFactUseDays11 = null;
		this.factUseMinutes11 = null;
		this.beforeFactUseMinutes11 = null;
		this.afterFactUseMinutes11 = null;
		this.factUseTimes11 = null;
		this.remainMinutes11 = null;
		this.factRemainMinutes11 = null;
		this.beforeRemainMinutes11 = null;
		this.beforeFactRemainMinutes11 = null;
		this.afterRemainDays11 = null;
		this.afterRemainMinutes11 = null;
		this.afterFactRemainDays11 = null;
		this.afterFactRemainMinutes11 = null;
		this.grantDays11 = null;
		this.notUseMinutes11 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays11 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes11 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays11 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes11 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays11 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes11 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays11 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays11 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays11 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes11 = useTimes.getUseTimes().v();
					this.beforeUseMinutes11 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes11 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes11 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays11 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes11 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays11 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes11 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays11 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes11 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays11 = useDays.getUseDays().v();
					this.beforeFactUseDays11 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays11 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes11 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes11 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes11 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes11 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays11 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes11 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr11 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays11 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain12(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays12 = null;
		this.useMinutes12 = null;
		this.beforeUseMinutes12 = null;
		this.afterUseMinutes12 = null;
		this.useTimes12 = null;
		this.afterFactUseDays12 = null;
		this.factUseMinutes12 = null;
		this.beforeFactUseMinutes12 = null;
		this.afterFactUseMinutes12 = null;
		this.factUseTimes12 = null;
		this.remainMinutes12 = null;
		this.factRemainMinutes12 = null;
		this.beforeRemainMinutes12 = null;
		this.beforeFactRemainMinutes12 = null;
		this.afterRemainDays12 = null;
		this.afterRemainMinutes12 = null;
		this.afterFactRemainDays12 = null;
		this.afterFactRemainMinutes12 = null;
		this.grantDays12 = null;
		this.notUseMinutes12 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays12 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes12 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays12 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes12 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays12 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes12 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays12 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays12 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays12 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes12 = useTimes.getUseTimes().v();
					this.beforeUseMinutes12 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes12 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes12 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays12 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes12 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays12 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalTime = afterRemainGrant.getTime();
				if (afterRemainGrant.getTime().isPresent()) {
					SpecialLeavaRemainTime time = optionalTime.get();
					this.afterFactRemainMinutes12 = time.v();
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays12 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes12 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays12 = useDays.getUseDays().v();
					this.beforeFactUseDays12 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays12 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes12 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes12 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes12 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes12 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays12 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes12 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr12 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays12 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain13(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays13 = null;
		this.useMinutes13 = null;
		this.beforeUseMinutes13 = null;
		this.afterUseMinutes13 = null;
		this.useTimes13 = null;
		this.afterFactUseDays13 = null;
		this.factUseMinutes13 = null;
		this.beforeFactUseMinutes13 = null;
		this.afterFactUseMinutes13 = null;
		this.factUseTimes13 = null;
		this.remainMinutes13 = null;
		this.factRemainMinutes13 = null;
		this.beforeRemainMinutes13 = null;
		this.beforeFactRemainMinutes13 = null;
		this.afterRemainDays13 = null;
		this.afterRemainMinutes13 = null;
		this.afterFactRemainDays13 = null;
		this.afterFactRemainMinutes13 = null;
		this.grantDays13 = null;
		this.notUseMinutes13 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays13 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes13 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays13 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes13 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays13 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes13 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays13 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays13 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays13 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes13 = useTimes.getUseTimes().v();
					this.beforeUseMinutes13 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes13 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes13 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays13 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes13 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays13 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes13 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays13 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes13 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays13 = useDays.getUseDays().v();
					this.beforeFactUseDays13 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays13 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes13 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes13 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes13 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes13 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays13 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes13 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr13 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays13 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain14(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays14 = null;
		this.useMinutes14 = null;
		this.beforeUseMinutes14 = null;
		this.afterUseMinutes14 = null;
		this.useTimes14 = null;
		this.afterFactUseDays14 = null;
		this.factUseMinutes14 = null;
		this.beforeFactUseMinutes14 = null;
		this.afterFactUseMinutes14 = null;
		this.factUseTimes14 = null;
		this.remainMinutes14 = null;
		this.factRemainMinutes14 = null;
		this.beforeRemainMinutes14 = null;
		this.beforeFactRemainMinutes14 = null;
		this.afterRemainDays14 = null;
		this.afterRemainMinutes14 = null;
		this.afterFactRemainDays14 = null;
		this.afterFactRemainMinutes14 = null;
		this.grantDays14 = null;
		this.notUseMinutes14 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays14 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes14 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays14 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes14 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays14 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes14 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays14 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays14 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays14 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes14 = useTimes.getUseTimes().v();
					this.beforeUseMinutes14 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes14 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes14 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays14 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes14 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays14 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes14 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays14 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes14 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays14 = useDays.getUseDays().v();
					this.beforeFactUseDays14 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays14 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes14 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes14 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes14 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes14 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays14 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes14 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr14 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays14 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain15(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays15 = null;
		this.useMinutes15 = null;
		this.beforeUseMinutes15 = null;
		this.afterUseMinutes15 = null;
		this.useTimes15 = null;
		this.afterFactUseDays15 = null;
		this.factUseMinutes15 = null;
		this.beforeFactUseMinutes15 = null;
		this.afterFactUseMinutes15 = null;
		this.factUseTimes15 = null;
		this.remainMinutes15 = null;
		this.factRemainMinutes15 = null;
		this.beforeRemainMinutes15 = null;
		this.beforeFactRemainMinutes15 = null;
		this.afterRemainDays15 = null;
		this.afterRemainMinutes15 = null;
		this.afterFactRemainDays15 = null;
		this.afterFactRemainMinutes15 = null;
		this.grantDays15 = null;
		this.notUseMinutes15 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays15 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes15 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays15 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes15 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays15 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes15 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays15 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays15 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays15 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes15 = useTimes.getUseTimes().v();
					this.beforeUseMinutes15 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes15 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes15 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays15 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes15 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays15 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes15 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays15 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes15 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays15 = useDays.getUseDays().v();
					this.beforeFactUseDays15 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays15 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes15 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes15 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes15 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes15 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays15 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes15 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr15 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays15 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain16(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays16 = null;
		this.useMinutes16 = null;
		this.beforeUseMinutes16 = null;
		this.afterUseMinutes16 = null;
		this.useTimes16 = null;
		this.afterFactUseDays16 = null;
		this.factUseMinutes16 = null;
		this.beforeFactUseMinutes16 = null;
		this.afterFactUseMinutes16 = null;
		this.factUseTimes16 = null;
		this.remainMinutes16 = null;
		this.factRemainMinutes16 = null;
		this.beforeRemainMinutes16 = null;
		this.beforeFactRemainMinutes16 = null;
		this.afterRemainDays16 = null;
		this.afterRemainMinutes16 = null;
		this.afterFactRemainDays16 = null;
		this.afterFactRemainMinutes16 = null;
		this.grantDays16 = null;
		this.notUseMinutes16 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays16 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes16 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays16 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes16 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays16 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes16 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays16 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays16 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays16 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes16 = useTimes.getUseTimes().v();
					this.beforeUseMinutes16 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes16 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes16 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays16 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes16 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays16 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes16 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays16 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes16 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays16 = useDays.getUseDays().v();
					this.beforeFactUseDays16 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays16 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes16 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes16 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes16 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes16 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays16 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes16 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr16 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays16 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain17(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays17 = null;
		this.useMinutes17 = null;
		this.beforeUseMinutes17 = null;
		this.afterUseMinutes17 = null;
		this.useTimes17 = null;
		this.afterFactUseDays17 = null;
		this.factUseMinutes17 = null;
		this.beforeFactUseMinutes17 = null;
		this.afterFactUseMinutes17 = null;
		this.factUseTimes17 = null;
		this.remainMinutes17 = null;
		this.factRemainMinutes17 = null;
		this.beforeRemainMinutes17 = null;
		this.beforeFactRemainMinutes17 = null;
		this.afterRemainDays17 = null;
		this.afterRemainMinutes17 = null;
		this.afterFactRemainDays17 = null;
		this.afterFactRemainMinutes17 = null;
		this.grantDays17 = null;
		this.notUseMinutes17 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays17 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes17 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays17 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes17 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays17 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes17 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays17 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays17 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays17 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes17 = useTimes.getUseTimes().v();
					this.beforeUseMinutes17 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes17 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes17 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays17 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes17 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays17 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes17 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays17 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes17 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays17 = useDays.getUseDays().v();
					this.beforeFactUseDays17 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays17 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes17 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes17 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes17 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes17 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays17 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes17 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr17 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays17 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain18(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays18 = null;
		this.useMinutes18 = null;
		this.beforeUseMinutes18 = null;
		this.afterUseMinutes18 = null;
		this.useTimes18 = null;
		this.afterFactUseDays18 = null;
		this.factUseMinutes18 = null;
		this.beforeFactUseMinutes18 = null;
		this.afterFactUseMinutes18 = null;
		this.factUseTimes18 = null;
		this.remainMinutes18 = null;
		this.factRemainMinutes18 = null;
		this.beforeRemainMinutes18 = null;
		this.beforeFactRemainMinutes18 = null;
		this.afterRemainDays18 = null;
		this.afterRemainMinutes18 = null;
		this.afterFactRemainDays18 = null;
		this.afterFactRemainMinutes18 = null;
		this.grantDays18 = null;
		this.notUseMinutes18 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays18 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes18 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays18 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes18 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays18 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes18 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays18 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays18 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays18 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes18 = useTimes.getUseTimes().v();
					this.beforeUseMinutes18 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes18 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes18 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays18 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes18 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays18 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes18 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays18 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes18 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays18 = useDays.getUseDays().v();
					this.beforeFactUseDays18 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays18 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes18 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes18 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes18 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes18 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays18 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes18 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr18 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays18 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain19(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();
		this.afterUseDays19 = null;
		this.useMinutes19 = null;
		this.beforeUseMinutes19 = null;
		this.afterUseMinutes19 = null;
		this.useTimes19 = null;
		this.afterFactUseDays19 = null;
		this.factUseMinutes19 = null;
		this.beforeFactUseMinutes19 = null;
		this.afterFactUseMinutes19 = null;
		this.factUseTimes19 = null;
		this.remainMinutes19 = null;
		this.factRemainMinutes19 = null;
		this.beforeRemainMinutes19 = null;
		this.beforeFactRemainMinutes19 = null;
		this.afterRemainDays19 = null;
		this.afterRemainMinutes19 = null;
		this.afterFactRemainDays19 = null;
		this.afterFactRemainMinutes19 = null;
		this.grantDays19 = null;
		this.notUseMinutes19 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays19 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes19 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays19 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes19 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays19 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes19 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays19 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays19 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays19 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes19 = useTimes.getUseTimes().v();
					this.beforeUseMinutes19 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes19 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes19 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays19 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes19 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays19 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes19 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays19 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes19 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays19 = useDays.getUseDays().v();
					this.beforeFactUseDays19 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays19 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes19 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes19 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes19 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes19 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays19 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes19 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr19 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays19 = leaveGrantDay.v().intValue();
		}
	}

	public void toEntityMonthSpeRemain20(SpecialHolidayRemainData domain) {

		/** 特別休暇月別残数データ．実特別休暇 **/
		val actualSpecial = domain.getActualSpecial();
		/** 特別休暇月別残数データ．特別休暇 **/
		val specialLeave = domain.getSpecialLeave();

		this.afterUseDays20 = null;
		this.useMinutes20 = null;
		this.beforeUseMinutes20 = null;
		this.afterUseMinutes20 = null;
		this.useTimes20 = null;
		this.afterFactUseDays20 = null;
		this.factUseMinutes20 = null;
		this.beforeFactUseMinutes20 = null;
		this.afterFactUseMinutes20 = null;
		this.factUseTimes20 = null;
		this.remainMinutes20 = null;
		this.factRemainMinutes20 = null;
		this.beforeRemainMinutes20 = null;
		this.beforeFactRemainMinutes20 = null;
		this.afterRemainDays20 = null;
		this.afterRemainMinutes20 = null;
		this.afterFactRemainDays20 = null;
		this.afterFactRemainMinutes20 = null;
		this.grantDays20 = null;
		this.notUseMinutes20 = null;
		if (specialLeave != null) {
			/** 特別休暇月別残数データ．特別休暇．使用数 **/
			val useNumber = specialLeave.getUseNumber();
			/** 特別休暇月別残数データ．特別休暇．残数.日数 */
			val remain = specialLeave.getRemain();
			/** 特別休暇月別残数データ．特別休暇．残数付与前 **/
			val beforeRemainGrant = specialLeave.getBeforeRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = specialLeave.getAfterRemainGrant();
			/** 特別休暇月別残数データ．特別休暇．未消化数 **/
			val unDegestionNumber = specialLeave.getUnDegestionNumber();

			if (beforeRemainGrant != null) {
				this.beforeRemainDays20 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeRemainMinutes20 = beforeRemainGrant.getTime().get().v();
				}

			}

			if (optionalAfterRemainGrant.isPresent()) {
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterRemainDays20 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterRemainMinutes20 = afterRemainGrant.getTime().get().v();
				}
			}

			if (unDegestionNumber != null) {
				this.notUseDays20 = unDegestionNumber.getDays().v();
				Optional<SpecialLeavaRemainTime> optionalRemainTime = unDegestionNumber.getTimes();
				if (optionalRemainTime.isPresent()) {
					SpecialLeavaRemainTime remainTime = optionalRemainTime.get();
					this.notUseMinutes20 = remainTime.v();

				}
			}

			if (useNumber != null) {
				val useDays = useNumber.getUseDays();
				val useTimes = useNumber.getUseTimes().orElse(null);

				if (useDays != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
					this.useDays20 = useDays.getUseDays() == null ? 0.0d : useDays.getUseDays().v().doubleValue();
					this.beforeUseDays20 = useDays.getBeforeUseGrantDays() == null ? 0.0d
							: useDays.getBeforeUseGrantDays().v().doubleValue();
					this.afterUseDays20 = useDays.getUseDays().v();
				}
				if (useTimes != null) {
					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前 */
					this.useMinutes20 = useTimes.getUseTimes().v();
					this.beforeUseMinutes20 = useTimes.getBeforeUseGrantTimes().v();
					val afterUseGrantTimes = useTimes.getAfterUseGrantTimes();
					if (afterUseGrantTimes.isPresent()) {
						this.afterUseMinutes20 = afterUseGrantTimes.get().v();
					}

					/** 特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
					this.useTimes20 = useTimes.getUseNumber().v();
				}

			}

			if (remain != null) {
				this.remainDays20 = remain.getDays().v();
				if (remain.getTime().isPresent()) {
					this.remainMinutes20 = remain.getTime().get().v();
				}

			}

		}

		if (actualSpecial != null) {
			val factUseDays = actualSpecial.getUseNumber();

			/** 特別休暇月別残数データ．実特別休暇．残数 **/
			val factRemain = actualSpecial.getRemain();
			/** 特別休暇月別残数データ．実特別休暇．残数付与前 **/
			val beforeRemainGrant = actualSpecial.getBeforRemainGrant();
			/** 特別休暇月別残数データ．実特別休暇．残数付与後 **/
			val optionalAfterRemainGrant = actualSpecial.getAfterRemainGrant();

			if (optionalAfterRemainGrant.isPresent()) {
				/** 特別休暇月別残数データ．実特別休暇．残数付与後. 日数 */
				SpecialLeaveRemain afterRemainGrant = optionalAfterRemainGrant.get();
				this.afterFactRemainDays20 = afterRemainGrant.getDays() == null ? null : afterRemainGrant.getDays().v();
				if (afterRemainGrant.getTime().isPresent()) {
					this.afterFactRemainMinutes20 = new Double(afterRemainGrant.getTime().get().v());
				}
			}
			if (beforeRemainGrant != null) {
				this.beforeFactRemainDays20 = beforeRemainGrant.getDays().v();
				if (beforeRemainGrant.getTime().isPresent()) {
					this.beforeFactRemainMinutes20 = beforeRemainGrant.getTime().get().v();
				}
			}

			if (factUseDays != null) {
				/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
				val useDays = factUseDays.getUseDays();

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
				val useTimes = factUseDays.getUseTimes().orElse(null);
				if (useDays != null) {

					/** 特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数 */
					this.factUseDays20 = useDays.getUseDays().v();
					this.beforeFactUseDays20 = useDays.getBeforeUseGrantDays().v();
					if (useDays.getAfterUseGrantDays().isPresent()) {
						this.afterFactUseDays20 = useDays.getAfterUseGrantDays().get().v();
					}
				}

				if (useTimes != null) {
					/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用時間 */
					this.factUseMinutes20 = useTimes.getUseTimes().v();
					this.beforeFactUseMinutes20 = useTimes.getBeforeUseGrantTimes().v();
					if (useTimes.getAfterUseGrantTimes().isPresent()) {
						this.afterFactUseMinutes20 = useTimes.getAfterUseGrantTimes().get().v();
					}
				}

				/** 特別休暇月別残数データ．実特別休暇．使用数．使用時間.使用回数 */
				this.factUseTimes20 = useTimes.getUseNumber() == null ? null : useTimes.getUseNumber().v();
			}

			if (factRemain != null) {
				this.factRemainDays20 = factRemain.getDays().v();

				if (factRemain.getTime().isPresent()) {
					this.factRemainMinutes20 = factRemain.getTime().get().v();
				}

			}
		}
		this.grantAtr20 = domain.isGrantAtr() == true ? 1 : 0;
		Optional<SpecialLeaveGrantUseDay> optionalLeaveGrantDay = domain.getGrantDays();
		if (optionalLeaveGrantDay.isPresent()) {
			SpecialLeaveGrantUseDay leaveGrantDay = optionalLeaveGrantDay.get();
			this.grantDays20 = leaveGrantDay.v().intValue();
		}
	}
	/** KRCDT_MON_CHILD_HD_REMAIN **/
	
	/* KRCDT_MON_ANNLEA_REMAIN - エンティティ：年休月別残数データ */
	/**
	 * ドメインに変換
	 * @return 年休月別残数データ
	 */
	public AnnLeaRemNumEachMonth toDomainAnnLeaRemNumEachMonth(){
		
		// 年休月別残数明細を分類する

		
		// 年休：使用時間
		UsedMinutes valUsedMinutesAfter = null;
		if (this.annleaUsedMinutesAfter != null){
			valUsedMinutesAfter = new UsedMinutes(this.annleaUsedMinutesAfter);
		}
		TimeAnnualLeaveUsedTime valUsedTime = null;
		if (this.annleaUsedTimes != null &&
			this.annleaUsedMinutes != null &&
			this.annleaUsedMinutesBefore != null){
			valUsedTime = TimeAnnualLeaveUsedTime.of(
					new UsedTimes(this.annleaUsedTimes),
					new UsedMinutes(this.annleaUsedMinutes),
					new UsedMinutes(this.annleaUsedMinutesBefore),
					Optional.ofNullable(valUsedMinutesAfter));
		}
		
		// 年休：残数付与後
		AnnualLeaveRemainingNumber valRemainAfter = null;
		if (this.annleaRemainingDaysAfter != null){
			RemainingMinutes valRemainMinutesAfter = null;
			if (this.annleaRemainingMinutesAfter != null){
				valRemainMinutesAfter = new RemainingMinutes(this.annleaRemainingMinutesAfter);
			}
			valRemainAfter = AnnualLeaveRemainingNumber.of(
					new AnnualLeaveRemainingDayNumber(this.annleaRemainingDaysAfter),
					Optional.ofNullable(valRemainMinutesAfter),
					new ArrayList<>());
		}
		
		// 年休
		AnnualLeaveUsedDayNumber valUsedDaysAfter = null;
		if (this.annleaUsedDaysAfter != null){
			valUsedDaysAfter = new AnnualLeaveUsedDayNumber(this.annleaUsedDaysAfter);
		}
		RemainingMinutes valRemainMinutes = null;
		if (this.annleaRemainingMinutes != null){
			valRemainMinutes = new RemainingMinutes(this.annleaRemainingMinutes);
		}
		RemainingMinutes valRemainMinutesBefore = null;
		if (this.annleaRemainingMinutesBefore != null){
			valRemainMinutesBefore = new RemainingMinutes(this.annleaRemainingMinutesBefore);
		}
		UndigestedTimeAnnualLeaveTime valUnusedMinutes = null;
		if (this.annleaUnusedMinutes != null){
			valUnusedMinutes = UndigestedTimeAnnualLeaveTime.of(new UsedMinutes(this.annleaUnusedMinutes));
		}
		AnnualLeave annualLeave = AnnualLeave.of(
				AnnualLeaveUsedNumber.of(
						AnnualLeaveUsedDays.of(
								new AnnualLeaveUsedDayNumber(this.annleaUsedDays),
								new AnnualLeaveUsedDayNumber(this.annleaUsedDaysBefore),
								Optional.ofNullable(valUsedDaysAfter)),
						Optional.ofNullable(valUsedTime)),
				AnnualLeaveRemainingNumber.of(
						new AnnualLeaveRemainingDayNumber(this.annleaRemainingDays),
						Optional.ofNullable(valRemainMinutes),
						new ArrayList<>()),
				AnnualLeaveRemainingNumber.of(
						new AnnualLeaveRemainingDayNumber(this.annleaRemainingDaysBefore),
						Optional.ofNullable(valRemainMinutesBefore),
						new ArrayList<>()),
				Optional.ofNullable(valRemainAfter),
				AnnualLeaveUndigestedNumber.of(
						UndigestedAnnualLeaveDays.of(new AnnualLeaveUsedDayNumber(this.annleaUnusedDays)),
						Optional.ofNullable(valUnusedMinutes)));

		// 実年休：使用時間
		UsedMinutes valFactUsedMinutesAfter = null;
		if (this.annleaFactUsedMinutesAfter != null){
			valFactUsedMinutesAfter = new UsedMinutes(this.annleaFactUsedMinutesAfter);
		}
		TimeAnnualLeaveUsedTime valFactUsedTime = null;
		if (this.annleaFactUsedTimes != null &&
			this.annleaFactUsedMinutes != null &&
			this.annleaFactUsedMinutesBefore != null){
			valFactUsedTime = TimeAnnualLeaveUsedTime.of(
					new UsedTimes(this.annleaFactUsedTimes),
					new UsedMinutes(this.annleaFactUsedMinutes),
					new UsedMinutes(this.annleaFactUsedMinutesBefore),
					Optional.ofNullable(valFactUsedMinutesAfter));
		}
		
		// 実年休：残数付与後
		AnnualLeaveRemainingNumber valFactRemainAfter = null;
		if (this.annleaFactRemainingDaysAfter != null){
			RemainingMinutes valFactRemainMinutesAfter = null;
			if (this.annleaFactRemainingMinutesAfter != null){
				valFactRemainMinutesAfter = new RemainingMinutes(this.annleaFactRemainingMinutesAfter);
			}
			valFactRemainAfter = AnnualLeaveRemainingNumber.of(
					new AnnualLeaveRemainingDayNumber(this.annleaFactRemainingDaysAfter),
					Optional.ofNullable(valFactRemainMinutesAfter),
					new ArrayList<>());
		}
		
		// 実年休
		AnnualLeaveUsedDayNumber valFactUsedDaysAfter = null;
		if (this.annleaFactUsedDaysAfter != null){
			valFactUsedDaysAfter = new AnnualLeaveUsedDayNumber(this.annleaFactUsedDaysAfter);
		}
		RemainingMinutes valFactRemainMinutes = null;
		if (this.annleaFactRemainingMinutes != null){
			valFactRemainMinutes = new RemainingMinutes(this.annleaFactRemainingMinutes);
		}
		RemainingMinutes valFactRemainMinutesBefore = null;
		if (this.annleaFactRemainingMinutesBefore != null){
			valFactRemainMinutesBefore = new RemainingMinutes(this.annleaFactRemainingMinutesBefore);
		}
		RealAnnualLeave realAnnualLeave = RealAnnualLeave.of(
				AnnualLeaveUsedNumber.of(
						AnnualLeaveUsedDays.of(
								new AnnualLeaveUsedDayNumber(this.annleaFactUsedDays),
								new AnnualLeaveUsedDayNumber(this.annleaFactUsedDaysBefore),
								Optional.ofNullable(valFactUsedDaysAfter)),
						Optional.ofNullable(valFactUsedTime)),
				AnnualLeaveRemainingNumber.of(
						new AnnualLeaveRemainingDayNumber(this.annleaFactRemainingDays),
						Optional.ofNullable(valFactRemainMinutes),
						new ArrayList<>()),
				AnnualLeaveRemainingNumber.of(
						new AnnualLeaveRemainingDayNumber(this.annleaFactRemainingDaysBefore),
						Optional.ofNullable(valFactRemainMinutesBefore),
						new ArrayList<>()),
				Optional.ofNullable(valFactRemainAfter));
		
		// 半日年休
		HalfDayAnnualLeave halfDayAnnualLeave = null;
		if (this.annleaHalfRemainingTimes != null &&
			this.annleaHalfRemainingTimesBefore != null &&
			this.annleaHalfUsedTimes != null &&
			this.annleaHalfUsedTimesBefore != null){
			RemainingTimes valHalfRemainTimesAfter = null;
			if (this.annleaHalfRemainingTimesAfter != null){
				valHalfRemainTimesAfter = new RemainingTimes(this.annleaHalfRemainingTimesAfter);
			}
			UsedTimes valHalfUsedTimesAfter = null;
			if (this.annleaHalfUsedTimesAfter != null){
				valHalfUsedTimesAfter = new UsedTimes(this.annleaHalfUsedTimesAfter);
			}
			halfDayAnnualLeave = HalfDayAnnualLeave.of(
					HalfDayAnnLeaRemainingNum.of(
							new RemainingTimes(this.annleaHalfRemainingTimes),
							new RemainingTimes(this.annleaHalfRemainingTimesBefore),
							Optional.ofNullable(valHalfRemainTimesAfter)),
					HalfDayAnnLeaUsedNum.of(
							new UsedTimes(this.annleaHalfUsedTimes),
							new UsedTimes(this.annleaHalfUsedTimesBefore),
							Optional.ofNullable(valHalfUsedTimesAfter)));
		}
		
		// 実半日年休
		HalfDayAnnualLeave realHalfDayAnnualLeave = null;
		if (this.annleaFactHalfRemainingTimes != null &&
			this.annleaFactHalfRemainingTimesBefore != null &&
			this.annleaFactHalfUsedTimes != null &&
			this.annleaFactHalfUsedTimesBefore != null){
			RemainingTimes valFactHalfRemainTimesAfter = null;
			if (this.annleaFactHalfRemainingTimesAfter != null){
				valFactHalfRemainTimesAfter = new RemainingTimes(this.annleaFactHalfRemainingTimesAfter);
			}
			UsedTimes valFactHalfUsedTimesAfter = null;
			if (this.annleaFactHalfUsedTimesAfter != null){
				valFactHalfUsedTimesAfter = new UsedTimes(this.annleaFactHalfUsedTimesAfter);
			}
			realHalfDayAnnualLeave = HalfDayAnnualLeave.of(
					HalfDayAnnLeaRemainingNum.of(
							new RemainingTimes(this.annleaFactHalfRemainingTimes),
							new RemainingTimes(this.annleaFactHalfRemainingTimesBefore),
							Optional.ofNullable(valFactHalfRemainTimesAfter)),
					HalfDayAnnLeaUsedNum.of(
							new UsedTimes(this.annleaFactHalfUsedTimes),
							new UsedTimes(this.annleaFactHalfUsedTimesBefore),
							Optional.ofNullable(valFactHalfUsedTimesAfter)));
		}
		
		// 年休付与情報
		AnnualLeaveGrant annualLeaveGrant = null;
		if (this.annleaGrantDays != null &&
			this.annleaGrantLaborDays != null &&
			this.annleaGrantPredeterminedDays != null &&
			this.annleaGrantDeductionDays != null &&
			this.annleaDeductionDaysBefore != null &&
			this.annleaDeductionDaysAfter != null &&
			this.annleaAttendanceRate != null){
			annualLeaveGrant = AnnualLeaveGrant.of(
					new AnnualLeaveGrantDayNumber(this.annleaGrantDays),
					new YearlyDays((double)this.annleaGrantLaborDays),
					new YearlyDays((double)this.annleaGrantPredeterminedDays),
					new YearlyDays((double)this.annleaGrantDeductionDays),
					new MonthlyDays((double)this.annleaDeductionDaysBefore),
					new MonthlyDays((double)this.annleaDeductionDaysAfter),
					new AttendanceRate(this.annleaAttendanceRate));
		}
		
		// 上限残時間
		AnnualLeaveMaxRemainingTime maxRemainingTime = null;
		if (this.annleaTimeRemainingMinutes != null &&
			this.annleaTimeRemainingMinutesBefore != null){
			RemainingMinutes valTimeRemainMinutesAfter = null;
			if (this.annleaTimeRemainingMinutesAfter != null){
				valTimeRemainMinutesAfter = new RemainingMinutes(this.annleaTimeRemainingMinutesAfter);
			}
			maxRemainingTime = AnnualLeaveMaxRemainingTime.of(
					new RemainingMinutes(this.annleaTimeRemainingMinutes),
					new RemainingMinutes(this.annleaTimeRemainingMinutesBefore),
					Optional.ofNullable(valTimeRemainMinutesAfter));
		}
		
		// 実上限残時間
		AnnualLeaveMaxRemainingTime realMaxRemainingTime = null;
		if (this.annleaFactTimeRemainingMinutes != null &&
			this.annleaFactTimeRemainingMinutesBefore != null){
			RemainingMinutes valFactTimeRemainMinutesAfter = null;
			if (this.annleaFactTimeRemainingMinutesAfter != null){
				valFactTimeRemainMinutesAfter = new RemainingMinutes(this.annleaFactTimeRemainingMinutesAfter);
			}
			realMaxRemainingTime = AnnualLeaveMaxRemainingTime.of(
					new RemainingMinutes(this.annleaFactTimeRemainingMinutes),
					new RemainingMinutes(this.annleaFactTimeRemainingMinutesBefore),
					Optional.ofNullable(valFactTimeRemainMinutesAfter));
		}
		
		return AnnLeaRemNumEachMonth.of(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonRemainPk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), (this.krcdtMonRemainPk.getIsLastDay() != 0)),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				annualLeave,
				realAnnualLeave,
				Optional.ofNullable(halfDayAnnualLeave),
				Optional.ofNullable(realHalfDayAnnualLeave),
				Optional.ofNullable(annualLeaveGrant),
				Optional.ofNullable(maxRemainingTime),
				Optional.ofNullable(realMaxRemainingTime),
				AnnualLeaveAttdRateDays.of(
						new MonthlyDays((double)this.annleaLaborDays),
						new MonthlyDays((double)this.annleaPredeterminedDays),
						new MonthlyDays((double)this.annleaDeductionDays)),
				(this.annleaGrantAtr != 0));
	}
	
	
	/**
	 * KRCDT_MON_RSVLEA_REMAIN
	 * ドメインに変換
	 * @return 年休月別残数データ
	 */
	public RsvLeaRemNumEachMonth toDomainRsvLeaRemNumEachMonth(){
		
		// 積立年休月別残数明細を分類する
		List<ReserveLeaveRemainingDetail> normalDetail = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> normalDetailBefore = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> normalDetailAfter = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetail = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetailBefore = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetailAfter = new ArrayList<>();
		
		// 積立年休：残数付与後
		ReserveLeaveRemainingNumber valRemainAfter = null;
		if (this.rsvleaRemainingDaysAfter != null){
			valRemainAfter = ReserveLeaveRemainingNumber.of(
					new ReserveLeaveRemainingDayNumber(this.rsvleaRemainingDaysAfter),
					normalDetailAfter);
		}
		
		// 積立年休
		ReserveLeaveUsedDayNumber valUsedDaysAfter = null;
		if (this.rsvleaUsedDaysAfter != null){
			valUsedDaysAfter = new ReserveLeaveUsedDayNumber(this.rsvleaUsedDaysAfter);
		}
		ReserveLeave reserveLeave = ReserveLeave.of(
				ReserveLeaveUsedNumber.of(
						new ReserveLeaveUsedDayNumber(this.rsvleaUsedDays),
						new ReserveLeaveUsedDayNumber(this.rsvleaUsedDaysBefore),
						Optional.ofNullable(valUsedDaysAfter)),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.rsvleaRemainingDays),
						normalDetail),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.rsvleaRemainingDaysBefore),
						normalDetailBefore),
				Optional.ofNullable(valRemainAfter),
				ReserveLeaveUndigestedNumber.of(
						new ReserveLeaveRemainingDayNumber(this.rsvleaNotUsedDays)));

		// 実積立年休：残数付与後
		ReserveLeaveRemainingNumber valFactRemainAfter = null;
		if (this.rsvleaFactRemainingDaysAfter != null){
			valFactRemainAfter = ReserveLeaveRemainingNumber.of(
					new ReserveLeaveRemainingDayNumber(this.rsvleaFactRemainingDaysAfter),
					realDetailAfter);
		}
		
		// 実積立年休
		ReserveLeaveUsedDayNumber valFactUsedDaysAfter = null;
		if (this.rsvleaFactUsedDaysAfter != null){
			valFactUsedDaysAfter = new ReserveLeaveUsedDayNumber(this.rsvleaFactUsedDaysAfter);
		}
		RealReserveLeave realReserveLeave = RealReserveLeave.of(
				ReserveLeaveUsedNumber.of(
						new ReserveLeaveUsedDayNumber(this.rsvleaFactUsedDays),
						new ReserveLeaveUsedDayNumber(this.rsvleaFactUsedDaysBefore),
						Optional.ofNullable(valFactUsedDaysAfter)),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.rsvleaFactRemainingDays),
						realDetail),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.rsvleaFactRemainingDaysBefore),
						realDetailBefore),
				Optional.ofNullable(valFactRemainAfter));
		
		// 積立年休付与情報
		ReserveLeaveGrant reserveLeaveGrant = null;
		if (this.rsvleaGrantDays != null){
			reserveLeaveGrant = ReserveLeaveGrant.of(
					new ReserveLeaveGrantDayNumber(this.rsvleaGrantDays));
		}
		
		return RsvLeaRemNumEachMonth.of(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonRemainPk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), (this.krcdtMonRemainPk.getIsLastDay() != 0)),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				reserveLeave,
				realReserveLeave,
				Optional.ofNullable(reserveLeaveGrant),
				(this.rsvleaGrantAtr != 0));
	}
	
	
	public SpecialHolidayRemainDataMerge toDomainSpecialHolidayRemainData() {
		SpecialHolidayRemainDataMerge merge = new SpecialHolidayRemainDataMerge();
		merge.setSpecialHolidayRemainData1	(this.toDomainSpecialHolidayRemainData1	());
		merge.setSpecialHolidayRemainData2	(this.toDomainSpecialHolidayRemainData2	());
		merge.setSpecialHolidayRemainData3	(this.toDomainSpecialHolidayRemainData3	());
		merge.setSpecialHolidayRemainData4	(this.toDomainSpecialHolidayRemainData4	());
		merge.setSpecialHolidayRemainData5	(this.toDomainSpecialHolidayRemainData5	());
		merge.setSpecialHolidayRemainData6	(this.toDomainSpecialHolidayRemainData6	());
		merge.setSpecialHolidayRemainData7	(this.toDomainSpecialHolidayRemainData7	());
		merge.setSpecialHolidayRemainData8	(this.toDomainSpecialHolidayRemainData8	());
		merge.setSpecialHolidayRemainData9	(this.toDomainSpecialHolidayRemainData9	());
		merge.setSpecialHolidayRemainData10	(this.toDomainSpecialHolidayRemainData10());
		merge.setSpecialHolidayRemainData11	(this.toDomainSpecialHolidayRemainData11());
		merge.setSpecialHolidayRemainData12	(this.toDomainSpecialHolidayRemainData12());
		merge.setSpecialHolidayRemainData13	(this.toDomainSpecialHolidayRemainData13());
		merge.setSpecialHolidayRemainData14	(this.toDomainSpecialHolidayRemainData14());
		merge.setSpecialHolidayRemainData15	(this.toDomainSpecialHolidayRemainData15());
		merge.setSpecialHolidayRemainData16	(this.toDomainSpecialHolidayRemainData16());
		merge.setSpecialHolidayRemainData17	(this.toDomainSpecialHolidayRemainData17());
		merge.setSpecialHolidayRemainData18	(this.toDomainSpecialHolidayRemainData18());
		merge.setSpecialHolidayRemainData19	(this.toDomainSpecialHolidayRemainData19());
		merge.setSpecialHolidayRemainData20	(this.toDomainSpecialHolidayRemainData20());

		return merge;
	}
	
	/**
	 * KRCDT_MON_SP_REMAIN
	 * 特別休暇月別残数データ
	 *  
	 */
	public SpecialHolidayRemainData toDomainSpecialHolidayRemainData1(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays1));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes1)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays1));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes1)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays1));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays1));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays1)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes1));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes1));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes1));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays1));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes1 == null ? null : this.afterFactRemainMinutes1.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays1));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes1)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays1));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes1)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays1));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays1));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays1)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes1));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes1));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes1));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays1));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes1)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays1));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes1 == null ? null : this.afterRemainMinutes1.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays1 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays1));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				1,
				actualSpecial,
				specialLeave,
				(this.grantAtr1 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	public SpecialHolidayRemainData toDomainSpecialHolidayRemainData2(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays2));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes2)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays2));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes2)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays2));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays2));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays2)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes2));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes2));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes2));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays2));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes2 == null ? null : this.afterFactRemainMinutes2.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays2));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes2)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays2));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes2)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays2));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays2));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays2)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes2));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes2));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes2));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays2));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes2)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays2));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes2 == null ? null : this.afterRemainMinutes2.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays2 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays2));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				2,
				actualSpecial,
				specialLeave,
				(this.grantAtr2 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData3(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays3));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes3)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays3));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes3)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays3));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays3));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays3)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes3));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes3));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes3));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays3));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes3 == null ? null : this.afterFactRemainMinutes3.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays3));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes3)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays3));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes3)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays3));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays3));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays3)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes3));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes3));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes3));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays3));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes3)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays3));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes3 == null ? null : this.afterRemainMinutes3.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays3 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays3));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				3,
				actualSpecial,
				specialLeave,
				(this.grantAtr3 !=  0),
				Optional.ofNullable(grantDays));
	}
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData4(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays4));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes4)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays4));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes4)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays4));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays4));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays4)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes4));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes4));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes4));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays4));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes4 == null ? null : this.afterFactRemainMinutes4.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays4));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes4)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays4));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes4)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays4));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays4));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays4)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes4));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes4));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes4));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays4));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes4)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays4));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes4 == null ? null : this.afterRemainMinutes4.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays4 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays4));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				4,
				actualSpecial,
				specialLeave,
				(this.grantAtr4 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData5(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays5));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes5)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays5));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes5)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays5));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays5));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays5)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes5));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes5));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes5));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays5));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes5 == null ? null : this.afterFactRemainMinutes5.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays5));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes5)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays5));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes5)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays5));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays5));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays5)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes5));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes5));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes5));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays5));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes5)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays5));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes5 == null ? null : this.afterRemainMinutes5.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays5 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays5));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				5,
				actualSpecial,
				specialLeave,
				(this.grantAtr5 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData6(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays6));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes6)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays6));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes6)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays6));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays6));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays6)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes6));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes6));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes6));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays6));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes6 == null ? null : this.afterFactRemainMinutes6.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays6));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes6)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays6));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes6)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays6));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays6));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays6)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes6));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes6));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes6));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays6));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes6)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays6));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes6 == null ? null : this.afterRemainMinutes6.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays6 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays6));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				6,
				actualSpecial,
				specialLeave,
				(this.grantAtr6 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData7(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays7));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes7)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays7));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes7)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays7));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays7));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays7)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes7));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes7));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes7));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays7));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes7 == null ? null : this.afterFactRemainMinutes7.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays7));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes7)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays7));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes7)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays7));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays7));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays7)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes7));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes7));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes7));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays7));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes7)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays7));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes7 == null ? null : this.afterRemainMinutes7.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays7 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays7));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				7,
				actualSpecial,
				specialLeave,
				(this.grantAtr7 !=  0),
				Optional.ofNullable(grantDays));
	}
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData8(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays8));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes8)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays8));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes8)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays8));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays8));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays8)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes8));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes8));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes8));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays8));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes8 == null ? null : this.afterFactRemainMinutes8.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays8));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes8)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays8));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes8)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays8));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays8));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays8)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes8));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes8));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes8));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays8));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes8)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays8));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes8 == null ? null : this.afterRemainMinutes8.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays8 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays8));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				8,
				actualSpecial,
				specialLeave,
				(this.grantAtr8 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData9(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays9));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes9)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays9));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes9)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays9));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays9));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays9)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes9));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes9));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes9));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays9));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes9 == null ? null : this.afterFactRemainMinutes9.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays9));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes9)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays9));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes9)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays9));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays9));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays9)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes9));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes9));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes9));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays9));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes9)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays9));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes9 == null ? null : this.afterRemainMinutes9.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays9 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays9));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				9,
				actualSpecial,
				specialLeave,
				(this.grantAtr9 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData10(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays10));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes10)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays10));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes10)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays10));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays10));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays10)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes10));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes10));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes10));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays10));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes10 == null ? null : this.afterFactRemainMinutes10.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays10));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes10)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays10));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes10)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays10));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays10));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays10)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes10));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes10));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes10));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays10));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes10)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays10));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes10 == null ? null : this.afterRemainMinutes10.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays10 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays10));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				10,
				actualSpecial,
				specialLeave,
				(this.grantAtr10 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData11(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays11));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes11)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays11));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes11)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays11));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays11));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays11)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes11));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes11));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes11));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays11));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes11 == null ? null : this.afterFactRemainMinutes11.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays11));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes11)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays11));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes11)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays11));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays11));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays11)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes11));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes11));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes11));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays11));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes11)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays11));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes11 == null ? null : this.afterRemainMinutes11.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays11 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays11));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				11,
				actualSpecial,
				specialLeave,
				(this.grantAtr11 !=  0),
				Optional.ofNullable(grantDays));
	}
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData12(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays12));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes12)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays12));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes12)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays12));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays12));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays12)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes12));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes12));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes12));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays12));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes12 == null ? null : this.afterFactRemainMinutes12.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays12));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes12)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays12));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes12)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays12));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays12));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays12)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes12));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes12));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes12));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays12));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes12)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays12));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes12 == null ? null : this.afterRemainMinutes12.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays12 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays12));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				12,
				actualSpecial,
				specialLeave,
				(this.grantAtr12 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData13(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays13));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes13)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays13));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes13)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays13));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays13));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays13)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes13));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes13));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes13));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays13));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes13 == null ? null : this.afterFactRemainMinutes13.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays13));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes13)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays13));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes13)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays13));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays13));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays13)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes13));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes13));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes13));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays13));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes13)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays13));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes13 == null ? null : this.afterRemainMinutes13.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays13 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays13));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				13,
				actualSpecial,
				specialLeave,
				(this.grantAtr13 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData14(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays14));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes14)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays14));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes14)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays14));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays14));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays14)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes14));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes14));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes14));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays14));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes14 == null ? null : this.afterFactRemainMinutes14.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays14));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes14)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays14));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes14)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays14));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays14));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays14)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes14));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes14));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes14));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays14));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes14)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays14));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes14 == null ? null : this.afterRemainMinutes14.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays14 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays14));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				14,
				actualSpecial,
				specialLeave,
				(this.grantAtr14 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData15(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays15));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes15)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays15));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes15)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays15));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays15));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays15)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes15));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes15));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes15));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays15));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes15 == null ? null : this.afterFactRemainMinutes15.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays15));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes15)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays15));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes15)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays15));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays15));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays15)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes15));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes15));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes15));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays15));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes15)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays15));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes15 == null ? null : this.afterRemainMinutes15.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays15 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays15));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				15,
				actualSpecial,
				specialLeave,
				(this.grantAtr15 !=  0),
				Optional.ofNullable(grantDays));
	}
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData16(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays16));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes16)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays16));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes16)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays16));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays16));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays16)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes16));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes16));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes16));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays16));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes16 == null ? null : this.afterFactRemainMinutes16.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays16));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes16)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays16));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes16)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays16));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays16));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays16)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes16));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes16));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes16));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays16));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes16)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays16));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes16 == null ? null : this.afterRemainMinutes16.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays16 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays16));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				16,
				actualSpecial,
				specialLeave,
				(this.grantAtr16 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData17(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays17));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes17)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays17));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes17)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays17));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays17));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays17)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes17));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes17));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes17));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays17));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes17 == null ? null : this.afterFactRemainMinutes17.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays17));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes17)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays17));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes17)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays17));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays17));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays17)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes17));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes17));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes17));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays17));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes17)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays17));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes17 == null ? null : this.afterRemainMinutes17.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays17 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays17));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				17,
				actualSpecial,
				specialLeave,
				(this.grantAtr17 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	public SpecialHolidayRemainData toDomainSpecialHolidayRemainData18(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays18));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes18)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays18));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes18)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays18));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays18));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays18)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes18));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes18));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes18));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays18));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes18 == null ? null : this.afterFactRemainMinutes18.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays18));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes18)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays18));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes18)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays18));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays18));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays18)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes18));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes18));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes18));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays18));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes18)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays18));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes18 == null ? null : this.afterRemainMinutes18.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays18 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays18));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				18,
				actualSpecial,
				specialLeave,
				(this.grantAtr18 !=  0),
				Optional.ofNullable(grantDays));
	}
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData19(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays19));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes19)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays19));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes19)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays19));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays19));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays19)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes19));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes19));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes19));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays19));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes19 == null ? null : this.afterFactRemainMinutes19.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays19));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes19)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays19));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes19)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays19));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays19));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays19)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes19));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes19));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes19));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays19));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes19)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays19));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes19 == null ? null : this.afterRemainMinutes19.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays19 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays19));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				19,
				actualSpecial,
				specialLeave,
				(this.grantAtr19 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
		public SpecialHolidayRemainData toDomainSpecialHolidayRemainData20(){
		
		/** 特別休暇月別残数データ．実特別休暇．残数 **/
		SpecialLeaveRemain factRemain = new SpecialLeaveRemain();
		factRemain.setDays(new SpecialLeaveRemainDay(this.factRemainDays20));
		factRemain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.factRemainMinutes20)));
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与前 **/
		SpecialLeaveRemain factBeforeRemainGrant = new SpecialLeaveRemain();
		factBeforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeFactRemainDays20));
		factBeforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeFactRemainMinutes20)));
		

		 //  使用日数 
		SpecialLeaveUseDays factUseDays = new SpecialLeaveUseDays();
		factUseDays.setUseDays(new SpecialLeaveRemainDay(this.factUseDays20));
		factUseDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeFactUseDays20));
		factUseDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterFactUseDays20)));
		// 使用時間 
		SpecialLeaveUseTimes factUseTimes = new SpecialLeaveUseTimes();
		factUseTimes.setUseNumber(new UseNumber(this.factUseTimes20));
		factUseTimes.setUseTimes(new SpecialLeavaRemainTime(this.factUseMinutes20));
		factUseTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeFactUseMinutes20));
		
		/**	特別休暇月別残数データ．実特別休暇．使用数 **/
		SpecialLeaveUseNumber factUseNumber = new SpecialLeaveUseNumber(factUseDays,factUseTimes );
		
		/** 特別休暇月別残数データ．実特別休暇.残数付与後  **/
		SpecialLeaveRemain factAfterRemainGrant = new SpecialLeaveRemain();
		factAfterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterFactRemainDays20));
		factAfterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterFactRemainMinutes20 == null ? null : this.afterFactRemainMinutes20.intValue())));
		
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(factRemain, factBeforeRemainGrant, factUseNumber, Optional.ofNullable(factAfterRemainGrant));
		

		/** 特別休暇月別残数データ．特別休暇．残数 **/
		SpecialLeaveRemain remain = new SpecialLeaveRemain();
		remain.setDays(new SpecialLeaveRemainDay(this.remainDays20));
		remain.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.remainMinutes20)));
		
		/** 特別休暇月別残数データ．特別休暇.残数付与前 **/
		SpecialLeaveRemain beforeRemainGrant = new SpecialLeaveRemain();
		beforeRemainGrant.setDays(new SpecialLeaveRemainDay(this.beforeRemainDays20));
		beforeRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.beforeRemainMinutes20)));
		
		/**	特別休暇月別残数データ.特別休暇．使用数 **/
		 //  使用日数 
		SpecialLeaveUseDays useDays = new SpecialLeaveUseDays();
		useDays.setUseDays(new SpecialLeaveRemainDay(this.useDays20));
		useDays.setBeforeUseGrantDays(new SpecialLeaveRemainDay(this.beforeUseDays20));
		useDays.setAfterUseGrantDays(Optional.ofNullable(new SpecialLeaveRemainDay(this.afterUseDays20)));
		// 使用時間 
		SpecialLeaveUseTimes useTimes = new SpecialLeaveUseTimes();
		useTimes.setUseNumber(new UseNumber(this.useTimes20));
		useTimes.setUseTimes(new SpecialLeavaRemainTime(this.useMinutes20));
		useTimes.setBeforeUseGrantTimes(new SpecialLeavaRemainTime(this.beforeUseMinutes20));
		 //  使用日数 
		SpecialLeaveUseNumber useNumber =  new SpecialLeaveUseNumber(factUseDays, factUseTimes);
		
		/** 特別休暇月別残数データ.特別休暇.未消化数  **/
		SpecialLeaveUnDigestion unDegestionNumber = new SpecialLeaveUnDigestion();
		unDegestionNumber.setDays(new  SpecialLeaveRemainDay(this.notUseDays20));
		unDegestionNumber.setTimes(Optional.ofNullable(new SpecialLeavaRemainTime(this.notUseMinutes20)));
		
		/** 特別休暇月別残数データ．特別休暇 .残数付与後  **/
		SpecialLeaveRemain afterRemainGrant = new SpecialLeaveRemain();
		afterRemainGrant.setDays(new SpecialLeaveRemainDay(this.afterRemainDays20));
		afterRemainGrant.setTime(Optional.ofNullable(new SpecialLeavaRemainTime(this.afterRemainMinutes20 == null ? null : this.afterRemainMinutes20.intValue())));
		
		/** 特別休暇月別残数データ.特別休暇.残数付与後  **/
		/** 特別休暇月別残数データ．特別休暇  **/
		SpecialLeave specialLeave = new SpecialLeave(remain, beforeRemainGrant, useNumber, unDegestionNumber, Optional.ofNullable(afterRemainGrant));
		
		
		//特別休暇付与情報: 付与日数
		SpecialLeaveGrantUseDay grantDays = null;
		if(this.grantDays20 != null) {
			grantDays = new SpecialLeaveGrantUseDay(new Double(this.grantDays20));
		}
		
		return new SpecialHolidayRemainData(
				this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()),
				this.krcdtMonRemainPk.getClosureId(),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				new ClosureDate(this.krcdtMonRemainPk.getClosureDay(), this.krcdtMonRemainPk.getIsLastDay() == 1),
				20,
				actualSpecial,
				specialLeave,
				(this.grantAtr20 !=  0),
				Optional.ofNullable(grantDays));
	}
	
	
	
	/**
	 * KRCDT_MON_DAYOFF_REMAIN
	 * 代休月別残数データ
	 * @return MonthlyDayoffRemainData
	 */
	
	public MonthlyDayoffRemainData toDomainMonthlyDayoffRemainData() {
		DayOffDayAndTimes occurrenceDayTimes = new DayOffDayAndTimes();
		occurrenceDayTimes.setDay(new RemainDataDaysMonth(new Double(this.dayOffOccurredDays)));
		occurrenceDayTimes.setTime(Optional.ofNullable(this.dayOffOccurredTimes == null ? null : new RemainDataTimesMonth(this.dayOffOccurredTimes)));
		
		DayOffDayAndTimes  useDayTimes = new DayOffDayAndTimes();
		useDayTimes.setDay(new RemainDataDaysMonth(new Double(this.dayOffUsedDays)));
		useDayTimes.setTime(Optional.ofNullable(this.dayOffUsedMinutes == null ? null : new RemainDataTimesMonth(this.dayOffUsedMinutes)));
		
		
		DayOffRemainDayAndTimes remainingDayTimes = new DayOffRemainDayAndTimes();
		remainingDayTimes.setDays(new AttendanceDaysMonthToTal(this.dayOffRemainingDays));
		remainingDayTimes.setTimes(Optional.ofNullable(this.dayOffRemainingMinutes == null ? null : new RemainingMinutes(this.dayOffRemainingMinutes)));
		
		DayOffRemainDayAndTimes carryForWardDayTimes = new DayOffRemainDayAndTimes();
		carryForWardDayTimes.setDays(new AttendanceDaysMonthToTal(this.dayOffCarryforwardDays));
		carryForWardDayTimes.setTimes(Optional.ofNullable(this.dayOffCarryforwardMinutes == null ? null : new RemainingMinutes(this.dayOffCarryforwardMinutes)));
		
		DayOffDayAndTimes  unUsedDayTimes = new DayOffDayAndTimes();
		unUsedDayTimes.setDay(new RemainDataDaysMonth(new Double(this.dayOffUnUsedDays)));
		unUsedDayTimes.setTime(Optional.ofNullable(this.dayOffUnUsedTimes == null ? null : new RemainDataTimesMonth(this.dayOffUnUsedTimes)));
		
		return new MonthlyDayoffRemainData(this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()), this.krcdtMonRemainPk.getClosureId(),
				this.krcdtMonRemainPk.getClosureDay(), (this.krcdtMonRemainPk.getIsLastDay() != 0),
				this.startDate, this.endDate,
				occurrenceDayTimes,useDayTimes, remainingDayTimes, carryForWardDayTimes, unUsedDayTimes);
	}
	
	/**
	 * KRCDT_MON_SUBOFHD_REMAIN
	 * 振休使用日数合計
	 * @return AbsenceLeaveRemainData
	 */
	
	public AbsenceLeaveRemainData toDomainAbsenceLeaveRemainData() {
		RemainDataDaysMonth occurredDay = new RemainDataDaysMonth(this.subofHdOccurredDays);
		RemainDataDaysMonth usedDays = new RemainDataDaysMonth(this.subofHdUsedDays);
		AttendanceDaysMonthToTal remainingDays = new AttendanceDaysMonthToTal(this.subofHdRemainingDays);
		AttendanceDaysMonthToTal carryforwardDays = new AttendanceDaysMonthToTal(this.subofHdCarryForWardDays);
		RemainDataDaysMonth unUsedDays = new RemainDataDaysMonth(this.subofHdUnUsedDays);
		return new AbsenceLeaveRemainData(this.krcdtMonRemainPk.getEmployeeId(),
				new YearMonth(this.krcdtMonRemainPk.getYearMonth()), this.krcdtMonRemainPk.getClosureId(),
				this.krcdtMonRemainPk.getClosureDay(), (this.krcdtMonRemainPk.getIsLastDay() != 0),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				this.startDate, this.endDate,
				occurredDay, usedDays, remainingDays, carryforwardDays, unUsedDays);
	}
	/**
	 * KRCDT_MON_CHILD_HD_REMAIN
	 */
	public void  toDomainMonthChildHolidayRemain() {
		//TODO - code have not domain , entity
	}
	
	/**
	 * KRCDT_MON_CARE_HD_REMAIN
	 */
	public void  toDomainMonthCareHolidayRemain() {
		//TODO - code have not domain , entity
	}
	
}
