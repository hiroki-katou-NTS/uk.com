package nts.uk.ctx.exio.dom.exo.condset;
/**
 * 区切り文字
 */
public enum Delimiter {
	/**
	 * なし
	 */
	NONE(0, ""),
	/**
	 * カンマ
	 */
	COMMA(1, ","),
	/**
	 * セミコロン
	 */
	SEMICOLON(2, ";"),
	/**
	 * タブ
	 */
	TAB(3, "    "),
	/**
	 * スペース
	 */
	SPACE(4, " ");

	/** The value. */
	public final int value;
	
	public final String delimiter;

	private Delimiter(int value, String delimiter) {
		this.value = value;
		this.delimiter = delimiter;
	}
}
