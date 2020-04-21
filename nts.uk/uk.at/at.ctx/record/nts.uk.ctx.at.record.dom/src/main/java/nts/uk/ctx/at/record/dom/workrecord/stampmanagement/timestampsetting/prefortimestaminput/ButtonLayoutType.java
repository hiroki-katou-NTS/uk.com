package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

/**
 * ボタン配置タイプ
 * @author phongtq
 *
 */

public enum ButtonLayoutType {
	
	/** 大2小4 */
	LARGE_2_SMALL_4(0,"大2小4","Enum_ButtonLayoutType_LARGE_2_SMALL_4"),

	/** 小8 */
	SMALL_8(1,"小8","Enum_ButtonLayoutType_SMALL_8");

	/** The value. */
	public int value;
	
	/** The name id. */
	public  String nameId;

	/** The description. */
	public  String description;

	/** The Constant values. */
	private final static ButtonLayoutType[] values = ButtonLayoutType.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ButtonLayoutType(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ButtonLayoutType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ButtonLayoutType val : ButtonLayoutType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
