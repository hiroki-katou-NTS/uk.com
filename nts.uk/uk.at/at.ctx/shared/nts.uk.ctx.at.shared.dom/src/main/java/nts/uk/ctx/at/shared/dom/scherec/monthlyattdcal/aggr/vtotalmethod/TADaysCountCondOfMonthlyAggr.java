package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

/**
 * 月別実績集計の振出日数カウント条件
 * @author shuichu_ishida
 */
public enum TADaysCountCondOfMonthlyAggr {
	/** 勤務している日のみカウント */
	ONLY_DAY_OF_WORK(0),
	/** 勤務していない日もカウント */
	INCLUDE_DAY_NOT_WORK(1);
	
	public int value;
	private TADaysCountCondOfMonthlyAggr(int value){
		this.value = value;
	}
}
