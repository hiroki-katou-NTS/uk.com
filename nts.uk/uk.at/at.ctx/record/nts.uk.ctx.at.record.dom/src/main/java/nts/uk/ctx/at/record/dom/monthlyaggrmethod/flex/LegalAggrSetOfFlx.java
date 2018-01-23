package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

import lombok.Getter;
import lombok.val;

/**
 * フレックス時間勤務の法定内集計設定
 * @author shuichu_ishida
 */
@Getter
public class LegalAggrSetOfFlx {

	/** 集計時間設定 */
	private AggregateTimeSetting aggregateTimeSet;
	/** 時間外超過時間設定 */
	private ExcessOutsideTimeSetting excessOutsideTimeSet;
	
	/**
	 * コンストラクタ
	 */
	public LegalAggrSetOfFlx(){
		
		this.aggregateTimeSet = new AggregateTimeSetting();
		this.excessOutsideTimeSet = new ExcessOutsideTimeSetting();
	}

	/**
	 * ファクトリー
	 * @param aggregateTimeSet 集計時間設定
	 * @param excessOutsideTimeSet 時間外超過時間設定
	 * @return フレックス時間勤務の法定内集計設定
	 */
	public static LegalAggrSetOfFlx of(
			AggregateTimeSetting aggregateTimeSet,
			ExcessOutsideTimeSetting excessOutsideTimeSet){
		
		val domain = new LegalAggrSetOfFlx();
		domain.aggregateTimeSet = aggregateTimeSet;
		domain.excessOutsideTimeSet = excessOutsideTimeSet;
		return domain;
	}
}
