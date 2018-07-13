package nts.uk.ctx.exio.dom.exo.dataformat;
/**
 * 
 * @author TamNX 日付出力書式
 *
 */
public enum DateOutputFormat {
	
	JJYY_MM_DD(0, "JJYY/MM/DD"),
	JJYYMMDD(1, "JJYYMMDD"),
	YY_MM_DD(2, "YY/MM/DD"),
	YYMMDD(3, "YYMMDD"),
	YYYY_MM_DD(4, "YYYY/MM/DD"),
	YYYYMMDD(5, "YYYYMMDD"),
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
