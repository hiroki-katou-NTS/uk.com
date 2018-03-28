package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 端数処理
 *
 */
public enum Rounding {
	TRUNCATION(0, "Enum_Rounding_Truncation"), 
	ROUND_UP(1, "Enum_Rounding_Round_Up"), 
	DOWN_4_UP_5(2, "Enum_Rounding_Down_4_Up_5");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private Rounding(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
