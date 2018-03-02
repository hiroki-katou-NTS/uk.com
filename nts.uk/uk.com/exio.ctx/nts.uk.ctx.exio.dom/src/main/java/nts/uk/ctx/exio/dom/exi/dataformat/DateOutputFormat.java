package nts.uk.ctx.exio.dom.exi.dataformat;
/**
 * 
 * @author DatLH 日付出力書式
 *
 */
public enum DateOutputFormat {
	
	YYYY_MM_DD(0, "Enum_DateOutputFormat_YYYY_MM_DD"),
	YYYYMMDD(1, "Enum_DateOutputFormat_YYYYMMDD"),
	YY_MM_DD(2, "Enum_DateOutputFormat_YY_MM_DD"),
	YYMMDD(3, "Enum_DateOutputFormat_YYMMDD"),
	JJYY_MM_DD(4, "Enum_DateOutputFormat_JJYY_MM_DD"),
	JJYYMMDD(5, "Enum_DateOutputFormat_JJYYMMDD");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DateOutputFormat(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
