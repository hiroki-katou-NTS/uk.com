package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import lombok.Getter;
import lombok.val;

/**
 * 通常勤務の法定内集計設定
 * @author shuichu_ishida
 */
@Getter
public class LegalAggrSetOfReg {

	/** 集計時間設定 */
	private AggregateTimeSet aggregateTimeSet;
	/** 時間外超過設定 */
	private ExcessOutsideTimeSet excessOutsideTimeSet;
	
	/**
	 * コンストラクタ
	 */
	public LegalAggrSetOfReg(){
		
		this.aggregateTimeSet = new AggregateTimeSet();
		this.excessOutsideTimeSet = new ExcessOutsideTimeSet();
	}

	/**
	 * ファクトリー
	 * @param aggreagteTimeSet 集計時間設定
	 * @param excessOutsideTimeSet 時間外超過設定
	 * @return 通常勤務の法定内集計設定
	 */
	public static LegalAggrSetOfReg of(
			AggregateTimeSet aggreagteTimeSet,
			ExcessOutsideTimeSet excessOutsideTimeSet){
		
		val domain = new LegalAggrSetOfReg();
		domain.aggregateTimeSet = aggreagteTimeSet;
		domain.excessOutsideTimeSet = excessOutsideTimeSet;
		return domain;
	}
}
