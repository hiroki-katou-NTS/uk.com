package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * フレックス時間勤務の月の集計設定　（共通）
 * @author shuichi_ishida
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetFlxAggr implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 集計方法 */
	@Column(name = "AGGR_METHOD")
	public int aggregateMethod;
	
	/** 残業時間を含める */
	@Column(name = "INCLUDE_OVER_TIME")
	public int includeOverTime;
	
	/** 繰越設定 */
	@Column(name = "CARRYFWD_SET")
	public int carryforwardSet;
	
	/** 集計設定 */
	@Column(name = "AGGREGATE_SET")
	public int aggregateSet;
	
	/** 時間外超過対象設定 */
	@Column(name = "EXCESS_OUTTIME_SET")
	public int excessOutsideTimeTargetSet;
	
	/** 36協定時間集計方法 */
	@Column(name = "AGGR_METHOD_36AGRE")
	public int aggregateMethodOf36AgreementTime;
	
	/** 清算期間 */
	@Column(name = "SETTLE_PERIOD")
	public int settlePeriod;
	
	/** 開始月 */
	@Column(name = "START_MONTH")
	public int startMonth;
	
	/** 清算期間月数 */
	@Column(name = "SETTLE_PERIOD_MON")
	public int settlePeriodMon;
}
