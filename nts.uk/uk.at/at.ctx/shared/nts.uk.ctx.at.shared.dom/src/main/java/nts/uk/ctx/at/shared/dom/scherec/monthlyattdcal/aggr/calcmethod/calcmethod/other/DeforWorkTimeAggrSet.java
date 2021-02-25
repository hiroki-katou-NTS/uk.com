package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 変形労働時間勤務の法定内集計設定
 */
@Getter
public abstract class DeforWorkTimeAggrSet extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	protected String comId;

	/** 集計時間設定 */
	protected ExcessOutsideTimeSetReg aggregateTimeSet;

	/** 時間外超過設定 */
	protected ExcessOutsideTimeSetReg excessOutsideTimeSet;

	/** 変形労働計算の設定 */
	protected DeforLaborCalSetting deforLaborCalSetting;

	/** 精算期間 */
	protected DeforLaborSettlementPeriod settlementPeriod;

	/**
	 * 変形労働精算期間の取得
	 *
	 * @return 精算期間
	 */
	public DeforLaborSettlementPeriod getDeforLaborAccPeriod() {
		return this.settlementPeriod;
	}
	
	protected DeforWorkTimeAggrSet () {}
	
	protected void set(String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			DeforLaborCalSetting deforLaborCalSetting,
			DeforLaborSettlementPeriod settlementPeriod) {

		this.comId = comId;
		this.aggregateTimeSet = aggregateTimeSet;
		this.excessOutsideTimeSet = excessOutsideTimeSet;
		this.deforLaborCalSetting = deforLaborCalSetting;
		this.settlementPeriod = settlementPeriod;
	}
}
