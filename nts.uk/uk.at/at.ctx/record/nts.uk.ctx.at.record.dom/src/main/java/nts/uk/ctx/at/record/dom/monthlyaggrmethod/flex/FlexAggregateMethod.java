package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * フレックス集計方法
 * @author shuichu_ishida
 */
public enum FlexAggregateMethod {
	/** 原則集計 */
	PRINCIPLE(0),
	/** 便宜上集計 */
	FOR_CONVENIENCE(1);
	
	/**
	 * 原則集計か判定する
	 * @return true：原則集計、false：原則集計でない
	 */
	public boolean isPrinciple(){
		return this.equals(PRINCIPLE);
	}
	
	/**
	 * 便宜上集計か判定する
	 * @return true：便宜上集計、false：便宜上集計でない
	 */
	public boolean isForConvenience(){
		return this.equals(FOR_CONVENIENCE);
	}
	
	public int value;
	private FlexAggregateMethod(int value){
		this.value = value;
	}
}
