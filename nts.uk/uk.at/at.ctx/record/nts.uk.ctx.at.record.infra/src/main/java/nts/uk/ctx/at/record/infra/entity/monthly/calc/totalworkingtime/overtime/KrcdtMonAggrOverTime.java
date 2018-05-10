package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_OVER_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrOverTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrOverTimePK PK;

	/** 残業時間 */
	@Column(name = "OVER_TIME")
	public int overTime;

	/** 計算残業時間 */
	@Column(name = "CALC_OVER_TIME")
	public int calcOverTime;

	/** 事前残業時間 */
	@Column(name = "BEFORE_OVER_TIME")
	public int beforeOverTime;

	/** 振替残業時間 */
	@Column(name = "TRNOVR_TIME")
	public int transferOverTime;

	/** 計算振替残業時間 */
	@Column(name = "CALC_TRNOVR_TIME")
	public int calcTransferOverTime;

	/** 法定内残業時間 */
	@Column(name = "LEGAL_OVER_TIME")
	public int legalOverTime;

	/** 法定内振替残業時間 */
	@Column(name = "LEGAL_TRNOVR_TIME")
	public int legalTransferOverTime;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
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
	
	/**
	 * ドメインに変換
	 * @return 集計残業時間
	 */
	public AggregateOverTime toDomain(){
		
		return AggregateOverTime.of(
				new OverTimeFrameNo(this.PK.overTimeFrameNo),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.overTime),
						new AttendanceTimeMonth(this.calcOverTime)),
				new AttendanceTimeMonth(this.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferOverTime),
						new AttendanceTimeMonth(this.calcTransferOverTime)),
				new AttendanceTimeMonth(this.legalOverTime),
				new AttendanceTimeMonth(this.legalTransferOverTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計残業時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateOverTime domain){
		
		this.PK = new KrcdtMonAggrOverTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getOverTimeFrameNo().v());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計残業時間
	 */
	public void fromDomainForUpdate(AggregateOverTime domain){
		
		this.overTime = domain.getOverTime().getTime().v();
		this.calcOverTime = domain.getOverTime().getCalcTime().v();
		this.beforeOverTime = domain.getBeforeOverTime().v();
		this.transferOverTime = domain.getTransferOverTime().getTime().v();
		this.calcTransferOverTime = domain.getTransferOverTime().getCalcTime().v();
		this.legalOverTime = domain.getLegalOverTime().v();
		this.legalTransferOverTime = domain.getLegalTransferOverTime().v();
	}
}
