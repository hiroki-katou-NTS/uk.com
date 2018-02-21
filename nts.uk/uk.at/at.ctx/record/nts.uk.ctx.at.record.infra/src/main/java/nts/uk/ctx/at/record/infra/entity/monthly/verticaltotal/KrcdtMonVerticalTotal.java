package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
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
	
	/** 所定日数付与前 */
	@Column(name = "PREDET_DAYS_BFR_GRNT")
	public double predetermineDaysBeforeGrant;
	
	/** 所定日数付与後 */
	@Column(name = "PREDET_DAYS_AFT_GRNT")
	public double predetermineDaysAfterGrant;
	
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
		@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
}
