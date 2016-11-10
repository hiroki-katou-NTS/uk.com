package nts.uk.ctx.pr.proto.dom.enums;

/**
 * 予備月区分
 * 
 * @author vunv
 *
 */
public enum SparePayAttribute {
	/**
	 * 0:通常
	 */
	NORMAL(0),

	/**
	 * 1:予備
	 */
	PRELIMINARY(1);

	public int value;

	private SparePayAttribute(int value) {
		this.value = value;
	}

}
