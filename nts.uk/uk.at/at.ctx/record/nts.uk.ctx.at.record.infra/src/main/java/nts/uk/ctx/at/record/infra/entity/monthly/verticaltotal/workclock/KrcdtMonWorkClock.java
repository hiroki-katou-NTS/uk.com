package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workclock;

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
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.EndClockOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.WorkClockOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonClock;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonClockOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonDivergenceOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の勤務時刻
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_WORK_CLOCK")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonWorkClock extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 終業回数 */
	@Column(name = "ENDWORK_TIMES")
	public int endWorkTimes;
	
	/** 終業合計時刻 */
	@Column(name = "ENDWORK_TOTAL_CLOCK")
	public int endWorkTotalClock;

	/** 終業平均時刻 */
	@Column(name = "ENDWORK_AVE_CLOCK")
	public int endWorkAverageClock;
	
	/** ログオン合計日数 */
	@Column(name = "LOGON_TOTAL_DAYS")
	public double logonTotalDays;
	
	/** ログオン合計時刻 */
	@Column(name = "LOGON_TOTAL_CLOCK")
	public int logonTotalClock;

	/** ログオン平均時刻 */
	@Column(name = "LOGON_AVE_CLOCK")
	public int logonAverageClock;
	
	/** ログオフ合計日数 */
	@Column(name = "LOGOFF_TOTAL_DAYS")
	public double logoffTotalDays;
	
	/** ログオフ合計時刻 */
	@Column(name = "LOGOFF_TOTAL_CLOCK")
	public int logoffTotalClock;

	/** ログオフ平均時刻 */
	@Column(name = "LOGOFF_AVE_CLOCK")
	public int logoffAverageClock;
	
	/** ログオン乖離日数 */
	@Column(name = "LOGON_DIV_DAYS")
	public double logonDivDays;
	
	/** ログオン乖離合計時間 */
	@Column(name = "LOGON_DIV_TOTAL_TIME")
	public int logonDivTotalTime;

	/** ログオン乖離平均時間 */
	@Column(name = "LOGON_DIV_AVE_TIME")
	public int logonDivAverageTime;
	
	/** ログオフ乖離日数 */
	@Column(name = "LOGOFF_DIV_DAYS")
	public double logoffDivDays;
	
	/** ログオフ乖離合計時間 */
	@Column(name = "LOGOFF_DIV_TOTAL_TIME")
	public int logoffDivTotalTime;

	/** ログオフ乖離平均時間 */
	@Column(name = "LOGOFF_DIV_AVE_TIME")
	public int logoffDivAverageTime;
	
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
//	//テーブル結合用
//	public KrcdtMonTime krcdtMonTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績の勤務時刻
	 */
	public WorkClockOfMonthly toDomain(){
		
		return WorkClockOfMonthly.of(
				EndClockOfMonthly.of(
						new AttendanceTimesMonth(this.endWorkTimes),
						new AttendanceTimeMonth(this.endWorkTotalClock),
						new AttendanceTimeMonth(this.endWorkAverageClock)),
				PCLogonOfMonthly.of(
						PCLogonClockOfMonthly.of(
								AggrPCLogonClock.of(
										new AttendanceDaysMonth(this.logonTotalDays),
										new AttendanceTimeMonth(this.logonTotalClock),
										new AttendanceTimeMonth(this.logonAverageClock)),
								AggrPCLogonClock.of(
										new AttendanceDaysMonth(this.logoffTotalDays),
										new AttendanceTimeMonth(this.logoffTotalClock),
										new AttendanceTimeMonth(this.logoffAverageClock))),
						PCLogonDivergenceOfMonthly.of(
								AggrPCLogonDivergence.of(
										new AttendanceDaysMonth(this.logonDivDays),
										new AttendanceTimeMonth(this.logonDivTotalTime),
										new AttendanceTimeMonth(this.logonDivAverageTime)),
								AggrPCLogonDivergence.of(
										new AttendanceDaysMonth(this.logoffDivDays),
										new AttendanceTimeMonth(this.logoffDivTotalTime),
										new AttendanceTimeMonth(this.logoffDivAverageTime)))));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の勤務時刻
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, WorkClockOfMonthly domain){
		
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
	 * @param domain 月別実績の勤務時刻
	 */
	public void fromDomainForUpdate(WorkClockOfMonthly domain){
		
		val endClock = domain.getEndClock();
		val logonClock = domain.getLogonInfo().getLogonClock();
		val logonDiv = domain.getLogonInfo().getLogonDivergence();
		
		this.endWorkTimes = endClock.getTimes().v();
		this.endWorkTotalClock = endClock.getTotalClock().v();
		this.endWorkAverageClock = endClock.getAverageClock().v();
		this.logonTotalDays = logonClock.getLogonClock().getTotalDays().v();
		this.logonTotalClock = logonClock.getLogonClock().getTotalClock().v();
		this.logonAverageClock = logonClock.getLogonClock().getAverageClock().v();
		this.logoffTotalDays = logonClock.getLogoffClock().getTotalDays().v();
		this.logoffTotalClock = logonClock.getLogoffClock().getTotalClock().v();
		this.logoffAverageClock = logonClock.getLogoffClock().getAverageClock().v();
		this.logonDivDays = logonDiv.getLogonDivergence().getDays().v();
		this.logonDivTotalTime = logonDiv.getLogonDivergence().getTotalTime().v();
		this.logonDivAverageTime = logonDiv.getLogonDivergence().getAverageTime().v();
		this.logoffDivDays = logonDiv.getLogoffDivergence().getDays().v();
		this.logoffDivTotalTime = logonDiv.getLogoffDivergence().getTotalTime().v();
		this.logoffDivAverageTime = logonDiv.getLogoffDivergence().getAverageTime().v();
	}
}
