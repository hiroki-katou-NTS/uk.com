package nts.uk.ctx.exio.dom.exo.datafomat;
/**
 * 
 * @author TamNX 日付出力書式
 *
 */
public enum DateOutputFormat {
	
	曜日(0, "曜日"),
	YYYYMMDD(1, "YYYYMMDD"),
	YYYY_MM_DD(2, "YYYY/MM/DD"),
	YYMMDD(3, "YYMMDD"),
	YY_MM_DD(4, "YY/MM/DD"),
	JJYYMMDD(5, "JJYYMMDD"),
	JJYY_MM_DD(6, "JJYY/MM/DD");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DateOutputFormat(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
