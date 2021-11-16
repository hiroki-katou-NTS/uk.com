package nts.uk.ctx.exio.dom.exi.dataformat;
/**
 * 
 * @author HiepLD 小数端数

 *
 */
public enum DecimalFraction {
	/**
	 * 切り捨て
	 */
	TRUNCATION(0, "TRUNCATION"),
	/**
	 * 切り上げ
	 */
	ROUND_UP(1, "ROUND_UP"),
	/**
	 * 四捨五入
	 */
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
