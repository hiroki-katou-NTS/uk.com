package nts.uk.ctx.at.request.dom.application.overtime;

/**
 * @author loivt
 * 残業区分
 */
public enum OverTimeAtr {
	
	/**
	 * 早出残業
	 */
	PREOVERTIME(0),
	/**
	 * 通常残業
	 */
	REGULAROVERTIME(1),
	/**
	 * 早出残業・通常残業
	 */
	ALL(2);
	public final int value;
	
	OverTimeAtr(int value){
		this.value = value;
	}

}
