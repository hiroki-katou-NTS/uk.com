package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime;

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
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_OVER_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonOverTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
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
	 * @param krcdtMonAggrOverTimes 集計残業時間
	 * @return 月別実績の残業時間
	 */
	public OverTimeOfMonthly toDomain(List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimes){
		
		if (krcdtMonAggrOverTimes == null) krcdtMonAggrOverTimes = new ArrayList<>();
		
		return OverTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalOverTime),
						new AttendanceTimeMonth(this.calcTotalOverTime)),
				new AttendanceTimeMonth(this.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalTransferOverTime),
						new AttendanceTimeMonth(this.calcTotalTransferOverTime)),
				krcdtMonAggrOverTimes.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の残業時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, OverTimeOfMonthly domain){
		
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
	 * @param domain 月別実績の残業時間
	 */
	public void fromDomainForUpdate(OverTimeOfMonthly domain){
		
		this.totalOverTime = domain.getTotalOverTime().getTime().v();
		this.calcTotalOverTime = domain.getTotalOverTime().getCalcTime().v();
		this.beforeOverTime = domain.getBeforeOverTime().v();
		this.totalTransferOverTime = domain.getTotalTransferOverTime().getTime().v();
		this.calcTotalTransferOverTime = domain.getTotalTransferOverTime().getCalcTime().v();
	}
}
