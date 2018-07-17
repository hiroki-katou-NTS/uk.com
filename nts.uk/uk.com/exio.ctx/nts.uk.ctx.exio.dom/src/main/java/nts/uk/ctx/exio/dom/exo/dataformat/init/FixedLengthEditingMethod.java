package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum FixedLengthEditingMethod {
	
	//後ゼロ
	BEFORE_ZERO(0, "Enum_FixedLengthEditingMethod_BEFORE_ZERO"),
	//前ゼロ
	AFTER_ZERO(1, "Enum_FixedLengthEditingMethod_AFTER_ZERO"),
	//前スペース
	BEFORE_SPACE(2, "Enum_FixedLengthEditingMethod_BEFORE_SPACE"),
	//後スペース
	AFTER_SPACE(3, "Enum_FixedLengthEditingMethod_AFTER_SPACE");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private FixedLengthEditingMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
