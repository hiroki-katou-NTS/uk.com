package nts.uk.ctx.at.record.infra.entity.byperiod.verticaltotal.worktime;

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
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceAtrOfMonthly;
import nts.uk.ctx.at.record.infra.entity.byperiod.KrcdtAnpAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計乖離時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_DIVG_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrDivgTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrDivgTimePK PK;
	
	/** 乖離フラグ */
	@Column(name = "DIVERGENCE_ATR")
	public int divergenceAtr;
	/** 乖離時間 */
	@Column(name = "DIVERGENCE_TIME")
	public int divergenceTime;
	/** 控除時間 */
	@Column(name = "DEDUCTION_TIME")
	public int deductionTime;
	/** 控除後乖離時間 */
	@Column(name = "DVRGEN_TIME_AFT_DEDU")
	public int divergenceTimeAfterDeduction;

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
	 * @return 集計乖離時間
	 */
	public AggregateDivergenceTime toDomain(){
		
		return AggregateDivergenceTime.of(
				this.PK.divergenceTimeNo,
				new AttendanceTimeMonth(this.divergenceTime),
				new AttendanceTimeMonth(this.deductionTime),
				new AttendanceTimeMonth(this.divergenceTimeAfterDeduction),
				EnumAdaptor.valueOf(this.divergenceAtr, DivergenceAtrOfMonthly.class));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 集計乖離時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateDivergenceTime domain){
		
		this.PK = new KrcdtAnpAggrDivgTimePK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
				domain.getDivergenceTimeNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計乖離時間
	 */
	public void fromDomainForUpdate(AggregateDivergenceTime domain){
		
		this.divergenceAtr = domain.getDivergenceAtr().value;
		this.divergenceTime = domain.getDivergenceTime().v();
		this.deductionTime = domain.getDeductionTime().v();
		this.divergenceTimeAfterDeduction = domain.getDivergenceTimeAfterDeduction().v();
	}
}
