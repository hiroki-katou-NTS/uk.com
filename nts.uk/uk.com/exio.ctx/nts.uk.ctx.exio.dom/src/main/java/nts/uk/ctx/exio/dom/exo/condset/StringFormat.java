package nts.uk.ctx.exio.dom.exo.condset;

public enum StringFormat {
	/**
	 * なし
	 */
	NONE(0, ""),
	/**
	 * ダブルコーテーション（"）
	 */
	DOUBLE_QUOTATION(1, "\""),
	/**
	 * シングルコーテーション（'）
	 */
	SINGLE_QUOTATION(2, "'");

	/** The value. */
	public final int value;
	
	/** The value. */
	public final String character;

	private StringFormat(int value, String character) {
		this.value = value;
		this.character = character;
	}
}
