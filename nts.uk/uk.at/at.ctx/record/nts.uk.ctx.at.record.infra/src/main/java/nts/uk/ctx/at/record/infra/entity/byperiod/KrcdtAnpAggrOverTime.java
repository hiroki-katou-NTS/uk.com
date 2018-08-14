package nts.uk.ctx.at.record.infra.entity.byperiod;

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
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_OVER_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrOverTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrOverTimePK PK;

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
	
	/** マッチング：任意期間別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "FRAME_CODE", referencedColumnName = "FRAME_CODE", insertable = false, updatable = false)
	})
	public KrcdtAnpAttendanceTime krcdtAnpAttendanceTime;
	
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
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 集計残業時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateOverTime domain){
		
		this.PK = new KrcdtAnpAggrOverTimePK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
	}
}
