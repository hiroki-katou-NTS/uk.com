package nts.uk.file.at.app.export.monthlyschedule;

/**
 * Display type (0: hide, 1: display)
 *
 * @author ChienDM
 */
public enum DisplayTypeEnum {
	/** The hide. */
	//非表示
	HIDE(0),
	
	/** The display. */
	//表示
	DISPLAY(1);
	
	/** The value. */
	public int value;

	/**
	 * Instantiates a new file output type.
	 *
	 * @param value the value
	 */
	private DisplayTypeEnum(int value) {
		this.value = value;
	}
	
	public static DisplayTypeEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DisplayTypeEnum val : DisplayTypeEnum.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
