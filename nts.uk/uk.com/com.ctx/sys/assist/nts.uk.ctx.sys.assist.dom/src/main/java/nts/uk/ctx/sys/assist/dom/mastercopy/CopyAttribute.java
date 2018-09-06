package nts.uk.ctx.sys.assist.dom.mastercopy;

public enum CopyAttribute {
	
	/** The with company id. */
	COPY_WITH_COMPANY_ID(0, "Enum_CopyAttribute_COPY_WITH_COMPANY_ID"),

	/** The copy more company id. */
	COPY_MORE_COMPANY_ID(1, "Enum_CopyAttribute_COPY_MORE_COMPANY_ID"),
	
	COPY_OTHER(2, "Enum_CopyAttribute_COPY_OTHER");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The Constant values. */
	private final static CopyAttribute[] values = CopyAttribute.values();
	
	/**
	 * Instantiates a new copy attribute.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private CopyAttribute(int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the copy attribute
	 */
	public static CopyAttribute valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CopyAttribute val : CopyAttribute.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
