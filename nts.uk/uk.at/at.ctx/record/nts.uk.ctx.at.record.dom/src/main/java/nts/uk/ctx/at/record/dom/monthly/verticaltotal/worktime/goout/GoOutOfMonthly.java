package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の外出
 * @author shuichu_ishida
 */
@Getter
public class GoOutOfMonthly {

	/** 外出 */
	private List<AggregateGoOut> goOuts;
	/** 育児外出 */
	private GoOutForChildCare goOutForChildCare;
	
	/**
	 * コンストラクタ
	 */
	public GoOutOfMonthly(){
		
		this.goOuts = new ArrayList<>();
		this.goOutForChildCare = new GoOutForChildCare();
	}

	/**
	 * ファクトリー
	 * @param goOuts 外出
	 * @param goOutForChildCare 育児外出
	 * @return 月別実績の外出
	 */
	public static GoOutOfMonthly of(
			List<AggregateGoOut> goOuts,
			GoOutForChildCare goOutForChildCare){
		
		val domain = new GoOutOfMonthly();
		domain.goOuts = goOuts;
		domain.goOutForChildCare = goOutForChildCare;
		return domain;
	}
}
