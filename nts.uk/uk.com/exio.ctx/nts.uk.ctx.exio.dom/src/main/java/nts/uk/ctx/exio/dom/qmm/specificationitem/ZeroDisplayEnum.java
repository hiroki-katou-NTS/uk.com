package nts.uk.ctx.exio.dom.qmm.specificationitem;

/**
 * ゼロ表示区分
 */
public enum ZeroDisplayEnum {
	NOT_SHOW(0, "Enum_Zero_Display_NOT_USE"), 
	SHOW(1, "Enum_Zero_Display_USE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ZeroDisplayEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}