package nts.uk.ctx.exio.dom.exo.dataformat.init;

/**
 * 
 * @author TamNX 区切り文字設定
 *
 */
public enum DelimiterSetting {
	
	//区切り文字なし
	NO_DELIMITER(0, "Enum_DelimiterSetting_NO_DELIMITER"), 
	//小数点で区切る
	SEPARATE_BY_DECIMAL(1, "Enum_DelimiterSetting_SEPARATE_BY_DECIMAL"), 
	//コロンで区切る
	SEPARATE_BY_COLON(2, "Enum_DelimiterSetting_SEPARATE_BY_COLON");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DelimiterSetting(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
