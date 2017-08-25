package nts.uk.ctx.at.request.dom.application.stamp;
/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 外出理由
 *
 */
public enum StampGoOutReason {
	
	/**
	 * 私用
	 */
	PRIVATE(0,"私用"),
	
	/**
	 * 公用
	 */
	PUBLIC(1,"公用"),
	
	/**
	 * 有償
	 */
	COMPENSATION(2,"有償"),
	
	/**
	 * 組合
	 */
	UNION(3,"組合"),
	
	/**
	 * 離席
	 */
	LEAVE_SEAT(4,"離席");
	
	public final int value;
	
	public final String name;
	
	StampGoOutReason(int value, String name){
		this.value = value;
		this.name = name;
	}
}
