package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc;

/**
 * 集計区分
 * @author shuichu_ishida
 */
public enum MonthlyAggregateAtr {
	/** 法定内含む*/
	AGGREGATE_STATUTORY_HOLIDAYS(0),
	/** 法定内含まない */
	NO_AGGREGATE_STATUTORY_HOLIDAYS(1);
	
	public int value;
	private MonthlyAggregateAtr(int value){
		this.value = value;
	}
}
