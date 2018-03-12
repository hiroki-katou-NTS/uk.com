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
	
	public int value;
	private FlexAggregateMethod(int value){
		this.value = value;
	}
}
