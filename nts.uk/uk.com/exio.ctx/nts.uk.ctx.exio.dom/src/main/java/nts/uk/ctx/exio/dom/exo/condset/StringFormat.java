package nts.uk.ctx.exio.dom.exo.condset;

public enum StringFormat {
	/**
	 * なし
	 */
	NONE(2, ""),
	/**
	 * ダブルコーテーション（"）
	 */
	DOUBLE_QUOTATION(3, "\""),
	/**
	 * シングルコーテーション（'）
	 */
	SINGLE_QUOTATION(4, "'");

	/** The value. */
	public final int value;
	
	/** The value. */
	public final String character;

	private StringFormat(int value, String character) {
		this.value = value;
		this.character = character;
	}
}
