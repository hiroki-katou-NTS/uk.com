package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import lombok.Getter;
import lombok.val;

/**
 * 変形労働時間勤務の法定内集計設定
 * @author shuichu_ishida
 */
@Getter
public class LegalAggrSetOfIrg {

	/** 集計時間設定 */
	private AggregateTimeSet aggregateTimeSet;
	/** 時間外超過設定 */
	private ExcessOutsideTimeSet excessOutsideTimeSet;
	/** 変形労働計算の設定 */
	private CalcSettingOfIrregular calcSetOfIrregular;
	/** 精算期間 */
	private SettlementPeriodOfIrg settlementPeriod;
	
	/**
	 * コンストラクタ
	 */
	public LegalAggrSetOfIrg(){
		
		this.aggregateTimeSet = new AggregateTimeSet();
		this.excessOutsideTimeSet = new ExcessOutsideTimeSet();
		this.calcSetOfIrregular = new CalcSettingOfIrregular();
		this.settlementPeriod = new SettlementPeriodOfIrg();
	}

	/**
	 * ファクトリー
	 * @param aggreagteTimeSet 集計時間設定
	 * @param excessOutsideTimeSet 時間外超過設定
	 * @param calcSetOfIrregular 変形労働計算の設定
	 * @param settlementPeriod 精算期間
	 * @return 変形労働時間勤務の法定内集計設定
	 */
	public static LegalAggrSetOfIrg of(
			AggregateTimeSet aggreagteTimeSet,
			ExcessOutsideTimeSet excessOutsideTimeSet,
			CalcSettingOfIrregular calcSetOfIrregular,
			SettlementPeriodOfIrg settlementPeriod){
		
		val domain = new LegalAggrSetOfIrg();
		domain.aggregateTimeSet = aggreagteTimeSet;
		domain.excessOutsideTimeSet = excessOutsideTimeSet;
		domain.calcSetOfIrregular = calcSetOfIrregular;
		domain.settlementPeriod = settlementPeriod;
		return domain;
	}
}
