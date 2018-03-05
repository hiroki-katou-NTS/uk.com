package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 区切り文字設定
 *
 */
public enum DelimiterSetting {
	NO_DELIMITER(0, "区切り文字なし"), 
	CUT_BYDECIMAL_POINT(1, "小数点で区切る"), 
	CUT_BY_COLON(2, "コロンで区切る");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DelimiterSetting(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
