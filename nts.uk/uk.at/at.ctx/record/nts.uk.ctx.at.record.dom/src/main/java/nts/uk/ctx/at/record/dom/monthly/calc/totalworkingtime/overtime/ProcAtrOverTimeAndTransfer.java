package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime;

/**
 * 残業振替区分
 * @author shuichu_ishida
 */
public enum ProcAtrOverTimeAndTransfer {
	/** 残業 */
	OVER_TIME(0),
	/** 振替 */
	TRANSFER(1);
	
	public int value;
	private ProcAtrOverTimeAndTransfer(int value){
		this.value = value;
	}
}
