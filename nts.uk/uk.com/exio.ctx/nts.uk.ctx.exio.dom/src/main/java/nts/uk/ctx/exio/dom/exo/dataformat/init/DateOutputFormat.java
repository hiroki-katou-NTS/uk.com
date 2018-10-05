package nts.uk.ctx.exio.dom.exo.dataformat.init;
/**
 * 
 * @author TamNX 日付出力書式
 *
 */
public enum DateOutputFormat {
	
    YYYY_MM_DD(0, "YYYY/MM/DD", "yyyy/MM/dd"),
    YYYYMMDD(1, "YYYYMMDD", "yyyyMMdd"),
    YY_MM_DD(2, "YY/MM/DD", "yy/MM/dd"),
    YYMMDD(3, "YYMMDD", "yyMMdd"),
	JJYY_MM_DD(4, "JJYY/MM/DD", ""),
	JJYYMMDD(5, "JJYYMMDD", ""),
	DAY_OF_WEEK(6, "曜日", "w");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	public final String format;

	private DateOutputFormat(int value, String nameId, String format) {
		this.value = value;
		this.nameId = nameId;
		this.format = format;
	}
}
