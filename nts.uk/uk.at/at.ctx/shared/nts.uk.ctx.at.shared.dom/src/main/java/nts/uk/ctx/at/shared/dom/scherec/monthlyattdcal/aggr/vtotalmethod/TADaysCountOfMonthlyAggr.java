package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.Getter;

/**
 * 月別実績集計の振出日数カウント
 * @author shuichu_ishida
 */
@Getter
public class TADaysCountOfMonthlyAggr {

	/** 振出日数カウント条件 */
	private TADaysCountCondOfMonthlyAggr TADaysCountCondition;
	
	/**
	 * コンストラクタ
	 */
	public TADaysCountOfMonthlyAggr(){
		
		this.TADaysCountCondition = TADaysCountCondOfMonthlyAggr.ONLY_DAY_OF_WORK;
	}
	
	/**
	 * ファクトリー
	 * @param TADaysCountCondition 振出日数カウント条件
	 * @return 月別実績集計の振出日数カウント
	 */
	public static TADaysCountOfMonthlyAggr of(TADaysCountCondOfMonthlyAggr TADaysCountCondition){
		
		TADaysCountOfMonthlyAggr domain = new TADaysCountOfMonthlyAggr();
		domain.TADaysCountCondition = TADaysCountCondition;
		return domain;
	}
}
