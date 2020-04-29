package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

/**
 * @author loivt
 * 振休振出区分
 */
public enum BreakOrRestTime {
	
	/**
	 * 振休
	 */
	RESTTIME(0),
	
	/**
	 * 振出 
	 */
	BREAKTIME(1);

	public final int value;
	
	BreakOrRestTime(int value){
		this.value = value;
	}
}
