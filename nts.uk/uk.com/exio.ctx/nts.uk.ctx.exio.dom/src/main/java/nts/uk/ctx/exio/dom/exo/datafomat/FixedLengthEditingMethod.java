package nts.uk.ctx.exio.dom.exo.datafomat;

public enum FixedLengthEditingMethod {
	
	//後ゼロ
	AFTER_ZERO(0, "Enum_FixedLengthEditingMethod_AFTER_ZERO"),
	//後スペース
	AFTER_SPACE(1, "Enum_FixedLengthEditingMethod_AFTER_SPACE"),
	//前ゼロ
	PREVIOUS_ZERO(2, "Enum_FixedLengthEditingMethod_PREVIOUS_ZERO"),
	//前スペース
	PRE_SPACE(3, "Enum_FixedLengthEditingMethod_PRE_SPACE");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private FixedLengthEditingMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
