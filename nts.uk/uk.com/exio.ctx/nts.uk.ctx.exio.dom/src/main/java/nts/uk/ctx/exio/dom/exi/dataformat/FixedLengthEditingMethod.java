package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 固定長編集方法
 *
 */
public enum FixedLengthEditingMethod {
	/**
	 * 0:前ゼロ
	 */
	ZERO_BEFORE(0, "Enum_FixedLengthEditingMethod_ZERO_BEFORE"),
	/**
	 * 1:後ゼロ
	 */
	ZERO_AFTER(1, "Enum_FixedLengthEditingMethod_ZERO_AFTER"),
	/**
	 * 2:前スペース
	 */
	SPACE_BEFORE(2, "Enum_FixedLengthEditingMethod_SPACE_BEFORE"),
	/**
	 * 3: 後スペース
	 */
	SPACE_AFTER(3, "Enum_FixedLengthEditingMethod_SPACE_AFTER");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private FixedLengthEditingMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
