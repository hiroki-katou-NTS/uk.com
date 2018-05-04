package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.uk.shr.com.i18n.TextResource;

/**
 * The Enum SystemType.
 */
// システム区分
public enum SystemType {
	
	/** The common. */
	COMMON(0, "Enum_SystemType_COMMON", TextResource.localize("CMM001_43")), // will update correct text resource later

	/** The employment. */
	EMPLOYMENT(1, "Enum_SystemType_EMPLOYMENT", TextResource.localize("CMM001_43")), // will update correct text resource later
	
	/** The salary. */
	SALARY(2, "Enum_SystemType_SALARY", TextResource.localize("CMM001_44")), // will update correct text resource later
	
	/** The human resource. */
	HUMAN_RESOURCE(3, "Enum_SystemType_HUMAN_RESOURCE", TextResource.localize("CMM001_45")); // will update correct text resource later

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The name display UI */
	public final String displayName;

	/** The Constant values. */
	private final static SystemType[] values = SystemType.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private SystemType(int value, String nameId, String displayName) {
		this.value = value;
		this.nameId = nameId;
		this.displayName = displayName;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static SystemType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SystemType val : SystemType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
