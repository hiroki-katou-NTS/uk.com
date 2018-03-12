package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

/**
 * 残業振替区分
 * @author shuichu_ishida
 */
public enum OverTimeAndTransferAtr {
	/** 残業 */
	OVER_TIME(0),
	/** 振替 */
	TRANSFER(1);
	
	public int value;
	private OverTimeAndTransferAtr(int value){
		this.value = value;
	}
}
