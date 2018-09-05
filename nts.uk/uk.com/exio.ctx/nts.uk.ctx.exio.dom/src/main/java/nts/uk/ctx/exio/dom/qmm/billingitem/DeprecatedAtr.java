package nts.uk.ctx.exio.dom.qmm.billingitem;

/**
 * 
 * @author thanh.tq 廃止区分
 * 
 */
public enum DeprecatedAtr {

	NOT_DEPRECATED(0, "廃止しない"), 
	DEPRECATED(1, "廃止する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DeprecatedAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
