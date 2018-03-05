package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author HungTT 項目型
 *
 */
public enum ItemType {
	NUMERIC(0, "数値型"),

	CHARACTER(1, "文字型"),

	DATE(2, "日付型"), 
	
	INS_TIME(3, "時刻型");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
