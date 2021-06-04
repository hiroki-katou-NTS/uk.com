package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author HiepLD 小数点区分
 *
 */
public enum DecimalPointClassification {
	/**
	 * 0:小数点を付加しない
	 */
	NO_OUTPUT_DECIMAL_POINT(0, "Enum_DecimalPointClassification_NO_OUTPUT_DECIMAL_POINT"),
	/**
	 * 1: 小数点を付加する
	 */
	OUTPUT_DECIMAL_POINT(1, "Enum_DecimalPointClassification_OUTPUT_DECIMAL_POINT");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalPointClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
