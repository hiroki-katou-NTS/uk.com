package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/** 時間区分 **/
public enum EnumTimeDivision {
		
	/** 指定時間 Specified time **/
	SPECIFIED_TIME(0 ,"Enum_SPECIFIED_TIME"),
	
	/**一定時間  Fixed time**/
	FIXED_TIME(1 , "ENUM_FIXED_TIME");
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static EnumTimeDivision[] values = EnumTimeDivision.values();
	
	private EnumTimeDivision(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	public static EnumTimeDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EnumTimeDivision val : EnumTimeDivision.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
}
