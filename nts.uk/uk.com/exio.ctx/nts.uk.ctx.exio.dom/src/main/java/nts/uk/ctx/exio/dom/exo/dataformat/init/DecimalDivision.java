package nts.uk.ctx.exio.dom.exo.dataformat.init;

/**
 * 
 * @author TamNX
 *
 */
public enum DecimalDivision {
	
	//小数なし
	NO_DECIMAL(0, "Enum_DecimalDivision_NO_DECIMAL"),
	//小数あり
	DECIMAL(1, "Enum_DecimalDivision_DECIMAL");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalDivision(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
