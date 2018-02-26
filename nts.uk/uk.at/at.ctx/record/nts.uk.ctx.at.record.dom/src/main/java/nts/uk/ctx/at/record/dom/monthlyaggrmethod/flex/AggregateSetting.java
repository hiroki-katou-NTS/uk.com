package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * 集計設定
 * @author shuichu_ishida
 */
public enum AggregateSetting {
	/** フレックス時間の内訳を管理する */
	MANAGE_DETAIL(0),
	/** 時間外は全てフレックス時間として管理する */
	INCLUDE_ALL_OUTSIDE_TIME_IN_FLEX_TIME(1);
	
	public int value;
	private AggregateSetting(int value){
		this.value = value;
	}
}
