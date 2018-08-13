package nts.uk.ctx.exio.dom.exo.dataformat.init;
/**
 * 
 * @author TamNX 日付出力書式
 *
 */
public enum DateOutputFormat {
	
    YYYY_MM_DD(0, "YYYY/MM/DD"),
    YYYYMMDD(1, "YYYYMMDD"),
    YY_MM_DD(2, "YY/MM/DD"),
    YYMMDD(3, "YYMMDD"),
	JJYY_MM_DD(4, "JJYY/MM/DD"),
	JJYYMMDD(5, "JJYYMMDD"),
	DAY_OF_WEEK(6, "曜日");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DateOutputFormat(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
