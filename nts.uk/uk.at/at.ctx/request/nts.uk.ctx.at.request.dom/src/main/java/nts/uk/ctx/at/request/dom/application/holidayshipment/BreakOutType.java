package nts.uk.ctx.at.request.dom.application.holidayshipment;

/**
 * 振休振出区分 KAF011
 * 
 * @author sonnlb
 */
public enum BreakOutType {

	/**
	 * 振出
	 */
	WORKING_DAY(1),
	/**
	 * 振休
	 */
	HOLIDAY(0);

	public final int value;

	private BreakOutType(int value) {
		this.value = value;
	}

}
