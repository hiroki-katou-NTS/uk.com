package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc;

/**
 * 集計区分
 * @author shuichu_ishida
 */
public enum MonthlyAggregateAtr {
	/** 月の計算 */
	MONTHLY(0),
	/** 時間外超過 */
	EXCESS_OUTSIDE_WORK(1);
	
	public int value;
	private MonthlyAggregateAtr(int value){
		this.value = value;
	}
}
