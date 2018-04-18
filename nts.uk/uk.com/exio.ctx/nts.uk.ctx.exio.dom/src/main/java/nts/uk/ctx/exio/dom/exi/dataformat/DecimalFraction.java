package nts.uk.ctx.exio.dom.exi.dataformat;
/**
 * 
 * @author HiepLD 小数端数

 *
 */
public enum DecimalFraction {

	TRUNCATION(0, "TRUNCATION"),
	ROUND_UP(1, "ROUND_UP"),
	DOWN_4_UP_5(2, "DOWN4_UP_5");

	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalFraction(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
