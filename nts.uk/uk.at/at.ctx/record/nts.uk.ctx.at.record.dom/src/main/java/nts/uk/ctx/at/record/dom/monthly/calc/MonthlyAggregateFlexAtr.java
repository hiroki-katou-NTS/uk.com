package nts.uk.ctx.at.record.dom.monthly.calc;

/**
 * フレックス集計区分
 * @author shuichu_ishida
 */
public enum MonthlyAggregateFlexAtr {
	/** 原則集計 */
	Principle(0),
	/** 便宜上集計 */
	ForConvenience(1);

	/**
	 * 原則集計か判定する
	 * @return true：原則集計、false：原則集計でない
	 */
	public boolean isPrinciple(){
		return this.equals(Principle);
	}
	
	/**
	 * 便宜上集計か判定する
	 * @return true：便宜上集計、false：便宜上集計でない
	 */
	public boolean isForConvenience(){
		return this.equals(ForConvenience);
	}
	
	public int value;
	private MonthlyAggregateFlexAtr(int value){
		this.value = value;
	}
}
