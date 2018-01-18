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
	
	/**
	 * フレックス時間の内訳を管理するか判定する
	 * @return true：内訳を管理する、false：内訳を管理しない
	 */
	public boolean isManageDetail(){
		return this.equals(MANAGE_DETAIL);
	}
	
	/**
	 * 時間外は全てフレックス時間として管理するか判定する
	 * @return true：全てフレックス時間とする、false：全てがフレックス時間とは限らない
	 */
	public boolean isIncludeAllOutsideTimeInFlexTime(){
		return this.equals(INCLUDE_ALL_OUTSIDE_TIME_IN_FLEX_TIME);
	}
	
	public int value;
	private AggregateSetting(int value){
		this.value = value;
	}
}
