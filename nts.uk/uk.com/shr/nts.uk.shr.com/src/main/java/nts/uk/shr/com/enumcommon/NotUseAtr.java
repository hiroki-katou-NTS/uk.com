package nts.uk.shr.com.enumcommon;

import java.io.Serializable;

//するしない区分
public enum NotUseAtr implements Serializable {

	/** The use. */
	USE(1, "利用する"),

	/** The not use. */
	NOT_USE(0, "利用しない");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static NotUseAtr[] values = NotUseAtr.values();
	
	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private NotUseAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static NotUseAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (NotUseAtr val : NotUseAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
