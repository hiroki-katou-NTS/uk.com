/*
 * 
 */
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

/**
 * The Enum DisplayDivision.
 */
public enum DisplayDivision {
    
	/** The Notshow. */
	Notshow(0, "表示しない"),
    
    /** The Indicate. */
    Indicate(1, "表示する");

	/** The value. */
	public int value;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static DisplayDivision[] values = DisplayDivision.values();

	/**
	 * Instantiates a new DisplayDivision.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private DisplayDivision(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the DisplayDivision
	 */
	public static DisplayDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DisplayDivision val : DisplayDivision.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
