package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 小数区分
 *
 */
public enum DecimalDivision {
	DECIMAL(1, "Enum_DecimalDivision_DECIMAL"), 
	NO_DECIMAL(0, "Enum_DecimalDivision_NO_DECIMAL");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalDivision(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
