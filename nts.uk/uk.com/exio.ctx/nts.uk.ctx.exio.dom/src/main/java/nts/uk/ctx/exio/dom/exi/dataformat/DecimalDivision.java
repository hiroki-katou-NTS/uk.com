package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 小数区分
 *
 */
public enum DecimalDivision {
	/**
	 * 1:小数あり
	 */
	DECIMAL(1, "小数あり"),
	/**
	 * 小数なし
	 */
	NO_DECIMAL(0, "小数なし");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalDivision(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
